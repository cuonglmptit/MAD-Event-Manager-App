package com.cuonglmptit.SpringServer.controller;

import com.cuonglmptit.SpringServer.model.filedata.FileDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class FileController {

    @Autowired
    private FileDataDAO fileDataDAO;

    @PostMapping("/fileSystem")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = fileDataDAO.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = fileDataDAO.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
}
