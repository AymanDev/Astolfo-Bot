package jp.aymandev.astolfo.rpg.pets;

import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.helpers.RPGHelper;
import jp.aymandev.astolfo.rpg.pets.talents.PetTalent;
import jp.aymandev.astolfo.rpg.pets.talents.PetTalentList;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by AymanDev on 01.08.2018.
 */
public class RPGPet {

    private Pet pet;
    private LinkedHashMap<String, Integer> characteristics;
    private HashMap<String, PetTalent> learnedTalents = new HashMap<>();
    private int escapeBuff;
    private int upgradePoints;

    private int experience;
    private int level;

    private RPGPlayer rpgPlayer;

    private JSONObject petData = new JSONObject();

    public RPGPet(RPGPlayer rpgPlayer, Pet pet) {
        this.rpgPlayer = rpgPlayer;
        characteristics = new LinkedHashMap<>();
        this.pet = pet;
        level = 1;
        experience = 0;
        upgradePoints = 10;
        setCharacteristic(EnumCharacteristic.INTELLIGENCE, pet.getCharacteristic(EnumCharacteristic.INTELLIGENCE));
        setCharacteristic(EnumCharacteristic.AGILITY, pet.getCharacteristic(EnumCharacteristic.AGILITY));
        setCharacteristic(EnumCharacteristic.ENDURANCE, pet.getCharacteristic(EnumCharacteristic.ENDURANCE));
        setCharacteristic(EnumCharacteristic.STRENGTH, pet.getCharacteristic(EnumCharacteristic.STRENGTH));
    }

    public boolean loadData(JSONObject playerData) {
        if (playerData.has("petData")) {
            petData = playerData.getJSONObject("petData");

            String petName = petData.getString("name");
            this.pet = PetList.pets.get(petName);

            level = petData.getInt("level");
            experience = petData.getInt("experience");
            escapeBuff = petData.getInt("escapeBuff");
            upgradePoints = petData.getInt("upgradePoints");

            loadCharacteristics(petData);
            loadTalents(petData);
            return true;
        }
        return false;
    }

    private void loadCharacteristics(JSONObject petData) {
        JSONObject characteristicsData = petData.getJSONObject("characteristics");
        for (String key : characteristicsData.keySet()) {
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(key);
            setCharacteristic(characteristic, characteristicsData.getInt(key));
        }
    }

    private void loadTalents(JSONObject petData) {
        JSONObject characteristicsData = petData.getJSONObject("talents");
        for (String key : characteristicsData.keySet()) {
            PetTalent petTalent = PetTalentList.getTalent(pet.getLocalizedName(), key);
            addTalent(petTalent);
        }
    }

    public JSONObject writeData(JSONObject playerData) {
        petData.put("name", pet.getLocalizedName().toLowerCase());
        petData.put("level", level);
        petData.put("experience", experience);
        petData.put("escapeBuff", escapeBuff);
        petData.put("characteristics", characteristics);
        petData.put("talents", learnedTalents);
        petData.put("upgradePoints", upgradePoints);

        return playerData.put("petData", petData);
    }


    public EmbedBuilder getPetEmbed() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setTitle(pet.getLocalizedName());
        embedBuilder.addField("Уровень:", level + ".lvl", false);
        embedBuilder.addField("Опыт:", experience + "/" + level * 1000, false);
        embedBuilder.addField("Очко улучшений:", upgradePoints + ".p", false);

        embedBuilder.addBlankField(false);

        embedBuilder.addField("Сила в ближнем бою:", calculateMeleePower() + ".power", true);
        embedBuilder.addField("Сила в магии:", calculateMagePower() + ".power", true);
        embedBuilder.addField("Сила талантов:", getTalentsPower() + ".power", true);
        embedBuilder.addField("Общая сила:", getPetPower() + getTalentsPower() + ".power", true);
        embedBuilder.addField("Шанс побега:", calculateEscapeChance() + "%", false);
        embedBuilder.addBlankField(false);

        for (String characteristicKey : characteristics.keySet()) {
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(characteristicKey);
            int value = getCharacteristic(characteristic);

            if (value > 0) {
                embedBuilder.addField(characteristic.localizedName + ":", value + ".p", true);
            }
        }

        if (!pet.getDescription().isEmpty()) {
            embedBuilder.addField("Описание: ", pet.getDescription(), false);
        }

