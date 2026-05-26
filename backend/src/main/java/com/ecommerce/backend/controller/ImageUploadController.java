package com.ecommerce.backend.controller;

import com.ecommerce.backend.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService
            imageUploadService;

    @PostMapping
    public Map<String, String> uploadImage(

            @RequestParam("file")
            MultipartFile file
    ) {

        String imageUrl =
                imageUploadService
                        .uploadImage(file);

        return Map.of(
                "imageUrl",
                imageUrl
        );
    }
}
