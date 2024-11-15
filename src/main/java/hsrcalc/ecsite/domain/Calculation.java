package hsrcalc.ecsite.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String characterName;
    private double result;
    private String filePath;
    private LocalDateTime date;

    // Constructors, getters, and setters
    public Calculation() {}

    public Calculation(String characterName, double result, String filePath, LocalDateTime date) {
        this.characterName = characterName;
        this.result = result;
        this.filePath = filePath;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getCharacterName() {
        return characterName;
    }

    public double getResult() {
        return result;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}