        return embedBuilder;
    }

    public EmbedBuilder getPetTalentsEmbed() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setTitle("Таланты " + pet.getLocalizedName() + "a для изучения:");

        for (int level : pet.getAvailableTalents().keySet()) {
            PetTalent petTalent = pet.getAvailableTalents().get(level);
            embedBuilder.addField(level + ".lvl " + petTalent.getLocalizedName(), petTalent.getDescription(), false);
        }
        return embedBuilder;
    }

    public EmbedBuilder getPetLearnedTalentsEmbed() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setTitle("Изученные таланты " + pet.getLocalizedName() + "a:");
        embedBuilder.addBlankField(false);

        for (PetTalent petTalent : learnedTalents.values()) {
            embedBuilder.addField(petTalent.getLocalizedName(),
                    "Сила таланта: " + petTalent.onTalentPowerCalculate(this) + "\nОписание: " + petTalent.getDescription(), false);
        }
        return embedBuilder;
    }

    public void learnTalent(PetTalent petTalent) {
        petTalent.onTalentLearned(this);
        addTalent(petTalent);
    }

    public void addTalent(PetTalent petTalent) {
        learnedTalents.put(petTalent.getLocalizedName().toLowerCase(), petTalent);
    }

    public void removeTalent(PetTalent petTalent) {
        learnedTalents.remove(petTalent.getLocalizedName().toLowerCase());
    }

    public int calculateMeleePower() {
        return (int) ((getCharacteristic(EnumCharacteristic.STRENGTH) +
                getCharacteristic(EnumCharacteristic.AGILITY)) * 2.0f);
    }

    public int calculateMagePower() {
        return (int) (getCharacteristic(EnumCharacteristic.INTELLIGENCE) * 2.0f);
    }

    public int calculateEscapeChance() {
        return (int) (getCharacteristic(EnumCharacteristic.ENDURANCE) * 0.5f) + escapeBuff;
    }

    public int getPetPower() {
        return getCharacteristic(EnumCharacteristic.AGILITY) + getCharacteristic(EnumCharacteristic.STRENGTH) * 2;
    }

    public int getEscapeBuff() {
        return escapeBuff;
    }

    public RPGPet setEscapeBuff(int escapeBuff) {
        this.escapeBuff = escapeBuff;
        return this;
    }

    public int getTalentsPower() {
        return RPGHelper.calculatePetTalentsPower(this);
    }

    public void addCharacteristic(EnumCharacteristic characteristic, int amount, boolean consumePoints) {
        int currentCharacteristic = characteristics.get(characteristic.name());
        currentCharacteristic += amount;
        setCharacteristic(characteristic, currentCharacteristic);

        if (consumePoints) {
            upgradePoints -= amount;
        }
    }

    public void setCharacteristic(EnumCharacteristic characteristic, int amount) {
        characteristics.put(characteristic.name(), amount);
    }

    public int getCharacteristic(EnumCharacteristic characteristic) {
        if (characteristic.name().equalsIgnoreCase(EnumCharacteristic.STRENGTH.name())) {
            if (learnedTalents.containsKey("взросление")) {
                return characteristics.get(characteristic.name()) * 5;
            }
        }
        if (characteristic.name().equalsIgnoreCase(EnumCharacteristic.AGILITY.name())) {
            if (learnedTalents.containsKey("взросление")) {
                return characteristics.get(characteristic.name()) * 5;
            }
        }
        return characteristics.get(characteristic.name());
    }

    public Collection<PetTalent> getLearnedTalents() {
        return learnedTalents.values();
    }

    public int getExperience() {
        return experience;
    }

    public RPGPet setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public void addExperience(int amount) {
        experience += amount;


        levelUp();
    }

    public void levelUp() {
        int neededExperience = level * 1000;

        if (experience >= neededExperience) {
            experience -= neededExperience;
            level++;
            upgradePoints++;

            if (pet.getAvailableTalents().containsKey(level)) {
                learnTalent(pet.getAvailableTalents().get(level));
            }

            if (experience > neededExperience) {
                levelUp();
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public RPGPet setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getUpgradePoints() {
        return upgradePoints;
    }

    public RPGPlayer getRpgPlayer() {
        return rpgPlayer;
    }
}
