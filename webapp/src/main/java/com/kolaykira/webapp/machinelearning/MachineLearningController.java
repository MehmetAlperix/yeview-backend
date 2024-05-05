package com.kolaykira.webapp.machinelearning;

import com.kolaykira.webapp.image.ImageRequest;
import com.kolaykira.webapp.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/machinelearning")
@CrossOrigin
public class MachineLearningController {

/*
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
*/
}

