package jp.aymandev.astolfo.rpg.player;

import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.rpg.quests.Quest;
import jp.aymandev.astolfo.rpg.quests.QuestList;
import jp.aymandev.astolfo.rpg.quests.QuestPlayerData;
import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by AymanDev on 30.07.2018.
 */
public class RPGQuestData {

    public HashMap<String, Quest> quests = new HashMap<>();
    private QuestPlayerData currentQuestData;
    private RPGPlayer rpgPlayer;
    private JSONObject questData = new JSONObject();
    private static Random random = new Random();

    public RPGQuestData(RPGPlayer rpgPlayer) {
        this.rpgPlayer = rpgPlayer;
    }

    public List<Integer> completeCurrentQuest(float mod) {
        int experience = (int) (currentQuestData.getExperience() * mod);
        int money = (int) (currentQuestData.getMoney() * mod);
        rpgPlayer.addExperience(experience);
        rpgPlayer.addMoney(money);

        if (rpgPlayer.hasPet()) {
            rpgPlayer.getPetData().addExperience(experience / 10);
        }

        currentQuestData = null;
        return Arrays.asList(experience, money);
    }

    public Quest getQuest(String questKey) {
        return quests.get(questKey);
    }

    public boolean hasCurrentQuest() {
        return currentQuestData != null;
    }

    public QuestPlayerData getQuestPlayerData() {
        return currentQuestData;
    }

    public RPGQuestData setCurrentQuestData(QuestPlayerData currentQuestData) {
        this.currentQuestData = currentQuestData;
        return this;
    }

    public EmbedBuilder getCompletingQuestDataEmbed() {
        return currentQuestData.getQuestEmbed();
    }

    public EmbedBuilder getAvailableQuests() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setTitle("Список доступных заданий:");

        for (Quest quest : quests.values()) {
            String questMod = quest.getQuestMod() > 1.0f ? "(*)" : "";
            embedBuilder.addField(quest.getLocalizedName() + questMod,
                    quest.getDescription(), false);
        }
        return embedBuilder;
    }

    public void generateQuestList() {
        quests.clear();

        for (int i = 0; i < 4; ) {
            Set<String> questKeySet = QuestList.quests.keySet();
            int randQuest = random.nextInt(questKeySet.size() - 4);
            String questKey = questKeySet.toArray(new String[0])[randQuest + i].toLowerCase();

            if (currentQuestData == null) {
                Quest quest = QuestList.quests.get(questKey);
                quests.put(questKey, quest);
                i++;
                continue;
            }

            if (!currentQuestData.getQuest().getLocalizedName().equalsIgnoreCase(questKey)) {
                Quest quest = QuestList.quests.get(questKey);
                quests.put(questKey, quest);
                i++;
            }
        }
    }

    public void takeQuest(String questKey) {
        Quest quest = quests.get(questKey);
        takeQuest(quest);
    }

    public void takeQuest(Quest quest) {
        currentQuestData = new QuestPlayerData(quest, rpgPlayer);
        currentQuestData.startQuestTimer();
        quests.remove(quest.getLocalizedName().toLowerCase());
    }

    public void loadData(JSONObject playerData) {
        if (playerData.has("questData")) {
            questData = playerData.getJSONObject("questData");

            if (questData.has("currentQuest")) {
                QuestPlayerData questPlayerData = loadQuestData(playerData, rpgPlayer);

                questPlayerData.startQuestTimer();
                currentQuestData = questPlayerData;
            }
        }
    }

    public static QuestPlayerData loadQuestData(JSONObject jsonObject, RPGPlayer rpgPlayer) {
        JSONObject questData = jsonObject.getJSONObject("questData");
        JSONObject currentQuestJsonData = questData.getJSONObject("currentQuest");
        String questName = currentQuestJsonData.getString("name");
        String description = currentQuestJsonData.getString("description");
        boolean isCompleted = currentQuestJsonData.getBoolean("completed");
        int experience = currentQuestJsonData.getInt("experience");
        int money = currentQuestJsonData.getInt("money");
        int power = currentQuestJsonData.getInt("power");
        float escapeChance = (float) currentQuestJsonData.getDouble("escape");
        float winChance = (float) currentQuestJsonData.getDouble("win");
        int minutes = currentQuestJsonData.getInt("minutes");

        Quest quest = QuestList.quests.get(questName);
        QuestPlayerData questPlayerData = new QuestPlayerData(quest, rpgPlayer);
        questPlayerData.setName(questName);
        questPlayerData.setDescription(description);
        questPlayerData.setCompleted(isCompleted);
        questPlayerData.setExperience(experience);
        questPlayerData.setMoney(money);
        questPlayerData.setQuestPower(power);
        questPlayerData.setEscapeChance(escapeChance);
        questPlayerData.setWinChance(winChance);
        questPlayerData.setCurrentMinutes(minutes);

        return questPlayerData;
    }

    public JSONObject writeData(JSONObject playerData) {
        questData.remove("currentQuest");
        if (currentQuestData != null) {
            JSONObject currentQuestJsonData = saveQuestData(currentQuestData);
            questData.put("currentQuest", currentQuestJsonData);
        }
        playerData.put("questData", questData);
        return playerData;
    }

    public static JSONObject saveQuestData(QuestPlayerData questData) {
        JSONObject questJsonData = new JSONObject();
        questJsonData.put("name", questData.getName());
        questJsonData.put("description", questData.getDescription());
        questJsonData.put("completed", questData.isCompleted());
        questJsonData.put("experience", questData.getExperience());
        questJsonData.put("money", questData.getMoney());
        questJsonData.put("power", questData.getQuestPower());
        questJsonData.put("escape", questData.getEscapeChance());
        questJsonData.put("win", questData.getWinChance());
        questJsonData.put("minutes", questData.getCurrentMinutes());
        return questJsonData;
    }

}
