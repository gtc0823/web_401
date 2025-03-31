package com.example.loginsystem.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.loginsystem.model.FileInfo; // We'll create this model next

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    // Define the root path for uploads relative to the project structure
    private final Path rootLocation = Paths.get("login_web_practice/uploads");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            System.out.println("Upload directory created at: " + rootLocation.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            // Use REPLACE_EXISTING to overwrite if file already exists
            Files.copy(file.getInputStream(), destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

     public List<FileInfo> loadAllFileInfo() {
        try (Stream<Path> pathStream = Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation) && Files.isRegularFile(path))) { // Ensure it's a file

            return pathStream.map(path -> {
                try {
                    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                    Instant instant = attrs.creationTime().toInstant();
                    LocalDateTime creationTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    long size = attrs.size();
                    String name = path.getFileName().toString();
                    return new FileInfo(name, creationTime, size);
                } catch (IOException e) {
                    System.err.println("Error reading attributes for file: " + path + " - " + e.getMessage());
                    // Return null or a special FileInfo object to indicate error, or filter out later
                    return null;
                }
            })
            .filter(fileInfo -> fileInfo != null) // Filter out nulls from errors
            .sorted(Comparator.comparing(FileInfo::getCreationTime).reversed()) // Sort by creation time descending
            .collect(Collectors.toList());

        } catch (IOException e) {
            System.err.println("Failed to read stored files: " + e.getMessage());
            throw new RuntimeException("Failed to read stored files", e);
        }
    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public void delete(String filename) {
         try {
            Path file = load(filename); // Resolves filename against rootLocation
            Path absoluteFile = file.toAbsolutePath().normalize(); // Normalize to handle potential ".." etc.
            Path absoluteRoot = this.rootLocation.toAbsolutePath().normalize();

            // Security check: Ensure the file to be deleted is directly within the upload directory
            if (!absoluteFile.getParent().equals(absoluteRoot)) {
                 System.err.println("Security check failed: File parent [" + absoluteFile.getParent() + "] does not match root [" + absoluteRoot + "]");
                 throw new RuntimeException(
                        "Security check failed: Attempt to delete file outside storage directory.");
            }
            Files.deleteIfExists(absoluteFile); // Use the absolute path for deletion
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + filename, e);
        }
    }

    // Optional: Method to delete all files (useful for cleanup)
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
