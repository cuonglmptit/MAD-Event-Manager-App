package com.cuonglmptit.SpringServer.model.filedata;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "filedata")
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String filePath;

    public FileData() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFilePath() {
        return filePath;
    }

    public FileData(String name, String type, String filePath) {
        this.name = name;
        this.type = type;
        this.filePath = filePath;
    }
}
