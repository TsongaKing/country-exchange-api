package com.backendwizards.country.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/countries")
public class ImageController {

    @GetMapping("/image")
    public ResponseEntity<?> getImage() {
        File f = new File("cache/summary.png");
        if (!f.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Summary image not found"));
        }
        FileSystemResource res = new FileSystemResource(f);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(res);
    }
}

