package com.cuonglmptit.SpringServer.model.filedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileDataDAO {
    @Autowired
    FileDataRepository fileDataRepository;
    private final String FOLDER_PATH = "src/main/resources/static/images/";

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = FOLDER_PATH + fileName;

        FileData fileData = new FileData(fileName, file.getContentType(), filePath);

        Path directory = Paths.get(FOLDER_PATH);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        InputStream inputStream = file.getInputStream();
        Files.copy(inputStream, directory.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);


        if (fileData != null) {
            fileDataRepository.save(fileData);
            return "File uploaded successfully: " + filePath;
        }
        return null;
    }


    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        FileData fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
