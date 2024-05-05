package com.kolaykira.webapp.image;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/image")
@CrossOrigin
public class ImageController {
    private final ImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody ImageRequest request) {
        try {

            String url = imageService.uploadFile(convertBase64StringToFile(request.getBase64()), request.getFileName());
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to upload image due to an IO error");
        }
    }

    public File convertBase64StringToFile(String base64String) throws IOException {
        // Decode Base64 string to byte array
        byte[] decodedBytes = Base64.decodeBase64(base64String);

        // Create a temporary file
        File tempFile = File.createTempFile("temp-file", ".tmp");

        // Write the byte array to the temporary file
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            FileCopyUtils.copy(decodedBytes, fos);
        }

        return tempFile;
    }
    // Method to convert MultipartFile to File

    @GetMapping("/get-url/{fileName}")
    public ResponseEntity<String> getImageUrl(@PathVariable String fileName) {
        try {
            String imageUrl = imageService.getPublicUrl(fileName);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }
}

