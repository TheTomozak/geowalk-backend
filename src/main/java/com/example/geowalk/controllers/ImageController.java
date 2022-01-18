package com.example.geowalk.controllers;

import com.example.geowalk.models.entities.Image;
import com.example.geowalk.services.ImageService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            imageService.save(image);
            logger.info("Uploaded the image successfully: " + image.getOriginalFilename());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.info("Could not upload the image: " + image.getOriginalFilename() + "!");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Image>> getListImages() {
        List<Image> imageList = imageService.loadAll().map(path -> {
            String image = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString()).build().toString();

            return new Image(image, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(imageList);
    }

    @GetMapping("/{imageName:.+}")
    @ResponseBody
    public ResponseEntity<?> getImage(@PathVariable String imageName) throws IOException {
        Resource image = imageService.load(imageName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; image=\"" + image.getFilename() + "\"").body(image);
    }


    @PostMapping("/up2")
    @ResponseBody            // 1
    public String handleFile(@RequestPart(name = "fileupload") MultipartFile file) { // 2
        File uploadDirectory = new File("uploads");
        uploadDirectory.mkdirs();    // 3

        File oFile = new File("uploads/" + file.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(oFile);
             InputStream inputStream = file.getInputStream()) {

            IOUtils.copy(inputStream, os); // 4
        } catch (IOException e) {
            e.printStackTrace();
            return "Wystąpił błąd podczas przesyłania pliku: " + e.getMessage();
        }

        return "ok!";
    }

    @GetMapping("im2/{name}")
    public ResponseEntity<?> showImage(@PathVariable String name) throws IOException {
        File file = new File("uploads/" + name);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(name)))
                .body(Files.readAllBytes(file.toPath()));
    }
}