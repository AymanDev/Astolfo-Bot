package jp.aymandev.astolfo.rpg.player.talents;

import jp.aymandev.astolfo.rpg.items.EnumItemType;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;

import java.util.HashMap;

/**
 * Created by AymanDev on 29.07.2018.
 */
public class TalentList {

    private static HashMap<String, Talent> level1Talents = new HashMap<>();
    private static HashMap<String, Talent> level4Talents = new HashMap<>();
    public static HashMap<String, Talent> levelAllTalents = new HashMap<>();

    public static HashMap<Integer, HashMap<String, Talent>> talents = new HashMap<>();

    public TalentList() {
        registerTalent(new Talent("Огненный шар", EnumItemType.STAFF, EnumCharacteristic.INTELLIGENCE, 1, true)
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/473097966479998987/fireball-red-1.png"));
        registerTalent(new Talent("Отравленная стрела", EnumItemType.BOW, EnumCharacteristic.AGILITY, 1, true)
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/473138743092445194/Arrow_1_Original.png"));
        registerTalent(new Talent("Рванная рана", EnumItemType.DAGGER, EnumCharacteristic.AGILITY, 1, true)
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/473138870746349569/Dagger_1_Mod_1.png"));
        registerTalent(new Talent("Силовой удар", EnumItemType.TWOHAND, EnumCharacteristic.STRENGTH, 1, true)
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/473139000031576074/Sword_1_Mod_1.png"));
        registerTalent(new Talent("Кровавые клинки", EnumItemType.ONEHAND, EnumCharacteristic.AGILITY, 1, true)
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/473139291384578048/Sword_5_Mod_1.png"));
        talents.put(1, level1Talents);
        registerTalent(new Talent("Мастер побега", EnumItemType.DEFAULT, EnumCharacteristic.NONE, 4, false)
                .setDescription("Увеличивает шанс на побег(5%)")
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/474228870736773140/ability_rogue_trip.jpg"));
        registerTalent(new Talent("Время - деньги", EnumItemType.DEFAULT, EnumCharacteristic.NONE, 4, false)
                .setDescription("Более длительные задания дают больше трапелей")
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/474228884426981378/ability_racial_timeismoney.jpg"));
        registerTalent(new Talent("Прыткость", EnumItemType.DEFAULT, EnumCharacteristic.NONE, 4, false)
                .setDescription("Сокращает время выполнения квестов(10%)")
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/474229183141117952/ability_rogue_sprint.jpg"));
        registerTalent(new Talent("Близость с природой", EnumItemType.DEFAULT, EnumCharacteristic.INTELLIGENCE, 4, false)
                .setDescription("Повышает шанс приручить питомца на задании + Сокращает время выполнение заданий тренировки питомцов(+5% к шансу приручения и -30% от выполнения заданий на тренировку питомца)")
                .setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/474229794259730454/ability_druid_replenish.png"));
        registerTalent(new Talent("Чародейский интеллект", EnumItemType.DEFAULT, EnumCharacteristic.NONE, 4, false) {
                    @Override
                    public void onTalentLearn(RPGPlayer rpgPlayer) {
                        rpgPlayer.addCharacteristic(EnumCharacteristic.INTELLIGENCE, 13);
                    }
                }.setDescription("+13 к интеллекту").setIconUrl("https://cdn.discordapp.com/attachments/321745119952764939/474228851384385537/spell_holy_magicalsentry.jpg")
        );
        talents.put(4, level4Talents);
    }

    private void registerTalent(Talent talent) {
        switch (talent.getLevel()) {
            case 1:
                level1Talents.put(talent.getTalentName().toLowerCase(), talent);
                break;
            case 4:
                level4Talents.put(talent.getTalentName().toLowerCase(), talent);
                break;
        }
        levelAllTalents.put(talent.getTalentName().toLowerCase(), talent);
    }
}
