package jp.aymandev.astolfo.rpg.quests;

import jp.aymandev.astolfo.AstolfoBot;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.rpg.RPGCore;
import jp.aymandev.astolfo.rpg.items.ItemBase;
import jp.aymandev.astolfo.rpg.items.ItemList;
import jp.aymandev.astolfo.rpg.pets.RPGPet;
import jp.aymandev.astolfo.rpg.pets.talents.PetTalentList;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.json.JSONArray;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by AymanDev on 30.07.2018.
 */
public class QuestPlayerData {

    private Quest quest;
    private int experience;
    private int money;
    private int questPower;
    private boolean isCompleted = false;
    private float escapeChance;
    private float winChance;
    private int currentMinutes = 1;
    private String name;
    private String description;

    private RPGPlayer rpgPlayer;

    private static Random random = new Random();
    private Timer timer = new Timer();

    public QuestPlayerData(Quest quest, RPGPlayer rpgPlayer) {
        if (rpgPlayer != null) {
            this.rpgPlayer = rpgPlayer;

            if (quest != null) {
                this.quest = quest;
                questPower = (int) (calculateQuestPower(rpgPlayer) * quest.getQuestMod());
                experience = calculateExperience(rpgPlayer);
                money = calculateMoney();
                escapeChance = calculateEscapeChance(rpgPlayer);
                winChance = calculateWinChance(rpgPlayer);
                currentMinutes = quest.getTime() * 60;

                name = quest.getLocalizedName();
                description = quest.getDescription();
            }
        }
    }

    public void startQuestTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Guild guild = AstolfoBot.currentGuild;
                Member member = guild.getMemberById(rpgPlayer.getUserID());
                MessageChannel rpgQuestsChannel = guild.getTextChannelById("473815608140496896");
                String randomEventMessage = recalculateRandomEvent();
                int currentWinChance = random.nextInt(100);

