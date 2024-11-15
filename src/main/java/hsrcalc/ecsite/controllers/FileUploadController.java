package hsrcalc.ecsite.controllers;

import hsrcalc.ecsite.domain.Calculation;
import hsrcalc.ecsite.repositories.CalculationRepository;
import hsrcalc.ecsite.service.CalculationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/calculations")
public class FileUploadController {

    private final CalculationRepository calculationRepository;
    private final CalculationService calculationService;
    private final Path uploadsDir = Paths.get("./uploads");

    @Autowired
    public FileUploadController(CalculationRepository calculationRepository, CalculationService calculationService) {
        this.calculationRepository = calculationRepository;
        this.calculationService = calculationService;
    }

    // Endpoint to save a new calculation entry
    @PostMapping("/add")
    public Calculation addCalculation(@RequestParam String characterName,
                                      @RequestParam double result,
                                      @RequestParam String filePath) {
        Calculation calculation = new Calculation(characterName, result, filePath, LocalDateTime.now());
        return calculationRepository.save(calculation);
    }

    // Endpoint to retrieve file uploads
    @GetMapping("/uploads/{filename:.+}")
    public Resource getFile(@PathVariable String filename) {
        try {
            Path file = uploadsDir.resolve(filename);
            return new UrlResource(file.toUri());
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("selectedOption") String selectedOption,
            @RequestParam("file") MultipartFile file) throws IOException, NoSuchMethodException,
                                                                ClassNotFoundException, InstantiationException,
                                                                IllegalAccessException, IllegalArgumentException,
                                                                InvocationTargetException {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("{\"error\": \"No file uploaded\"}");
            }

            String uploadsDirPath = "C:\\Users\\Brandon Du\\.vscode\\ec-site\\ecsite\\uploads"; // Absolute path
            File uploadDir = new File(uploadsDirPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Create directory if it doesn't exist
            }

            String fileName = file.getOriginalFilename();
            File saveFile = new File(uploadDir, fileName);
            file.transferTo(saveFile);
            
            double result = calculationService.calculate(selectedOption, saveFile);
            String color = getColorBasedOnResult(result);

            // Create a new Calculation object and save it to the database
            Calculation calculation = new Calculation(selectedOption, result, fileName, LocalDateTime.now());
            calculationRepository.save(calculation);

            // Return response data in JSON format
            Map<String, Object> response = new HashMap<>();
            response.put("result", result);
            response.put("color", color);
            response.put("characterName", selectedOption);
            response.put("filePath", fileName);
            response.put("date", calculation.getDate());

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            System.err.println("Failed to save file due to: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("File could not be saved: " + e.getMessage());
        }
    }

    private String getColorBasedOnResult(double result) {
        if (result < 5) {
            return "red";
        } else if (result >= 5 && result < 9) {
            return "orange";
        } else if (result >= 9 && result < 12) {
            return "yellow";
        } else if (result >= 12 && result < 15) {
            return "green";
        } else {
            return "cyan";
        }
    }

    @GetMapping("/recent")
    public List<Calculation> getRecentCalculations() {
        return calculationRepository.findAll(Sort.by(Sort.Direction.DESC, "date")); // Sorted by date, newest first
    }
}