package hsrcalc.ecsite.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hsrcalc.ecsite.domain.CharacterTypes.Amplifier;
import hsrcalc.ecsite.domain.CharacterTypes.DamageDealer;
import hsrcalc.ecsite.domain.CharacterTypes.Specialist;
import hsrcalc.ecsite.domain.CharacterTypes.Sustain;
import hsrcalc.ecsite.service.ImagePreprocessingService;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class CalculationService {

    public double calculate(String charName, File file) 
                                            throws IOException, NoSuchMethodException, ClassNotFoundException, 
                                            InstantiationException, IllegalAccessException, 
                                            IllegalArgumentException, InvocationTargetException {
        
        System.out.println("Service is working:");
        System.out.println("Character: " + charName);
        System.out.println("File Name: " + file.getName());
        System.out.println("FIle Path: " + file.getAbsolutePath());

        String newFilePath = ImagePreprocessingService.modifyImage(file.getAbsolutePath());
        System.out.println("New File Path: " + newFilePath);

        File newFile = new File(newFilePath);

        double score = ExtractTextAndNumbers(charName, newFile);
        return score;
    }

    public static double ExtractTextAndNumbers(String charName, File file) 
                                            throws IOException, NoSuchMethodException, ClassNotFoundException, 
                                            InstantiationException, IllegalAccessException, 
                                            IllegalArgumentException, InvocationTargetException {

        ITesseract tesseract = new Tesseract();
        try {
            String text = tesseract.doOCR(file);

            // FOR DEBUGGING
            // System.out.println("Original:");
            // System.out.println(text);
            
            String[] keywords = {"ATK", "DEF", "HP", "CRIT DMG", "CRIT Rate", "Break Effect", 
                                "Effect Hit Rate", "Effect RES", "SPD"};
            String filteredText = includeStringsWithPercentage(text, keywords);

            // FOR DEBUGGING
            System.out.println();
            System.out.println("Overall Stats:");
            System.out.println(filteredText + "\n");

            Map<String, Double> vals = filteredTextToDictionary(filteredText);
            // Ignore main stats, assuming BIS already
            Map<String, Double> newVals = removeFirstPair(vals);

            // FOR DEBUGGING
            // System.out.println("test: " + newVals);

            Double score = calculateScores(charName, newVals);
            Double roundedScore = round(score, 2);
            // System.out.println("Relic Score: " + roundedScore);
            // System.out.println("------------------------------ \n");

            return roundedScore;
            

        } catch (TesseractException e) {
            System.err.println("Error during OCR: " + e.getMessage());
        }
        return 0;
    }

    public static String includeStringsWithPercentage(String text, String[] includeStrings) {
        StringBuilder filteredText = new StringBuilder();
        // Normalize line endings and remove extra spaces/newlines
        text = text.replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("\\s{2,}", " ").trim();
        // FOR DEBUGGING
        // System.out.println("regex text: " + text);
        
        for (String includeString : includeStrings) {
            // Escape special characters in the keyword for regex
            String escapedIncludeString = Pattern.quote(includeString);
            // Regex pattern to match the keyword, followed by optional non-word characters, then the number (with or without percentage)
            Pattern pattern = Pattern.compile(escapedIncludeString + "\\s*[^\\d]*\\d+\\.?\\d*%?");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String match = matcher.group();
                // Extract the percentage or number part
                Pattern numberPattern = Pattern.compile("(\\d+\\.?\\d*%?)");
                Matcher numberMatcher = numberPattern.matcher(match);
                if (numberMatcher.find()) {
                    String cleanMatch = numberMatcher.group();
                    filteredText.append(includeString).append(" ").append(cleanMatch).append("\n");
                }
            }
        }
        return filteredText.toString().trim();
    }

    public static String classChecker(String charName) {
        if (DamageDealer.getChars().contains(charName)) {
            return "DamageDealer";
        }
        
        if (Specialist.getChars().contains(charName)) {
            return "Specialist";
        }

        if (Amplifier.getChars().contains(charName)) {
            return "Amplifier";
        }

        if (Sustain.getChars().contains(charName)) {
            return "Sustain";
        }
        return "No such character";
    }

    // Method to convert filtered text to a dictionary
    public static Map<String, Double> filteredTextToDictionary(String filteredText) {
        Map<String, Double> resultMap = new LinkedHashMap<>(); // Using LinkedHashMap to maintain order
        String[] lines = filteredText.split("\\n");
        
        for (String line : lines) {
            // FOR DEBUGGING
            // System.out.println("Processing line: " + line);
            int lastSpaceIndex = line.lastIndexOf(' ');
            if (lastSpaceIndex == -1) {
                // FOR DEBUGGING
                System.err.println("No space found in line: " + line);
                continue;
            }
            String key = line.substring(0, lastSpaceIndex).trim();
            String valueStr = line.substring(lastSpaceIndex + 1).trim();
            boolean isPercentage = valueStr.endsWith("%");
            if (isPercentage) {
                valueStr = valueStr.substring(0, valueStr.length() - 1); // Remove percentage sign
                key += "%"; // Append percentage sign to key
            }
            try {
                double value = Double.parseDouble(valueStr);
                resultMap.put(key, value);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for: " + valueStr);
            }
        }
        
        return resultMap;
    }

    // Method to create a new dictionary without the first pair
    public static Map<String, Double> removeFirstPair(Map<String, Double> originalMap) {
        Map<String, Double> newMap = new LinkedHashMap<>(); // Using LinkedHashMap to maintain order
        boolean firstPair = true;
        for (Map.Entry<String, Double> entry : originalMap.entrySet()) {
            if (firstPair) {
                firstPair = false; // Skip the first entry
                continue;
            }
            newMap.put(entry.getKey(), entry.getValue());
        }
        return newMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static double calculateScores(String charName, Map<String, Double> stats) 
                                              throws NoSuchMethodException, ClassNotFoundException, InstantiationException, 
                                              IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        String classType = classChecker(charName);
        // FOR DEBUGGING
        // System.out.println("class type: " + classType);

        String className = "hsrcalc.ecsite.domain.CharacterTypes." + classType;
        Class cl = Class.forName(className);

        Method m1 = cl.getMethod("getWeights");
        Method m2 = cl.getMethod("getAvgRolls");

        Constructor con = cl.getConstructor(String.class);
        Object charObject = con.newInstance(charName);

        Double score = 0.0;
        Map<String, Double> weights = (Map<String, Double>)m1.invoke(charObject);
        Map<String, Double> rolls = (Map<String, Double>)m2.invoke(charObject);

        // FOR DEBUGGING
        /*
        System.out.println("Weights:");
        for (Map.Entry<String, Double> entry: weights.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        System.out.println();
        System.out.println("Avg Rolls:");
        for (Map.Entry<String, Double> entry: rolls.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        */

        for (Map.Entry<String, Double> entry: stats.entrySet()) {
            String stat = entry.getKey();
            Double val = entry.getValue();
            Double weight = weights.get(stat);
            Double roll = rolls.get(stat);
            score += ((val/roll) * weight);
        }

        return score;
    }

    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
    }

}