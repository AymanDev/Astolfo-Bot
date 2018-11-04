package jp.aymandev.astolfo.rpg.pets;

import jp.aymandev.astolfo.rpg.pets.talents.PetTalent;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;

import java.util.LinkedHashMap;

/**
 * Created by AymanDev on 01.08.2018.
 */
public class Pet {

    private String localizedName;
    private String description;
    private LinkedHashMap<String, Integer> characteristics = new LinkedHashMap<>();
    private LinkedHashMap<Integer, PetTalent> availableTalents = new LinkedHashMap<>();

    public Pet(String localizedName) {
        this.localizedName = localizedName;

        setCharacteristic(EnumCharacteristic.INTELLIGENCE, 0);
        setCharacteristic(EnumCharacteristic.AGILITY, 0);
        setCharacteristic(EnumCharacteristic.ENDURANCE, 0);
        setCharacteristic(EnumCharacteristic.STRENGTH, 0);
    }

    public Pet addCharacteristic(EnumCharacteristic characteristic, int amount) {
        int currentCharacteristic = getCharacteristic(characteristic);
        currentCharacteristic += amount;
        setCharacteristic(characteristic, currentCharacteristic);
        return this;
    }

    public Pet setCharacteristic(EnumCharacteristic characteristic, int amount) {
        characteristics.put(characteristic.name(), amount);
        return this;
    }

    public int getCharacteristic(EnumCharacteristic characteristic) {
        return characteristics.get(characteristic.name());
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getDescription() {
        return description;
    }

    public Pet setDescription(String description) {
        this.description = description;
        return this;
    }

    public Pet addTalent(PetTalent petTalent, int level) {
        availableTalents.put(level, petTalent);
        return this;
    }

    public LinkedHashMap<Integer, PetTalent> getAvailableTalents() {
        return availableTalents;
    }
}
