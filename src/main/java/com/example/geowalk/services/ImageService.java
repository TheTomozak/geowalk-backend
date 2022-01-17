package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class ImageService {

    private final Path root = Paths.get("uploads");
    private final String BAD_REQUEST_IMAGE = "Image request has bad body";

    public void init() {
        if (!Files.exists(root)) {
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }
    }

    public void save(MultipartFile image) {
        try {
            Pattern png = Pattern.compile(".+\\.png");
            Pattern jpg = Pattern.compile(".+\\.jpg");
            Pattern jpeg = Pattern.compile(".+\\.jpeg");

            if(
                    !(png.matcher(image.getOriginalFilename()).matches() ||
                    jpg.matcher(image.getOriginalFilename()).matches() ||
                    jpeg.matcher(image.getOriginalFilename()).matches())
            ){
                throw new BadRequestException(BAD_REQUEST_IMAGE);
            }

            Files.copy(image.getInputStream(), this.root.resolve(image.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            System.out.println(file);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}