package com.kolaykira.webapp.image;

import com.google.cloud.storage.*;
import com.kolaykira.webapp.firebase.FirebaseInitialize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.net.URLEncoder;
import java.util.UUID;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class ImageService {
    private Storage storage;

    public ImageService() throws IOException {
        InputStream inputStream = FirebaseInitialize.JsonCreator();
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    public String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("yeview-b01f7.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = FirebaseInitialize.JsonCreator();
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        // Assuming you have all the correct settings to make the base64 publicly accessible
        String publicUrl = String.format("https://firebasestorage.googleapis.com/v0/b/yeview-b01f7.appspot.com/o/%s?alt=media",
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        return publicUrl;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original base64 name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for base64 name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String URL = this.uploadFile(file, fileName);                                   // to get uploaded base64 link
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

    public byte[] downloadFile(String fileName) throws IOException {
        BlobId blobId = BlobId.of("yeview-b01f7.appspot.com", fileName);
        Storage storage = getStorage();
        Blob blob = storage.get(blobId);
        if (blob == null) {
            throw new FileNotFoundException("No base64 found with the name: " + fileName);
        }
        return blob.getContent();
    }

    private Storage getStorage() throws IOException {
        InputStream inputStream = FirebaseInitialize.JsonCreator();
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    public String getPublicUrl(String fileName) throws IOException {
        // Assumes base64 names are unique in the bucket
        String bucketName = "yeview-b01f7.appspot.com"; // Your bucket name
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        if (blob == null) {
            throw new FileNotFoundException("No base64 found with the name: " + fileName);
        }

        // Check if the object's metadata contains the direct link
        if (blob.getMediaLink() == null) {
            throw new IllegalStateException("No accessible URL. Make sure the base64 is public or has a signed URL.");
        }

        // Encode the base64 name to ensure it's a valid URL component
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, encodedFileName);
    }
}
