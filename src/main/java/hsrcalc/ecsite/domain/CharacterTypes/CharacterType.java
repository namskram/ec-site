package hsrcalc.ecsite.domain.CharacterTypes;

import java.util.List;
import java.util.Map;

/* 
 * Characters types are chosen based off of Prydwen's specifications.
 * Different classes of characters will have different builds.
 * Stat weightings are chosen based off of MobileMeta's specifications.
 * Different characters will have different weightings.
 */

public abstract class CharacterType {
    private String name;
    private Map<String, Double> avgRolls;
    private Map<String, Double> weights;
    private String element;
    private String path;

    public String getName() {
        return this.name;
    }

    public Map<String, Double> getWeights() {
        return this.weights;
    }

    public Map<String, Double> getAvgRolls() {
        return this.avgRolls;
    }

    public String getElement() {
        return this.element;
    }

    public String getPath() {
        return this.path;
    }


}