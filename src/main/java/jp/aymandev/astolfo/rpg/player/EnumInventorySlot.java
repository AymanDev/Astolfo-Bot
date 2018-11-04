package jp.aymandev.astolfo.rpg.player;

/**
 * Created by AymanDev on 29.07.2018.
 */
public enum EnumInventorySlot {

    DEFAULT("Обычный"),
    HEAD("Голова"),
    ARTIFACT("Артефакт"),
    LEFTHAND("Л.Рука"),
    RIGHTHAND("П.Рука"),
    BOTHHAND("ЛПРука"),;
    public String slotName;

    EnumInventorySlot(String slotName) {
        this.slotName = slotName;
    }

    public static EnumInventorySlot getSlotByName(String localizedName) {
        localizedName = localizedName.toLowerCase();
        for (EnumInventorySlot slot : values()) {
            String localizedNameSlot = slot.slotName.toLowerCase();
            if (localizedNameSlot.equalsIgnoreCase(localizedName)) {
                return slot;
            }
        }
        return null;
    }
}
