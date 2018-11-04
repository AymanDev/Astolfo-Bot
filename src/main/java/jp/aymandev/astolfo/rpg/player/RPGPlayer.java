package jp.aymandev.astolfo.rpg.player;

import jp.aymandev.astolfo.AstolfoBot;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.helpers.RPGHelper;
import jp.aymandev.astolfo.rpg.items.ItemList;
import jp.aymandev.astolfo.rpg.pets.PetList;
import jp.aymandev.astolfo.rpg.pets.RPGPet;
import jp.aymandev.astolfo.rpg.player.talents.RPGTalents;
import jp.aymandev.astolfo.rpg.player.talents.TalentList;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class RPGPlayer {

    private Guild guild;
    private String name;
    private String userID;
    private int level;
    private int experience;
    private int money;
    private int upgradePoints = 12;
    private float strengthMod = 1;
    private float agilityMod = 1;
    private float magicMod = 1;
    private boolean hasToLearnTalents = true;
    //private RPGClass rpgClass;
    //private String classname;

    //Player is created?
    public boolean isCreated = false;

    //Current player characteristics
    private LinkedHashMap<String, Integer> characteristics;

    //Inventory data
    private RPGInventory inventory;

    //Talent data
    private RPGTalents talents;

    //Player data
    private JSONObject playerData = new JSONObject();

    //Quest data
    private RPGQuestData questData;

    //Pet
    private RPGPet petData;

    public RPGPlayer(String userID, Guild guild) {
        this.userID = userID;
        this.guild = guild;
        this.characteristics = new LinkedHashMap<>();
        setLevel(1);
        setMoney(0);
        setCharacteristic(EnumCharacteristic.STRENGTH, 0);
        setCharacteristic(EnumCharacteristic.AGILITY, 0);
        setCharacteristic(EnumCharacteristic.INTELLIGENCE, 0);
        setCharacteristic(EnumCharacteristic.ENDURANCE, 0);
        setCharacteristic(EnumCharacteristic.BEASTMASTER, 0);
        setCharacteristic(EnumCharacteristic.MELEE, 0);
        setCharacteristic(EnumCharacteristic.RANGE, 0);
        setCharacteristic(EnumCharacteristic.MAGIC, 0);
        inventory = new RPGInventory(this);
        talents = new RPGTalents(this);
        questData = new RPGQuestData(this);
        questData.generateQuestList();


        setPetData(new RPGPet(this, PetList.pets.get("призрачный волк")));
    }

    public void starterPack() {
        inventory.addItemToInventory(ItemList.getItemByName("Лук Авантюриста"));
        /*inventory.addItemToInventory(ItemList.getItemByName("Посох Авантюриста"));
        inventory.addItemToInventory(ItemList.getItemByName("Меч Авантюриста"));
        inventory.addItemToInventory(ItemList.getItemByName("Меч Авантюриста"));
        inventory.addItemToInventory(ItemList.getItemByName("Кинжал Авантюриста"));
        inventory.addItemToInventory(ItemList.getItemByName("Кинжал Авантюриста"));
        inventory.addItemToInventory(ItemList.getItemByName("Двуручный Меч Авантюриста"));
        inventory.addItemToInventory(ItemList.getItemByName("Перчатки Архимага"));*/
    }

    public RPGPlayer loadData(JSONObject loadingData) {
        playerData = loadingData;

        name = playerData.getString("name");
        userID = playerData.getString("userID");
        isCreated = playerData.getBoolean("created");
        level = playerData.getInt("level");
        experience = playerData.getInt("experience");
        money = playerData.getInt("money");
        upgradePoints = playerData.getInt("upgradePoints");
        agilityMod = playerData.getInt("agilityMod");
        strengthMod = playerData.getInt("strengthMod");
        magicMod = playerData.getInt("magicMod");
        hasToLearnTalents = playerData.getBoolean("hasToLearnTalents");
        loadCharacteristics(playerData);

        inventory.loadData(playerData);
        talents.loadData(playerData);
        questData.loadData(playerData);

        if (!petData.loadData(playerData)) {
            petData = null;
        }
        return this;
    }

    private void loadCharacteristics(JSONObject playerData) {
        JSONObject characteristicsData = playerData.getJSONObject("characteristics");
        for (String key : characteristicsData.keySet()) {
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(key);
            setCharacteristic(characteristic, characteristicsData.getInt(key));
        }
    }

    public JSONObject writeData() {
        playerData.put("name", name);
        playerData.put("userID", userID);
        playerData.put("created", isCreated);
        playerData.put("level", level);
        playerData.put("experience", experience);
        playerData.put("money", money);
        playerData.put("upgradePoints", upgradePoints);
        playerData.put("agilityMod", agilityMod);
        playerData.put("strengthMod", strengthMod);
        playerData.put("magicMod", magicMod);
        playerData.put("hasToLearnTalents", hasToLearnTalents);
        playerData.put("characteristics", characteristics);

        playerData = inventory.writeData(playerData);
        playerData = talents.writeData(playerData);
        playerData = questData.writeData(playerData);

        if (petData != null) {
            playerData = petData.writeData(playerData);
        } else {
            playerData.remove("petData");
        }
        return playerData;
    }

    public String getName() {
        return name;
    }

    public RPGPlayer setName(String name) {
        this.name = name;
        return this;
    }

    public String getUserID() {
        return userID;
    }

    public RPGPlayer setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public RPGPlayer setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getExperience() {
        return experience;
    }

    public RPGPlayer setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public void addExperience(int experience) {
        this.experience += experience;

        levelUp();
    }

    public void levelUp() {
        int neededExperience = level * 1000;

        if (!hasToLearnTalents && getExperience() >= neededExperience) {
            experience -= neededExperience;
            level++;
            upgradePoints++;

            if (TalentList.talents.containsKey(level)) {
                getTalents().setTalentPoints(getTalents().getTalentPoints() + 1);
                hasToLearnTalents = true;
            }

            if (experience > neededExperience) {
                levelUp();
            }
        }
    }

    public boolean isHasToLearnTalents() {
        return hasToLearnTalents;
    }

    public RPGPlayer setHasToLearnTalents(boolean hasToLearnTalents) {
        this.hasToLearnTalents = hasToLearnTalents;
        return this;
    }

    public int getMoney() {
        return money;
    }

    public RPGPlayer setMoney(int money) {
        this.money = money;
        return this;
    }

    public void addMoney(int money) {
        this.money += money;
    }


    public int getAllPowerPoints() {
        return getPowerPoints() + getTalentPowerPoints() + getPetPowerPoints();
    }

    public int getPowerPoints() {
        return RPGHelper.calculatePowerPoints(this);
    }

    public int getTalentPowerPoints() {
        return RPGHelper.calculateTalentPowerPoints(this);
    }

    public int getPetPowerPoints() {
        return RPGHelper.calculatePetPower(this);
    }

    public void addCharacteristic(EnumCharacteristic characteristic, int amount) {
        int currentCharacteristic = getCharacteristic(characteristic);
        currentCharacteristic += amount;
        setCharacteristic(characteristic, currentCharacteristic);
    }

    public void setCharacteristic(EnumCharacteristic characteristic, int amount) {
        characteristics.put(characteristic.name(), amount);
    }

    public int getCharacteristic(EnumCharacteristic characteristic) {
        return characteristics.get(characteristic.name());
    }

    public int getUpgradePoints() {
        return upgradePoints;
    }

    public RPGPlayer setUpgradePoints(int upgradePoints) {
        this.upgradePoints = upgradePoints;
        return this;
    }

    public EmbedBuilder getEmbedOfPlayer() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        Member member = AstolfoBot.currentGuild.getMemberById(userID);
        embedBuilder.setAuthor(getName(), "https://www.twitch.tv/aymandev", member.getUser().getAvatarUrl());
        embedBuilder.setThumbnail(member.getUser().getAvatarUrl());
        /*embedBuilder.addField("Класс:", rpgClass.getLocalizedName(), false);*/
        embedBuilder.addField("Уровень:", getLevel() + ".lvl", false);
        embedBuilder.addField("Опыт:", getExperience() + "/" + getLevel() * 1000, false);
        embedBuilder.addField("Очков прокачки:", getUpgradePoints() + ".p", false);
        embedBuilder.addField("Трапели:", getMoney() + ".trap", false);
        embedBuilder.addField("Cила героя:",
                getPowerPoints() + " +(" + getTalentPowerPoints() + ") " +
                        "+(" + getPetPowerPoints() + ") = " + getAllPowerPoints(), false);
        embedBuilder.addField("Таланты:", (talents.hasTalentPoints() ?
                talents.getTalentPoints() + " очков талантов! " : "") +
                "`a!rpg talents`", false);
        embedBuilder.addField("Питомец:", "`a!rpg pet`", false);
        embedBuilder.addBlankField(false);

        for (String key : characteristics.keySet()) {
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(key);
            if (!characteristic.isVisible) continue;

            int amount = characteristics.get(key);
            String upgradeStr = (characteristic.isUpgradable && getUpgradePoints() > 0) ? "(+)" : "";
            embedBuilder.addField(characteristic.localizedName +
                    ":", amount + ".p " + upgradeStr, true);
        }
        return embedBuilder;
    }

    public Guild getGuild() {
        return guild;
    }

    public RPGInventory getInventory() {
        return inventory;
    }

    public RPGQuestData getQuestData() {
        return questData;
    }

    public float getStrengthMod() {
        return strengthMod;
    }

    public RPGPlayer setStrengthMod(float strengthMod) {
        this.strengthMod = strengthMod;
        return this;
    }

    public void addStrengthMod(float amount) {
        setStrengthMod(getStrengthMod() + amount);
    }

    public void consumeStrengthMod(float amount) {
        setStrengthMod(getStrengthMod() - amount);
    }

    public float getAgilityMod() {
        return agilityMod;
    }

    public RPGPlayer setAgilityMod(float agilityMod) {
        this.agilityMod = agilityMod;
        return this;

    }

    public void addAgilityMod(float amount) {
        setAgilityMod(getAgilityMod() + amount);
    }

    public void consumeAgilityMod(float amount) {
        setAgilityMod(getAgilityMod() - amount);
    }

    public float getMagicMod() {
        return magicMod;
    }

    public RPGPlayer setMagicMod(float magicMod) {
        this.magicMod = magicMod;
        return this;
    }

    public void addMagicMod(float amount) {
        setMagicMod(getMagicMod() + amount);
    }

    public void consumeMagicMod(float amount) {
        setMagicMod(getMagicMod() - amount);
    }

    public RPGTalents getTalents() {
        return talents;
    }

    public RPGPet getPetData() {
        return petData;
    }

    public RPGPlayer setPetData(RPGPet petData) {
        this.petData = petData;
        return this;
    }

    public boolean hasPet() {
        return petData != null;
    }
}
