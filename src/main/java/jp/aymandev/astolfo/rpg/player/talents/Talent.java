package jp.aymandev.astolfo.rpg.player.talents;

import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.rpg.items.EnumItemType;
import jp.aymandev.astolfo.rpg.items.ItemBase;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.EnumInventorySlot;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import net.dv8tion.jda.core.EmbedBuilder;

/**
 * Created by AymanDev on 29.07.2018.
 */
public class Talent {

    private String talentName;
    private EnumItemType primaryItemType;
    private EnumCharacteristic primaryCharacteristic;
    private String iconUrl;
    private int level;
    private boolean hasPower;
    private String description = "";

    public Talent(String talentName, EnumItemType primaryItemType, EnumCharacteristic primaryCharacteristic, int level, boolean hasPower) {
        this.talentName = talentName;
        this.primaryItemType = primaryItemType;
        this.primaryCharacteristic = primaryCharacteristic;
        this.level = level;
        this.hasPower = hasPower;
    }

    public EmbedBuilder getTalentEmbed() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setTitle(talentName);
        embedBuilder.setThumbnail(iconUrl);
        embedBuilder.addField("Требуемый тип оружия:", primaryItemType.localizedName, false);
        embedBuilder.addField("Требуемый уровень:", level + ".lvl", false);
        embedBuilder.addField("Основная характеристика:", primaryCharacteristic.localizedName, false);

        if (!description.isEmpty()) {
            embedBuilder.addField("Описание:", description, false);
        }
        return embedBuilder;
    }

    public void onTalentLearn(RPGPlayer rpgPlayer) {

    }

    public int calculateTalentPower(RPGPlayer rpgPlayer) {
        if (!hasPower) return 0;

        ItemBase rightHand = rpgPlayer.getInventory().getEquippedItem(EnumInventorySlot.RIGHTHAND);
        ItemBase leftHand = rpgPlayer.getInventory().getEquippedItem(EnumInventorySlot.LEFTHAND);

        float mod = 1.0f;
        switch (primaryCharacteristic) {
            case AGILITY:
                mod = rpgPlayer.getAgilityMod();
                break;
            case STRENGTH:
                mod = rpgPlayer.getStrengthMod();
                break;
            case INTELLIGENCE:
                mod = rpgPlayer.getMagicMod();
                break;
        }

        if (leftHand != null) {
            if (!leftHand.getItemType().name().equals(primaryItemType.name())) {
                mod = 0.0f;
            }
        } else if (rightHand == null) {
            mod = 0.0f;
        }

        if (rightHand != null) {
            if (!rightHand.getItemType().name().equals(primaryItemType.name())) {
                mod = 0.0f;
            }
        } else {
            mod = 0.0f;
        }

       /* System.out.println(rpgPlayer.getCharacteristic(primaryCharacteristic) + " " + mod + " " +
                rpgPlayer.getStrengthMod() + " " + rpgPlayer.getAgilityMod() + " " + rpgPlayer.getMagicMod());*/
        return (int) (rpgPlayer.getCharacteristic(primaryCharacteristic) * mod);
    }

    public Talent setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTalentName() {
        return talentName;
    }

    public EnumItemType getPrimaryItemType() {
        return primaryItemType;
    }

    public EnumCharacteristic getPrimaryCharacteristic() {
        return primaryCharacteristic;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Talent setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public int getLevel() {
        return level;
    }
}
