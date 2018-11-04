package jp.aymandev.astolfo.rpg.items;

import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.EnumInventorySlot;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.LinkedHashMap;

/**
 * Created by AymanDev on 29.07.2018.
 */
public class ItemBase {

    private String itemName;
    private String itemIcon = "";
    private String itemShortDescription = "";
    private EnumCharacteristic primaryCharacteristic;
    private EnumInventorySlot itemSlot = EnumInventorySlot.DEFAULT;
    private EnumItemType itemType = EnumItemType.DEFAULT;
    private LinkedHashMap<String, Integer> itemCharacteristics;
    private float agilityMod = 0f;
    private float strengthMod = 0f;
    private float magicMod = 0f;

    public ItemBase(String itemName, EnumCharacteristic primaryCharacteristic) {
        this.itemName = itemName;
        this.primaryCharacteristic = primaryCharacteristic;
        itemCharacteristics = new LinkedHashMap<>();
    }

    public EmbedBuilder getItemDescription() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        if (!itemIcon.isEmpty()) {
            embedBuilder.setThumbnail(itemIcon);
        }
        embedBuilder.setTitle(itemName, "");
        if (!getItemType().name().equalsIgnoreCase(EnumItemType.DEFAULT.name())) {
            embedBuilder.addField("Тип оружия:", getItemType().localizedName, true);
        }
        embedBuilder.addField("Слот:", getItemSlot().slotName, true);

        embedBuilder.addField("Характеристики:", "", false);
        for (String characteristicKey : itemCharacteristics.keySet()) {
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(characteristicKey);
            int amount = itemCharacteristics.get(characteristicKey);

            embedBuilder.addField(characteristic.localizedName + ":", "+" + amount, false);
        }
        if (agilityMod >= 1f) {
            embedBuilder.addField("Усилитель Ловкости:", getAgilityMod() + ".a", false);
        }
        if (strengthMod >= 1f) {
            embedBuilder.addField("Усилитель Силы:", getStrengthMod() + ".s", false);
        }
        if (magicMod >= 1f) {
            embedBuilder.addField("Усилитель Магии:", getMagicMod() + ".m", false);
        }

        embedBuilder.addBlankField(false);
        embedBuilder.addField("Описание:", getItemShortDescription(), false);
        return embedBuilder;
    }

    public ItemBase addCharacteristic(EnumCharacteristic characteristic, int amount) {
        itemCharacteristics.put(characteristic.name(), amount);
        return this;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public ItemBase setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
        return this;
    }

    public String getItemShortDescription() {
        return itemShortDescription;
    }

    public ItemBase setItemShortDescription(String itemShortDescription) {
        this.itemShortDescription = itemShortDescription;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public EnumInventorySlot getItemSlot() {
        return itemSlot;
    }

    public ItemBase setItemSlot(EnumInventorySlot itemSlot) {
        this.itemSlot = itemSlot;
        return this;
    }

    public EnumCharacteristic getPrimaryCharacteristic() {
        return primaryCharacteristic;
    }

    public EnumItemType getItemType() {
        return itemType;
    }

    public ItemBase setItemType(EnumItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public float getAgilityMod() {
        return agilityMod;
    }

    public ItemBase setAgilityMod(float agilityMod) {
        this.agilityMod = agilityMod;
        return this;
    }

    public float getStrengthMod() {
        return strengthMod;
    }

    public ItemBase setStrengthMod(int strengthMod) {
        this.strengthMod = strengthMod;
        return this;
    }

    public float getMagicMod() {
        return magicMod;
    }

    public ItemBase setMagicMod(int magicMod) {
        this.magicMod = magicMod;
        return this;
    }

    public void addCharacteristicsToPlayer(RPGPlayer rpgPlayer) {
        for (String characteristicKey : itemCharacteristics.keySet()) {
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(characteristicKey);
            int amount = itemCharacteristics.get(characteristicKey);

            rpgPlayer.addCharacteristic(characteristic, amount);
            rpgPlayer.addAgilityMod(getAgilityMod());
            rpgPlayer.addStrengthMod(getStrengthMod());
            rpgPlayer.addMagicMod(getMagicMod());
        }
    }

    public void removeCharacteristicsFromPlayer(RPGPlayer rpgPlayer) {
        for (String characteristicKey : itemCharacteristics.keySet()) {
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(characteristicKey);
            int amount = itemCharacteristics.get(characteristicKey);

            rpgPlayer.addCharacteristic(characteristic, -amount);
            rpgPlayer.consumeAgilityMod(getAgilityMod());
            rpgPlayer.consumeStrengthMod(getStrengthMod());
            rpgPlayer.consumeMagicMod(getMagicMod());
        }
    }
}
