package hsrcalc.ecsite.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("selectedOption") String selectedOption,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Set the uploads directory to a known location in your project
        String uploadsDirPath = "C:\\Users\\Brandon Du\\.vscode\\ec-site\\ecsite\\uploads"; // Relative to the application root
        File uploadDir = new File(uploadsDirPath);

        // Create the uploads directory if it doesn't exist
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs(); // Create the directory
            if (!created) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("Failed to create uploads directory.");
            }
        }

        // Log the selected option (character name)
        System.out.println("Selected Character: " + selectedOption);

        // Handle the file upload
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            System.out.println("Uploaded File Name: " + fileName);

            // Save the file to the uploads directory
            File saveFile = new File(uploadDir, fileName);
            file.transferTo(saveFile);

            System.out.println("File saved at: " + saveFile.getAbsolutePath());
        }

        return ResponseEntity.ok("File and selected option processed successfully!");
    }
}