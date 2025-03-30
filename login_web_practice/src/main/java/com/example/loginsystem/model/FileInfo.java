package com.example.loginsystem.model;

import java.time.LocalDateTime;

public class FileInfo {
    private String name;
    private LocalDateTime creationTime;
    private long size; // Size in bytes

    public FileInfo(String name, LocalDateTime creationTime, long size) {
        this.name = name;
        this.creationTime = creationTime;
        this.size = size;
    }

    // Getters
    public String getName() {
        return name;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public long getSize() {
        return size;
    }

    // Optional Setters if needed, but likely not for this use case
    public void setName(String name) {
        this.name = name;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
