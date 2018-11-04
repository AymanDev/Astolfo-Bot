package jp.aymandev.astolfo.rpg.player;

/**
 * Created by AymanDev on 29.07.2018.
 */
public enum EnumCharacteristic {

    NONE("Нет", false, false),
    STRENGTH("Сила", true, true),
    AGILITY("Ловкость", true, true),
    INTELLIGENCE("Интеллект", true, true),
    ENDURANCE("Выносливость", true, true),
    BEASTMASTER("Зверолюб", true, true),
    MELEE("Ближний", false, true),
    RANGE("Дальний", false, true),
    MAGIC("Магия", false, true),
;

    public String localizedName;
    public boolean isUpgradable;
    public boolean isVisible;

    EnumCharacteristic(String localizedName, boolean isUpgradable, boolean isVisible) {
        this.localizedName = localizedName;
        this.isUpgradable = isUpgradable;
        this.isVisible = isVisible;
    }

    public static EnumCharacteristic getCharacteristicByName(String localizedName) {
        localizedName = localizedName.toLowerCase();
        for (EnumCharacteristic characteristic : values()) {
            String localizedNameCharacteristic = characteristic.localizedName.toLowerCase();
            if (localizedNameCharacteristic.equalsIgnoreCase(localizedName)) {
                return characteristic;
            }
        }
        return null;
    }
}
