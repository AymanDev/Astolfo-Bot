package jp.aymandev.astolfo.commands.rpg;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.helpers.RoleHelper;
import jp.aymandev.astolfo.rpg.RPGCore;
import jp.aymandev.astolfo.rpg.items.ItemBase;
import jp.aymandev.astolfo.rpg.items.ItemStack;
import jp.aymandev.astolfo.rpg.pets.RPGPet;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.EnumInventorySlot;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import jp.aymandev.astolfo.rpg.player.RPGQuestData;
import jp.aymandev.astolfo.rpg.player.talents.Talent;
import jp.aymandev.astolfo.rpg.player.talents.TalentList;
import jp.aymandev.astolfo.rpg.quests.Quest;
import jp.aymandev.astolfo.rpg.quests.QuestList;
import jp.aymandev.astolfo.rpg.quests.QuestPlayerData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandRPG extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        if (!sender.getRoles().contains(RoleHelper.rpgRole)) {
            messageChannel.sendMessage(sender.getAsMention() + ", `у вас нет доступа к РПГ-модулю!` :warning:").queue();
            return;
        }

        String content = sendedMessage.getContentRaw().toLowerCase();
        String notLoweredContent = sendedMessage.getContentRaw();
        String[] subContent = content.split(" ");
        RPGPlayer rpgPlayer = RPGCore.getPlayer(sender);

        if (subContent.length > 1) {
            if (subContent[1].equalsIgnoreCase("create")) {
                if (rpgPlayer != null) {
                    messageChannel.sendMessage(sender.getAsMention() + ", `у вас уже есть персонаж!` :warning:")
                            .queue();
                    return;
                }

                if (subContent.length < 3) {
                    messageChannel.sendMessage(sender.getAsMention() + ", `неверно использована команда!` :warning:")
                            .queue();
                    return;
                }

                String playerName = notLoweredContent.split(" ")[2];
                rpgPlayer = new RPGPlayer(sender.getUser().getId(), guild);
                rpgPlayer.setName(playerName);
                rpgPlayer.starterPack();
                RPGCore.loadPlayerInDatabase(rpgPlayer);
                RPGCore.savePlayer(rpgPlayer);
                messageChannel.sendMessage(sender.getAsMention() + ", добро пожаловать в мой мир, кексик, в этом мире " +
                        "ты будешь авантюристом, удачи тебе, чтобы узнать, что делать дальше " +
                        "используй `a!rpg guide`").queue();
                return;
            }

            if (rpgPlayer == null) {
                messageChannel.sendMessage(sender.getAsMention() + ", `сначала создайте персонажа!` :warning:")
                        .queue();
                return;
            }
            if (subContent[1].equalsIgnoreCase("suicide")) {
                boolean isDeleted = RPGCore.deleteMemberPlayer(sender);
                if (isDeleted) {
                    messageChannel.sendMessage(sender.getAsMention() + ", `вы сбросились со скалы!` :tada:").queue();
                } else {
                    messageChannel.sendMessage(sender.getAsMention() + ", `такого персонажа не существует!` :warning:").queue();
                }
                return;
            }

            if (subContent[1].equalsIgnoreCase("guide")) {
                int step = 1;
                EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
                embedBuilder.addField(":warning: ПРЕДУПРЕЖДЕНИЕ :warning:",
                        "На данный момент все команды ниже в процессе разработки, либо работают некорректно. " +
                                "Советы ниже только для визаулизации временно!", false);
                embedBuilder.addBlankField(false);
                embedBuilder.addField(step + "-й шаг",
                        "Закончите распределение характеристик `a!rpg setup <characteristic> <amount>`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Проверьте список доступных заданий в Гильдии Авантюристов `a!rpg quests`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Примите одно из заданий в списке `a!rpg quests`\n" +
                                "Обращайте на показатель возможного выполнения задания, если он низкий, то не стоит " +
                                "пробовать пройти сейчас это задание! Задания со `*` сложнее обычных!", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Узнайте ваши примерные шансы `a!rpg quests info <quest-name>`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Попробуйте выполнить задание `a!rpg quests take <quest-name>`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Если задание было успешно выполнено, попробуйте проверить местные рынки `a!rpg shop`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Попробуйте проверить возможность пройти подземелья `a!rpg dungeon list`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Попробуйте проверить возможность пройти подземелья `a!rpg dungeon start <dungeon-number>`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Проверьте количество доступных очков для прокачки `a!rpg`", false);
                step++;
                embedBuilder.addField(step + "-й шаг",
                        "Попробуйте прокачать одну из характеристик персонажа `a!rpg setup <characteristic> <amount>`", false);
                messageChannel.sendMessage(embedBuilder.build()).queue();
                return;
            }

            RPGQuestData playerQuestData = rpgPlayer.getQuestData();

            if (subContent[1].equalsIgnoreCase("setup")) {
                if (playerQuestData.hasCurrentQuest()) {
                    messageChannel.sendMessage(sender.getAsMention() +
                            ", `нельзя распределять характеритсики во время задания` :warning:").queue();
                    return;
                }
                if (subContent.length == 4) {
                    try {
                        String characteristicStr = subContent[2];
                        String amountStr = subContent[3];
                        EnumCharacteristic characteristic = EnumCharacteristic.getCharacteristicByName(characteristicStr);
                        int amount = Integer.parseInt(amountStr);

                        if (characteristic == null) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `такой характеристики не существует!` :warning:").queue();
                            return;
                        }

                        if (!characteristic.isUpgradable) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `данную характеристику нельзя прокачивать` :warning:").queue();
                            return;
                        }
                        if (amount <= rpgPlayer.getUpgradePoints()) {
                            rpgPlayer.addCharacteristic(characteristic, amount);
                            rpgPlayer.setUpgradePoints(rpgPlayer.getUpgradePoints() - amount);
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `характеристика " +
                                    characteristic.localizedName + " была прокачена на " + amount +
                                    " очков, осталось: " + rpgPlayer.getUpgradePoints() + " очков` :tada:").queue();
                            if (!rpgPlayer.isCreated) {
                                rpgPlayer.isCreated = true;
                            }
                        } else {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", ` недостаточно очков прокачки!` :warning:")
                                    .queue();
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                        messageChannel.sendMessage(sender.getAsMention() +
                                ", `ошибка при использовании команды!` :warning:").queue();
                    }
                    return;
                }
                messageChannel.sendMessage(sender.getAsMention() + ", `у вас доступно " +
                        rpgPlayer.getUpgradePoints() + " очков прокачки!`").queue();
                return;
            }

            if (!rpgPlayer.isCreated) {
                messageChannel.sendMessage(sender.getAsMention() +
                        ", `вы еще не распределили характеристики!` :warning:").queue();
                return;
            }

            if (subContent[1].equalsIgnoreCase("inventory")) {
                if (subContent.length > 3) {
                    if (subContent[2].equalsIgnoreCase("info")) {
                        String itemKey = content.replace("a!rpg inventory info ", "");
                        itemKey = itemKey.toLowerCase();
                        ItemStack item = rpgPlayer.getInventory().getItemInInventory(itemKey);
                        if (item == null) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `у вас нет такого предмета` :warning:").queue();
                            return;
                        }

                        EmbedBuilder embedBuilder = item.getItemBase().getItemDescription();
                        embedBuilder.setAuthor(rpgPlayer.getName(),
                                "http://twitch.tv/aymandev", sender.getUser().getAvatarUrl());
                        messageChannel.sendMessage(embedBuilder.build()).queue();
                        return;
                    }

                    if (subContent[2].equalsIgnoreCase("equip")) {
                        if (playerQuestData.hasCurrentQuest()) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `нельзя одевать предметы во время задания` :warning:").queue();
                            return;
                        }
                        String slotStr = subContent[3];
                        EnumInventorySlot slot = EnumInventorySlot.getSlotByName(slotStr);
                        if (slot == null) {
                            messageChannel.sendMessage(sender.getAsMention() + ", `неверный слот!` :warning:").queue();
                            return;
                        }

                        String itemKey = content.replace("a!rpg inventory equip " + slotStr + " ", "");
                        ItemStack item = rpgPlayer.getInventory().getItemInInventory(itemKey.toLowerCase());
                        if (item == null) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `у вас нет такого предмета` :warning:").queue();
                            return;
                        }

                        if (!rpgPlayer.getInventory().canEquipItem(item.getItemBase())) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `ваши руки уже заняты другим типом предмета!` :warning:").queue();
                            return;
                        }

                        rpgPlayer.getInventory().equipItem(item.getItemBase(), slot);
                        item.getItemBase().addCharacteristicsToPlayer(rpgPlayer);
                        messageChannel.sendMessage(sender.getAsMention() +
                                ", `предмет " + item.getItemBase().getItemName() + " был экипирован!` :tada:").queue();
                        return;
                    }

                    if (subContent[2].equalsIgnoreCase("unequip")) {
                        if (playerQuestData.hasCurrentQuest()) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `нельзя снимать предметы во время задания` :warning:").queue();
                            return;
                        }

                        String slotKey = subContent[3];
                        EnumInventorySlot slot = EnumInventorySlot.getSlotByName(slotKey);
                        if (slot == null) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `неправильно указано название слота` :warning:").queue();
                            return;
                        }

                        ItemBase unequippedItem = rpgPlayer.getInventory().unequipItem(slot);
                        if (unequippedItem == null) {
                            messageChannel.sendMessage(sender.getAsMention() + ", `этот слот уже пустой!` :warning:").queue();
                            return;
                        }
                        messageChannel.sendMessage(sender.getAsMention() +
                                ", `вы сняли с себя " + unequippedItem.getItemName() + "` :warning:").queue();
                        unequippedItem.removeCharacteristicsFromPlayer(rpgPlayer);
                        return;
                    }

                    return;
                }

                EmbedBuilder embedBuilder = rpgPlayer.getInventory().getInventoryEmbed();
                messageChannel.sendMessage(embedBuilder.build()).queue();
                return;
            }

            if (subContent[1].equalsIgnoreCase("talents")) {
                HashMap<String, Talent> talentsToLearn = TalentList.talents.get(rpgPlayer.getLevel());

                if (talentsToLearn != null) {
                    if (subContent.length > 2) {
                        if (subContent.length > 3 && subContent[2].equalsIgnoreCase("learn")) {
                            if (playerQuestData.hasCurrentQuest()) {
                                messageChannel.sendMessage(sender.getAsMention() +
                                        ", `нельзя снимать предметы во время задания` :warning:").queue();
                                return;
                            }
                            if (!rpgPlayer.getTalents().hasTalentPoints()) {
                                messageChannel.sendMessage(sender.getAsMention() +
                                        ", `вы уже изучили все доступные таланты!` :warning:").queue();
                                return;
                            }

                            String talentNumberStr = subContent[3];
                            try {
                                int talentNumber = Integer.valueOf(talentNumberStr);
                                Talent talent = (Talent) talentsToLearn.values().toArray()[talentNumber - 1];
                                rpgPlayer.getTalents().learnTalent(talent);
                                messageChannel.sendMessage(sender.getAsMention() +
                                        ", `талант был успешно изучен!` :tada:").queue();
                                return;
                            } catch (Exception ignored) {
                                messageChannel.sendMessage(sender.getAsMention() +
                                        ", `ошибка в номере таланта!` :warning:").queue();
                                return;
                            }
                        }

                        if (subContent.length > 3 && subContent[2].equalsIgnoreCase("info")) {
                            String talentName = content.replace("a!rpg talents info ", "");
                            Talent talent = TalentList.levelAllTalents.get(talentName.toLowerCase());
                            if (talent == null) {
                                messageChannel.sendMessage(sender.getAsMention() + ", `такого таланта не существует` :warning:").queue();
                                return;
                            }
                            messageChannel.sendMessage(talent.getTalentEmbed().build()).queue();
                            return;
                        }
                    }

                    if (!rpgPlayer.getTalents().hasTalentPoints()) {
                        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
                        embedBuilder.setAuthor(rpgPlayer.getName(), "http://twitch.tv/aymandev",
                                sender.getUser().getAvatarUrl());
                        embedBuilder.setTitle("Изученные таланты:");
                        for (Talent talent : rpgPlayer.getTalents().getLearnedTalents()) {
                            embedBuilder.addField(talent.getTalentName(),
                                    "Добавляет силы игроку: " + talent.calculateTalentPower(rpgPlayer), false);
                        }
                        messageChannel.sendMessage(embedBuilder.build()).queue();
                        return;
                    }

                    EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
                    embedBuilder.setAuthor(rpgPlayer.getName(), "http://twitch.tv/aymandev",
                            sender.getUser().getAvatarUrl());
                    embedBuilder.setTitle("Доступные для изучения таланты:");

                    int talentNumber = 1;
                    for (Talent talent : talentsToLearn.values()) {
                        embedBuilder.addField("[" + talentNumber + "]" + talent.getTalentName(),
                                "Добавит силы игроку: " + talent.calculateTalentPower(rpgPlayer), false);
                        talentNumber++;
                    }
                    messageChannel.sendMessage(embedBuilder.build()).queue();
                    return;
                } else {
                    messageChannel.sendMessage(sender.getAsMention() + ", `нет талантов для изучения` :warning:").queue();
                    return;
                }
            }

            if (subContent[1].equalsIgnoreCase("quests")) {
                if (subContent.length > 2) {
                    if (subContent[2].equalsIgnoreCase("legendary")) {
                        if (QuestList.legendaryQuest == null) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `легендарное задание недоступно!` :warning:").queue();
                            return;
                        }

                        if (subContent.length > 3 && subContent[3].equalsIgnoreCase("take")) {
                            rpgPlayer.getQuestData().setCurrentQuestData(QuestList.legendaryQuest);
                            rpgPlayer.getQuestData().getQuestPlayerData().setRpgPlayer(rpgPlayer);

                            float escapeChance = rpgPlayer.getQuestData().getQuestPlayerData().calculateEscapeChance(rpgPlayer);
                            float winChance = rpgPlayer.getQuestData().getQuestPlayerData().calculateWinChance(rpgPlayer);
                            rpgPlayer.getQuestData().getQuestPlayerData().setEscapeChance(escapeChance);
                            rpgPlayer.getQuestData().getQuestPlayerData().setWinChance(winChance);
                            rpgPlayer.getQuestData().getQuestPlayerData().startQuestTimer();

                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `вы начали выполнение ЛЕГЕНДАРНОГО задания!` :warning:").queue();
                            return;
                        }

                        EmbedBuilder embedBuilder = QuestList.legendaryQuest.getQuestEmbed();
                        messageChannel.sendMessage(embedBuilder.build()).queue();
                        return;
                    }

                    if (subContent[2].equalsIgnoreCase("active")) {
                        if (!playerQuestData.hasCurrentQuest()) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `вы не выполняете задание!` :warning:").queue();
                            return;
                        }
                        EmbedBuilder embedBuilder = playerQuestData.getCompletingQuestDataEmbed();
                        messageChannel.sendMessage(embedBuilder.build()).queue();
                        return;
                    }

                    if (subContent.length > 3 && subContent[2].equalsIgnoreCase("take")) {
                        if (playerQuestData.hasCurrentQuest()) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `вы уже выполняете задание!` :warning:").queue();
                            return;
                        }

                        String questKey = content.replace("a!rpg quests take ", "");
                        questKey = questKey.toLowerCase();
                        Quest quest = rpgPlayer.getQuestData().getQuest(questKey);

                        if (quest == null) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `такое задание недоступно!` :tada:").queue();
                            return;
                        }

                        playerQuestData.takeQuest(quest);
                        messageChannel.sendMessage(sender.getAsMention() +
                                ", `вы начали выполнение задания " + quest.getLocalizedName() + "!` :tada:").queue();
                        return;
                    }
                    if (subContent.length > 3 && subContent[2].equalsIgnoreCase("info")) {
                        String questKey = content.replace("a!rpg quests info ", "");
                        questKey = questKey.toLowerCase();

                        QuestPlayerData questPlayerData = rpgPlayer.getQuestData().getQuestPlayerData();
                        if (questPlayerData != null) {
                            messageChannel.sendMessage(questPlayerData.getQuestEmbed().build()).queue();
                            return;
                        }

                        Quest quest = rpgPlayer.getQuestData().getQuest(questKey);
                        if (quest == null) {
                            messageChannel.sendMessage(sender.getAsMention() + ", `такого задания нет в списке!` :warning:").queue();
                            return;
                        }

                        questPlayerData = new QuestPlayerData(quest, rpgPlayer);
                        EmbedBuilder embedBuilder = questPlayerData.getQuestEmbed();
                        messageChannel.sendMessage(embedBuilder.build()).queue();
                        return;
                    }
                }

                EmbedBuilder embedBuilder = playerQuestData.getAvailableQuests();
                messageChannel.sendMessage(embedBuilder.build()).queue();
                return;
            }

            if (subContent[1].equalsIgnoreCase("pet")) {
                RPGPet rpgPet = rpgPlayer.getPetData();

                if (rpgPet == null) {
                    messageChannel.sendMessage(sender.getAsMention() + ", `у вас нет питомца!` :warning:")
                            .queue();
                    return;
                }

                if (subContent.length > 4) {
                    if (subContent[2].equalsIgnoreCase("setup")) {
                        if (playerQuestData.hasCurrentQuest()) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `нельзя распределять характеристики во время задания` :warning:").queue();
                            return;
                        }
                        String charName = subContent[3].toLowerCase();
                        String valueStr = subContent[4];
                        EnumCharacteristic characteristic = EnumCharacteristic.getCharacteristicByName(charName);
                        int value = Integer.valueOf(valueStr);

                        if (characteristic == null) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `такой характеристики нет у питомца!` :warning:")
                                    .queue();
                            return;
                        }

                        if (!characteristic.isUpgradable) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `эту характеристику нельзя улучшать у питомца!` :warning:")
                                    .queue();
                            return;
                        }

                        if (value > rpgPet.getUpgradePoints()) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `недостаточно очков улучшений питомца!` :warning:")
                                    .queue();
                            return;
                        }

                        rpgPet.addCharacteristic(characteristic, value, true);
                        messageChannel.sendMessage(sender.getAsMention() +
                                ", `характеристика была прокачена! Осталось: " +
                                rpgPet.getUpgradePoints() + " очков!` :tada:")
                                .queue();
                        return;
                    }
                }

                if (subContent.length > 2) {
                    if (subContent[2].equalsIgnoreCase("talents")) {
                        if (subContent.length > 3 && subContent[3].equalsIgnoreCase("list")) {
                            messageChannel.sendMessage(rpgPet.getPetTalentsEmbed().build()).queue();
                            return;
                        }
                        messageChannel.sendMessage(rpgPet.getPetLearnedTalentsEmbed().build()).queue();
                        return;
                    }
                    if (subContent[2].equalsIgnoreCase("release")) {
                        if (playerQuestData.hasCurrentQuest()) {
                            messageChannel.sendMessage(sender.getAsMention() +
                                    ", `нельзя отпустить питомца во время задания` :warning:").queue();
                            return;
                        }
                        rpgPlayer.setPetData(null);
                        messageChannel.sendMessage(sender.getAsMention() + ", `вы отпустили питомца!` :warning:").queue();
                        return;
                    }
                }

                messageChannel.sendMessage(rpgPet.getPetEmbed().build()).queue();
                return;
            }
        }

        if (rpgPlayer != null) {
            EmbedBuilder embedBuilder = rpgPlayer.getEmbedOfPlayer();
            messageChannel.sendMessage(embedBuilder.build()).queue();
            return;
        } else {
            messageChannel.sendMessage(sender.getAsMention() + ", `сначала создайте персонажа!` :warning:")
                    .queue();
        }

        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("rpg", "rpg-2", "console");
    }

    @Override
    public String getCommand() {
        return "rpg";
    }
}
