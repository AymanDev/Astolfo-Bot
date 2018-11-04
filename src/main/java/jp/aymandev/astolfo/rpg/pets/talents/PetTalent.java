package jp.aymandev.astolfo.rpg.pets.talents;

import jp.aymandev.astolfo.rpg.pets.RPGPet;

/**
 * Created by AymanDev on 01.08.2018.
 */
public class PetTalent {

    private String localizedName;
    private String description = "";

    public PetTalent(String localizedName) {
        this.localizedName = localizedName;
    }

    public void onTalentLearned(RPGPet pet) {

    }

    public int onTalentPowerCalculate(RPGPet pet) {
        return 0;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getDescription() {
        return description;
    }

    public PetTalent setDescription(String description) {
        this.description = description;
        return this;
    }
}
