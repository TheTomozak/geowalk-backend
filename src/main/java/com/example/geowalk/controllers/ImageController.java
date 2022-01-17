package com.example.geowalk.controllers;

import com.example.geowalk.models.entities.Image;
import com.example.geowalk.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/image")
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

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getListImages() {
        List<Image> imageList = imageService.loadAll().map(path -> {
            String image = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString()).build().toString();

            return new Image(image, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(imageList);
    }

    @GetMapping("/images/{imageName:.+}")
    @ResponseBody
    public ResponseEntity<?> getImage(@PathVariable String imageName) throws IOException {
        Resource image = imageService.load(imageName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; image=\"" + image.getFilename() + "\"").body(image);
    }
}