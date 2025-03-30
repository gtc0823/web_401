package com.example.loginsystem.controller;

import com.example.loginsystem.model.FileInfo;
import com.example.loginsystem.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/document_management")
    public String listUploadedFiles(Model model) {
        List<FileInfo> fileInfos = fileStorageService.loadAllFileInfo();
        model.addAttribute("files", fileInfos);
        // Add empty message attributes in case they are not set by a redirect
        if (!model.containsAttribute("message")) {
            model.addAttribute("message", null);
        }
        if (!model.containsAttribute("errorMessage")) {
             model.addAttribute("errorMessage", null);
        }
        return "document_management"; // Return the template name
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            fileStorageService.store(file);
            redirectAttributes.addFlashAttribute("message",
                    "檔案上傳成功: " + file.getOriginalFilename());
        } catch (Exception e) {
             System.err.println("File upload failed: " + e.getMessage()); // Log the error
             redirectAttributes.addFlashAttribute("errorMessage",
                    "檔案上傳失敗: " + file.getOriginalFilename() + ". 原因: " + e.getMessage());
        }
        return "redirect:/document_management"; // Redirect back to the listing page
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody // Indicates the return value should be bound to the web response body
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileStorageService.loadAsResource(filename);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        // Ensure the browser prompts for download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

     @GetMapping("/delete/{filename:.+}")
    public String deleteFile(@PathVariable String filename, RedirectAttributes redirectAttributes) {
        try {
            fileStorageService.delete(filename);
            redirectAttributes.addFlashAttribute("message", "檔案刪除成功: " + filename);
        } catch (Exception e) {
             System.err.println("File deletion failed: " + e.getMessage()); // Log the error
             redirectAttributes.addFlashAttribute("errorMessage", "檔案刪除失敗: " + filename + ". 原因: " + e.getMessage());
        }
        return "redirect:/document_management"; // Redirect back to the listing page
    }
}
