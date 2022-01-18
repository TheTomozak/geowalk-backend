package com.example.geowalk.controllers;

import com.example.geowalk.exceptions.BadRequestImageException;
import com.example.geowalk.models.entities.Image;
import com.example.geowalk.services.ImageService;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
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
}