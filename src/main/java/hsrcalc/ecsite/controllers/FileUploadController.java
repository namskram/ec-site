package hsrcalc.ecsite.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("selectedOption") String selectedOption,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Access the selected option
        System.out.println("Selected Option: " + selectedOption);

        // Access the uploaded file
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            System.out.println("Uploaded File Name: " + fileName);

            // Save the file to a local directory (e.g., "uploads" folder)
            File saveFile = new File("uploads/" + fileName);
            file.transferTo(saveFile);

            System.out.println("File saved at: " + saveFile.getAbsolutePath());
        }

        return ResponseEntity.ok("File and selected option processed successfully!");
    }
}