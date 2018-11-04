package jp.aymandev.astolfo.rpg.quests;

/**
 * Created by AymanDev on 30.07.2018.
 */
public class Quest {

    private String localizedName;
    private int time;
    private String description = "";
    private float questMod = 1.0f;

    public Quest(String localizedName, int time) {
        this.localizedName = localizedName;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public Quest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public int getTime() {
        return time;
    }

    public float getQuestMod() {
        return questMod;
    }

    public Quest setQuestMod(float questMod) {
        this.questMod = questMod;
        return this;
    }
}
