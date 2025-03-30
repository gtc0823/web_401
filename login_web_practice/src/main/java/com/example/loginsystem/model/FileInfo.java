package com.example.loginsystem.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    
    private String name;
    private LocalDateTime creationTime;
    private long size; // Size in bytes
}
