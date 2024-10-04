package hsrcalc.ecsite.controllers;

import hsrcalc.ecsite.service.CalculationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.boot.autoconfigure.ssl.SslProperties;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("selectedOption") String selectedOption,
            @RequestParam("file") MultipartFile file) 
                                                    throws IOException, NoSuchMethodException,
                                                    ClassNotFoundException, InstantiationException,
                                                    IllegalAccessException, IllegalArgumentException,
                                                    InvocationTargetException {

        String uploadsDirPath = "C:\\Users\\Brandon Du\\.vscode\\ec-site\\ecsite\\uploads"; // Absolute path
        File uploadDir = new File(uploadsDirPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create directory if it doesn't exist
        }

        // Log the selected option
        System.out.println("Selected Character: " + selectedOption);

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            System.out.println("Uploaded File Name: " + fileName);

            // Save the file
            File saveFile = new File(uploadDir, fileName);
            file.transferTo(saveFile);

            System.out.println("File saved at: " + saveFile.getAbsolutePath());
            CalculationService.calculate(selectedOption, saveFile);
        }

        // Return JSON response with success message
        return ResponseEntity.ok().body("{\"message\":\"File and selected option processed successfully!\"}");
    }
}