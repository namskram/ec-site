package hsrcalc.ecsite.domain.CharacterTypes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Specialist extends CharacterType {
    private final String name;
    private final Map<String, Double> avgRolls = new HashMap<String, Double>() {{
        put("SPD", 2.3);
        put("HP", 38.0);
        put("ATK", 19.0);
        put("DEF", 19.0);
        put("HP%", 3.9);
        put("ATK%", 3.9);
        put("DEF%", 4.9);
        put("Break Effect%", 5.8);
        put("Effect Hit Rate%", 3.9);
        put("Effect RES%", 3.9);
        put("CRIT Rate%" , 2.9);
        put("CRIT DMG%" , 5.8);
    }};
    private final Map<String, Double> weights = new HashMap<String, Double>() {{
        put("SPD", 0.0);
        put("HP", 0.0);
        put("ATK", 0.0);
        put("DEF", 0.0);
        put("HP%", 0.0);
        put("ATK%", 0.0);
        put("DEF%", 0.0);
        put("Break Effect%", 0.0);
        put("Effect Hit Rate%", 0.0);
        put("Effect RES%", 0.0);
        put("CRIT Rate%" , 0.0);
        put("CRIT DMG%" , 0.0);
    }};
    private Map<String, String> types;
    private String element;
    private String path;
    private String type;

    public Specialist(String name) {
        this.name = name;
        setChars();
        setTypes();
        setType(name);
        setWeights();

        // FOR DEBUGGING
        // System.out.println("Initializing Specialist: " + name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Map<String, Double> getWeights() {
        return this.weights;
    }

    @Override
    public Map<String, Double> getAvgRolls() {
        return this.avgRolls;
    }

    @Override
    public String getElement() {
        return this.element;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, String> getTypes() {
        return this.types;
    }

    public static List<String> getChars() {
        List<String> list = Arrays.asList("Firefly", "Black Swan", "Boothill", "Kafka", 
                                                "Topaz", "Luka", "Sampo", "Xueyi", "Guinafen",
                                                "Jade", "Welt", "Sushang", "PMC");
        return list;
    }

    private void setTypes() {
        this.types = new HashMap<String, String>() {{
            put("Firefly", "Break");
            put("Black Swan", "DOT");
            put("Boothill", "Break");
            put("Kafka", "DOT");
            put("Topaz", "Generic");
            put("Luka", "DOT");
            put("Sampo", "DOT");
            put("Xueyi", "Break");
            put("Guinaifen", "DOT");
            put("Jade", "Generic");
            put("Welt", "Generic");
            put("Sushang", "Break");
            put("PMC", "Break");
        }};
    }

    private void setType(String charName) {
        this.type = types.get(charName);
    }

    private void setChars() {
        getChars();
    }

    private void setWeights() {
        if ("Break".equals(this.type)) {
            weights.put("Break Effect%", 2.0);
            weights.put("SPD", 2.0);
            weights.put("ATK%", 2.0);
            weights.put("ATK", 1.0);
            weights.put("HP%", 1.0);
            weights.put("DEF%", 1.0);
        }

        if ("DOT".equals(this.type)) {
            weights.put("SPD", 2.0);
            weights.put("ATK%", 2.0);
            weights.put("ATK", 1.0);
            weights.put("Effect Hit Rate%", 1.0);
            weights.put("Break Effect%", 1.0);
            weights.put("CRIT Rate%", 1.0);
            weights.put("CRIT DMG%", 1.0);
        }

        if ("Generic".equals(this.type)) {
            weights.put("CRIT Rate%", 2.0);
            weights.put("CRIT DMG%", 2.0);
            weights.put("SPD", 2.0);
            weights.put("ATK%", 1.0);
            weights.put("ATK", 1.0);
            weights.put("Break Effect%", 1.0);
        }
    }

}