                if (currentMinutes <= 0) {
                    if (randomEventMessage.isEmpty()) {
                        if (currentWinChance <= winChance) {
                            List<Integer> rewards = new ArrayList<>();
                            String winQuestMsg = ", `вы успешно выполнили задание: ";

                            if (QuestList.legendaryQuest != null) {
                                if (name.equalsIgnoreCase(QuestList.legendaryQuest
                                        .getName())) {
                                    winQuestMsg = ", `вы успешно выполнили ЛЕГЕНДАРНОЕ задание";

                                    rpgPlayer.addExperience(experience);
                                    rpgPlayer.addMoney(money);

                                    JSONArray itemRewardsJson = RPGCore.loadDataFromFile("legendary-quest.json")
                                            .getJSONObject("questData")
                                            .getJSONObject("currentQuest")
                                            .getJSONArray("rewards");

                                    for (int i = 0; i < itemRewardsJson.length(); i++) {
                                        String itemKey = itemRewardsJson.getString(i);
                                        ItemBase itemBase = ItemList.getItemByName(itemKey);

                                        rpgPlayer.getInventory().addItemToInventory(itemBase);
                                    }
                                    cancelTimer();
                                    rpgPlayer.getQuestData().setCurrentQuestData(null);
                                    QuestList.resetLegendaryQuest();
                                }
                            } else {
                                rewards = rpgPlayer.getQuestData().completeCurrentQuest(1.0f);
                            }

                            rpgQuestsChannel.sendMessage(member.getAsMention() +
                                    winQuestMsg + name + " :tada:").queue();
                        } else {
                            int currentEscapeChance = random.nextInt(100);

                            if (currentEscapeChance <= escapeChance) {
                                rpgPlayer.getQuestData().setCurrentQuestData(null);
                                if (rpgPlayer.hasPet()) {
                                    RPGPet rpgPet = rpgPlayer.getPetData();

                                    if (rpgPet.getLearnedTalents()
                                            .contains(PetTalentList.getTalent("Черепаха", "Крепкий панцирь"))) {
                                        rpgQuestsChannel.sendMessage(member.getAsMention() +
                                                ", `вы не выполнили задание, но успели сбежать и вместе с питомем!` " +
                                                ":warning:").queue();
                                    } else {
                                        rpgPlayer.setPetData(null);
                                        rpgQuestsChannel.sendMessage(member.getAsMention() +
                                                ", `вы не выполнили задание, но успели сбежать и потеряли питомца!` " +
                                                ":warning:").queue();
                                    }
                                } else {
                                    rpgQuestsChannel.sendMessage(member.getAsMention() +
                                            ", `вы не выполнили задание, но успели сбежать!` :warning:").queue();
                                }
                            } else {
                                RPGCore.deleteMemberPlayer(member);
                                rpgQuestsChannel.sendMessage(member.getAsMention() +
                                        ", `вы погибли во время задания!` :warning:").queue();
                            }
                        }
                    } else {
                        rpgQuestsChannel.sendMessage(member.getAsMention() + randomEventMessage).queue();
                    }
                    cancelTimer();
                }
                currentMinutes--;
            }
        }, 0, TimeUnit.MINUTES.toMillis(1));
    }

    public EmbedBuilder getQuestEmbed() {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setTitle(name);
        embedBuilder.setThumbnail("https://cdn.discordapp.com/attachments/472408819289554965/473560803187097640/1200x630bb.png");
        embedBuilder.addField("Мощь задания:", "~" + questPower + ".p (примерная)", false);
        embedBuilder.addField("Опыт:", "~" + experience + ".p (примерно)", false);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        embedBuilder.addField("Шанс победы:", "~" + df.format(winChance) + "% (примерно)", false);
        // embedBuilder.addField("Шанс на побег:", "~" + df.format(escapeChance) + "% (примерно) в случае поражения", false);
        embedBuilder.addField("Осталось минут:", currentMinutes + "m", false);

        if (!description.isEmpty()) {
            embedBuilder.addField("Описание:", description, false);
        }
        return embedBuilder;
    }

    private int calculateQuestPower(RPGPlayer rpgPlayer) {
        int randPowerIncrease = random.nextInt(30);
        int power = rpgPlayer.getAllPowerPoints() - 15 + randPowerIncrease;
        if (power <= 0) power = 1;

        return power;
    }

    private int calculateExperience(RPGPlayer rpgPlayer) {
        int neededExperience = rpgPlayer.getLevel() * 1000;
        float dividedPower = (float) questPower / rpgPlayer.getAllPowerPoints();
        return (int) (quest.getTime() * (neededExperience / 4.0f) * dividedPower);
    }

    private int calculateMoney() {
        return questPower / 5;
    }

    public float calculateEscapeChance(RPGPlayer rpgPlayer) {
        float escapeBuff = 0.0f;
        if (rpgPlayer.getTalents().isLearned("Мастер побега")) {
            escapeBuff = 5f;
        }

        if (rpgPlayer.hasPet()) {
            escapeBuff += rpgPlayer.getPetData().calculateEscapeChance();
        }

        float enduranceBuff = rpgPlayer.getCharacteristic(EnumCharacteristic.ENDURANCE) * 0.25f;
        return ((float) rpgPlayer.getAllPowerPoints() / questPower / 1.5f) * 100 + enduranceBuff + escapeBuff;
    }

    public float calculateWinChance(RPGPlayer rpgPlayer) {
        return ((float) rpgPlayer.getAllPowerPoints() / questPower) * 100f;
    }

    private String recalculateRandomEvent() {
        return "";
    }

    public Quest getQuest() {
        return quest;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


    public int getQuestPower() {
        return questPower;
    }

    public void setQuestPower(int questPower) {
        this.questPower = questPower;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public float getEscapeChance() {
        return escapeChance;
    }

    public void setEscapeChance(float escapeChance) {
        this.escapeChance = escapeChance;
    }

    public float getWinChance() {
        return winChance;
    }

    public void setWinChance(float winChance) {
        this.winChance = winChance;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCurrentMinutes() {
        return currentMinutes;
    }

    public void setCurrentMinutes(int currentMinutes) {
        this.currentMinutes = currentMinutes;
    }

    public void cancelTimer() {
        timer.cancel();
    }

    public String getName() {
        return name;
    }

    public QuestPlayerData setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public QuestPlayerData setDescription(String description) {
        this.description = description;
        return this;
    }

    public RPGPlayer getRpgPlayer() {
        return rpgPlayer;
    }

    public QuestPlayerData setRpgPlayer(RPGPlayer rpgPlayer) {
        this.rpgPlayer = rpgPlayer;
        return this;
    }
}
