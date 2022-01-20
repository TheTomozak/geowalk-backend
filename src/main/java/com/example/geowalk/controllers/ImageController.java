package com.example.geowalk.controllers;

import com.example.geowalk.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/{bPId}")
    @ResponseBody
    public ResponseEntity<?> updateFile(@RequestPart(name = "fileupload")  MultipartFile file, @PathVariable Long bPId) {
        imageService.save(file, "uploads", bPId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("load/{imageId}")
    public ResponseEntity<?> showImage(@PathVariable Long imageId) throws IOException {
        return ResponseEntity.ok().body(imageService.load(imageId));
    }

    @DeleteMapping("delete/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId){
        imageService.delete(imageId);
        return ResponseEntity.ok().build();
    }
}