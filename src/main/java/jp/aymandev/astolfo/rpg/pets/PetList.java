package jp.aymandev.astolfo.rpg.pets;

import jp.aymandev.astolfo.rpg.pets.talents.PetTalentList;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;

import java.util.HashMap;

/**
 * Created by AymanDev on 01.08.2018.
 */
public class PetList {

    public static final HashMap<String, Pet> pets = new HashMap<>();

    private PetTalentList petTalentList;

    public PetList() {
        petTalentList = new PetTalentList();

        registerPet(new Pet("Пёсик")
                .setDescription("Обычный няшный тупой пёсик")
                .setCharacteristic(EnumCharacteristic.AGILITY, 1)
                .setCharacteristic(EnumCharacteristic.STRENGTH, 1)
                .addTalent(PetTalentList.pesikTalents.get("милота"), 3)
                .addTalent(PetTalentList.pesikTalents.get("укус"), 6)
                .addTalent(PetTalentList.pesikTalents.get("команда взять"), 9)
                .addTalent(PetTalentList.pesikTalents.get("свирепый(очень) рёв"), 12)
                .addTalent(PetTalentList.pesikTalents.get("бег за хвостом"), 15)
                .addTalent(PetTalentList.pesikTalents.get("острые когти"), 18)
                .addTalent(PetTalentList.pesikTalents.get("разрывание цели"), 21)
                .addTalent(PetTalentList.pesikTalents.get("удар хвостом"), 24)
                .addTalent(PetTalentList.pesikTalents.get("острые зубы"), 27)
                .addTalent(PetTalentList.pesikTalents.get("преследованние цели"), 30)
                .addTalent(PetTalentList.pesikTalents.get("верный друг"), 35)
                .addTalent(PetTalentList.pesikTalents.get("увеличение в размерах"), 40)
                .addTalent(PetTalentList.pesikTalents.get("взросление"), 50)
        );
        registerPet(new Pet("Призрачный волк")
                .setDescription("Призванный с того мира, подчинился живому")
                .setCharacteristic(EnumCharacteristic.AGILITY, 20)
                .setCharacteristic(EnumCharacteristic.ENDURANCE, 20)
                .setCharacteristic(EnumCharacteristic.STRENGTH, 30)
                .setCharacteristic(EnumCharacteristic.INTELLIGENCE, 30)
                .addTalent(PetTalentList.ghostWolfTalents.get("свирепый укус"), 3)
                .addTalent(PetTalentList.ghostWolfTalents.get("астральный скачок"), 6)
                .addTalent(PetTalentList.ghostWolfTalents.get("команда взять"), 9)
                .addTalent(PetTalentList.ghostWolfTalents.get("исчезновение"), 12)
                .addTalent(PetTalentList.ghostWolfTalents.get("скачок за спину"), 15)
                .addTalent(PetTalentList.ghostWolfTalents.get("улучшенная духовная связь"), 18)
                .addTalent(PetTalentList.ghostWolfTalents.get("свирепый рёв"), 21)
                .addTalent(PetTalentList.ghostWolfTalents.get("удар когтями"), 24)
                .addTalent(PetTalentList.ghostWolfTalents.get("улучшенная духовная связь lvl.2"), 27)
                .addTalent(PetTalentList.ghostWolfTalents.get("волчий вой"), 30)
                .addTalent(PetTalentList.ghostWolfTalents.get("преследование цели"), 35)
                .addTalent(PetTalentList.ghostWolfTalents.get("увеличение в размерах"), 40)
                .addTalent(PetTalentList.ghostWolfTalents.get("идеальная духовная связь lvl.3"), 50)
        );
        registerPet(new Pet("Медведь")
                .setDescription("Король леса")
                .setCharacteristic(EnumCharacteristic.AGILITY, 10)
                .setCharacteristic(EnumCharacteristic.ENDURANCE, 20)
                .setCharacteristic(EnumCharacteristic.STRENGTH, 40)
                .setCharacteristic(EnumCharacteristic.INTELLIGENCE, 0)
                .addTalent(PetTalentList.bearTalents.get("свирепый укус"), 3)
                .addTalent(PetTalentList.bearTalents.get("таран"), 6)
                .addTalent(PetTalentList.bearTalents.get("команда взять"), 9)
                .addTalent(PetTalentList.bearTalents.get("стремительный рывок"), 12)
                .addTalent(PetTalentList.bearTalents.get("оглушение"), 18)
                .addTalent(PetTalentList.bearTalents.get("свирепый рёв"), 21)
                .addTalent(PetTalentList.bearTalents.get("удар когтями"), 24)
                .addTalent(PetTalentList.bearTalents.get("отвлечение"), 27)
                .addTalent(PetTalentList.bearTalents.get("заострённые клыки"), 30)
                .addTalent(PetTalentList.bearTalents.get("волчий вой"), 35)
                .addTalent(PetTalentList.bearTalents.get("увеличение в размерах"), 35)
                .addTalent(PetTalentList.bearTalents.get("толстая шкура"), 40)
        );
        registerPet(new Pet("Черепаха")
                .setDescription("Король леса")
                .setCharacteristic(EnumCharacteristic.AGILITY, 10)
                .setCharacteristic(EnumCharacteristic.ENDURANCE, 20)
                .setCharacteristic(EnumCharacteristic.STRENGTH, 40)
                .setCharacteristic(EnumCharacteristic.INTELLIGENCE, 0)
                .addTalent(PetTalentList.bearTalents.get("свирепый укус"), 3)
                .addTalent(PetTalentList.bearTalents.get("таран"), 6)
                .addTalent(PetTalentList.bearTalents.get("команда взять"), 9)
                .addTalent(PetTalentList.bearTalents.get("стремительный рывок"), 12)
                .addTalent(PetTalentList.bearTalents.get("оглушение"), 18)
                .addTalent(PetTalentList.bearTalents.get("свирепый рёв"), 21)
                .addTalent(PetTalentList.bearTalents.get("удар когтями"), 24)
                .addTalent(PetTalentList.bearTalents.get("отвлечение"), 27)
                .addTalent(PetTalentList.bearTalents.get("заострённые клыки"), 30)
                .addTalent(PetTalentList.bearTalents.get("волчий вой"), 35)
                .addTalent(PetTalentList.bearTalents.get("увеличение в размерах"), 35)
                .addTalent(PetTalentList.bearTalents.get("толстая шкура"), 40)
        );
    }

    private void registerPet(Pet pet) {
        pets.put(pet.getLocalizedName().toLowerCase(), pet);
    }
}
