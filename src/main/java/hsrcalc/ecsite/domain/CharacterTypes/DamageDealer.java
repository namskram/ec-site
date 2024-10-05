package hsrcalc.ecsite.domain.CharacterTypes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamageDealer extends CharacterType {
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
    }};;
    private String element;
    private String path;

    public DamageDealer(String name) {
        this.name = name;
        setChars();
        setWeights();

        // FOR DEBUGGING
        // System.out.println("Initializing Damage Dealer: " + name);
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

    public static List<String> getChars() {
        List<String> list = Arrays.asList("Acheron", "DHIL", "Jingliu", "Dr. Ratio", 
                                                "Clara", "Jing Yuan", "Qingque", "Seele", 
                                                "Blade", "Argenti", "Himeko", "Misha",
                                                "Serval", "Dan Heng", "Herta", "Hook", 
                                                "Yanqing", "Arlan", "DMC");
        return list;
    }

    private void setChars() {
        getChars();
        
    }

    private void setWeights() {
        weights.put("CRIT Rate%", 2.0);
        weights.put("CRIT DMG%", 2.0);
        weights.put("SPD", 2.0);
        weights.put("ATK%", 2.0);
        weights.put("ATK", 1.0);
        weights.put("Break Effect%", 1.0);
    }
}
