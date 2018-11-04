package jp.aymandev.astolfo.rpg.quests;

import jp.aymandev.astolfo.rpg.RPGCore;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import jp.aymandev.astolfo.rpg.player.RPGQuestData;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by AymanDev on 30.07.2018.
 */
public class QuestList {

    public static final HashMap<String, Quest> quests = new HashMap<>();

    public static QuestPlayerData legendaryQuest;

    public QuestList() {
        loadLegendaryQuest();

        registerQuest(new Quest("Призрачная деревня", 2)
                .setDescription("Это деревня призраков, говорят даже видели как деревня пропадает, а " +
                        "иногда вообще менятся на другую!")
                .setQuestMod(1.1f)
        );
        registerQuest(new Quest("Проверка информации", 1)
                .setDescription("К северу от города есть пещера, говорят там прячутся разбойники, нужно проверить " +
                        "эту информацию! `*ПЕЧАТЬ ГОРОДСКОЙ СТРАЖИ*`"));
        registerQuest(new Quest("Исследование нового подземелья", 4)
                .setDescription("Поступили сведения о новом подземелье, нужно его исследовать! " +
                        "`*ПЕЧАТЬ ГИЛЬДИИ АВАНТЮРИСТОВ*`")
                .setQuestMod(2.0f)
        );
        registerQuest(new Quest("Тюрьма", 1)
                .setDescription("В тюрьме города началось восстание, требуется помощь авантюристов! " +
                        "`*ПЕЧАТЬ ГОРОДСКОЙ СТРАЖИ*`")
                .setQuestMod(1.25f)
        );
        registerQuest(new Quest("Сбор материалов", 2)
                .setDescription("Для научных исследований, требуются некоторые травы и руды, а " +
                        "также части существ. `*ПЕЧАТЬ ГИЛЬДИИ МАГОВ*`")
        );
        registerQuest(new Quest("Атака черепах", 3)
                .setDescription("Стены города осаждают огромные черепахи. Герои, нам нужна ваша помощь.")
                .setQuestMod(1.25f)
        );
        registerQuest(new Quest("Разбойники в лесу", 2)
                .setDescription("В лесу завелась шайка разбойников, надо их как можно скорее сдать в местные власти.")
                .setQuestMod(1.1f)
        );
        registerQuest(new Quest("Повышенная преступность", 8)
                .setDescription("В последнее время в городе повысилась преступность. " +
                        "Просим отважных приключенцев патрулировать город.")
                .setQuestMod(1.3f)
        );
        registerQuest(new Quest("Ограбленик караванов", 4)
                .setDescription("Недавно группа опасных бандитов украла много драгоценностей из города,к сожалению, " +
                        "они уже далеко уехали. Просим самых отважных приключенцев вернуть эти драгоценности.")
                .setQuestMod(1.4f)
        );
        registerQuest(new Quest("Сопровождение караванов", 4)
                .setDescription("Смелые приключенцы,мы группа путешествующих торговцев просим от вас сопровождения " +
                        "нас с нашими караванами.")
                .setQuestMod(1.3f)
        );
        registerQuest(new Quest("Огромный тролль", 2)
                .setDescription("В окрестностях города появился большой троль, просим смелых приключенцев его убить.")
                .setQuestMod(1.4f)
        );
        registerQuest(new Quest("Дикие волки", 2)
                .setDescription("В последние время охотники сообщают о обилии диких волков, их надо истребить " +
                        "как можно скорее.")
                .setQuestMod(1.2f)
        );
        registerQuest(new Quest("Серийный убийца", 1)
                .setDescription("В городе в последние время произошло много похожих убийств, просим отважных " +
                        "приключенцев поймать этого преступника.")
                .setQuestMod(1.6f)
        );
        registerQuest(new Quest("Безумные животные", 2)
                .setDescription("Охотники сообщают что в последнее время животные как будто обезумели. Надо бы " +
                        "разобратся в этой проблеме и сократить численность безумных животных.")
                .setQuestMod(1.1f)
        );
        registerQuest(new Quest("Лесное чудовище", 5)
                .setDescription("Жители соседней деревне страдают от страшного лесного чудовище. " +
                        "Приключенцы должны разобратся с этой проблемой.")
                .setQuestMod(1.6f)
        );
        registerQuest(new Quest("Безумец в лесу", 2)
                .setDescription("Недавно в лесу нашли пару трупов и одного ранненого человека.Раненный поведал что они " +
                        "наткнулись на обезумевшего человека.Он кричал что-то вроде " +
                        "\"Ужас...По всюду анимешники....Надо их истребить\".Отважные приключенцы, помогите жителям " +
                        "соседней деревни с безумцем.")
                .setQuestMod(1.5f)
        );
        registerQuest(new Quest("Защита корабля", 6)
                .setDescription("В последнее время ходят слухи о увеличение количества пиратов. " +
                        "Защитите корабль заказчика от пиратов.")
                .setQuestMod(1.3f)
        );
    }

    public static void resetLegendaryQuest() {
        for (RPGPlayer rpgPlayer : RPGCore.players.values()) {
            if (QuestList.legendaryQuest != null) {
                if (rpgPlayer.getQuestData().hasCurrentQuest()) {
                    rpgPlayer.getQuestData().getQuestPlayerData().cancelTimer();
                    rpgPlayer.getQuestData().setCurrentQuestData(null);
                }
            }
        }
        legendaryQuest = null;

        File file = new File("data/", "legendary-quest.json");
        file.delete();
    }

    public static void loadLegendaryQuest() {
        JSONObject questData = RPGCore.loadDataFromFile("legendary-quest.json");
        if (questData != null)
            QuestList.legendaryQuest = RPGQuestData.loadQuestData(questData, null);
    }

    private void registerQuest(Quest quest) {
        quests.put(quest.getLocalizedName().toLowerCase(), quest);
    }
}
