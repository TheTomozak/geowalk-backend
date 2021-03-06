package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.BadRequestImageException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.models.entities.BlogPost;
import com.example.geowalk.models.entities.Image;
import com.example.geowalk.models.repositories.BlogPostRepo;
import com.example.geowalk.models.repositories.ImageRepo;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.regex.Pattern;

@Service
public class ImageService {

    private final ImageRepo imageRepo;
    private final BlogPostRepo blogPostRepo;

    private final String BAD_REQUEST_IMAGE = "Image request has bad body";

    public ImageService(ImageRepo imageRepo, BlogPostRepo blogPostRepo) {
        this.imageRepo = imageRepo;
        this.blogPostRepo = blogPostRepo;
    }

    public Image save(MultipartFile image, String folderName, Long idBlogPost) {

        String imageFolderName = folderName;

        File uploadDirectory = new File(imageFolderName);
        uploadDirectory.mkdirs();

        Long idName;
        if(imageRepo.max() == null) idName = 1L;
        else idName = imageRepo.max() + 1;

        File oFile = new File(imageFolderName+"/" + idName+image.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(oFile);
             InputStream inputStream = image.getInputStream()) {

            Pattern png = Pattern.compile(".+\\.png");
            Pattern jpg = Pattern.compile(".+\\.jpg");
            Pattern jpeg = Pattern.compile(".+\\.jpeg");

            if (
                    !(png.matcher(image.getOriginalFilename()).matches() ||
                            jpg.matcher(image.getOriginalFilename()).matches() ||
                            jpeg.matcher(image.getOriginalFilename()).matches())
            ) {
                throw new BadRequestException(BAD_REQUEST_IMAGE);
            }

            IOUtils.copy(inputStream, os);

            Image newImage = new Image();
            newImage.setName(image.getOriginalFilename());
            newImage.setUrl(imageFolderName+"/"+idName+image.getOriginalFilename());


            BlogPost bP = blogPostRepo.findById(idBlogPost).orElseThrow(() -> new BadRequestException("Cannot find BlogPost with id: "+idBlogPost));
            newImage.setBlogPost(bP);
            imageRepo.save(newImage);

            return newImage;
        } catch (IOException e) {
            throw new BadRequestImageException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public String load(Long imgId) throws IOException {

        Image image = getImageById(imgId);

        File file = new File(image.getUrl());
        if (!file.exists())  file = new File("uploads/error.png");


        InputStream iS = new FileInputStream(file);
        byte[] imageBytes = new byte[(int)file.length()];
        iS.read(imageBytes, 0, imageBytes.length);
        iS.close();
        return Base64.encodeBase64String(imageBytes);
    }

    private Image getImageById(Long imgId){
        return imageRepo.findById(imgId)
                .orElseThrow(() -> new NotFoundException("Cannot find Image with id: "+imgId));
    }

    public void delete(Long imageId) {
        Image image = imageRepo.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Cannot find image by id:"+imageId));

//        image.setBlogPost(null);
//        image.setVisible(!image.isVisible());

        try {
            File file = new File(image.getUrl());
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Unable to delete the file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageRepo.delete(image);
    }
}