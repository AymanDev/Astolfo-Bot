package jp.aymandev.astolfo.rpg.items;

/**
 * Created by AymanDev on 29.07.2018.
 */
public enum  EnumItemType {

    DEFAULT("Любой"),
    ONEHAND("Одноручный"),
    DAGGER("Кинжал"),
    TWOHAND("Двуручный"),
    BOW("Лук"),
    STAFF("Посох"),
    ;
    public String localizedName;

    EnumItemType(String localizedName) {
        this.localizedName = localizedName;
    }
}
