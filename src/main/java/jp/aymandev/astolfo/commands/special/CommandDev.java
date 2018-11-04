package jp.aymandev.astolfo.commands.special;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.AdminHelper;
import jp.aymandev.astolfo.rpg.RPGCore;
import jp.aymandev.astolfo.rpg.items.ItemBase;
import jp.aymandev.astolfo.rpg.items.ItemList;
import jp.aymandev.astolfo.rpg.pets.Pet;
import jp.aymandev.astolfo.rpg.pets.PetList;
import jp.aymandev.astolfo.rpg.pets.RPGPet;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import jp.aymandev.astolfo.rpg.player.RPGQuestData;
import jp.aymandev.astolfo.rpg.quests.QuestList;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by AymanDev on 02.08.2018.
 */
public class CommandDev extends Command {

    private static final File itemsFolder = new File("data/items");

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        String content = sendedMessage.getContentRaw();
        String[] subContent = content.split(" ");
        RPGPlayer rpgPlayer = RPGCore.getPlayer(sender);

        if (rpgPlayer == null) {
            messageChannel.sendMessage(sender.getAsMention() + ", `создайте персонажа!`")
                    .queue();
            return;
        }

        if (subContent[1].equalsIgnoreCase("quests")) {
            if (subContent[2].equalsIgnoreCase("add")) {
                Message.Attachment attachment = sendedMessage.getAttachments().get(0);
                File file = new File("data/", "legendary-quest.json");
                attachment.download(file);

                try {
                    attachment.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject questData = RPGCore.loadDataFromFile("legendary-quest.json");
                QuestList.legendaryQuest = RPGQuestData.loadQuestData(questData, rpgPlayer);
                messageChannel.sendMessage(sender.getAsMention() + "`легендарное задание было создано!` :warning:").queue();
                return;
            }

            if (subContent[2].equalsIgnoreCase("reset")) {
                QuestList.resetLegendaryQuest();
                messageChannel.sendMessage(sender.getAsMention() + "`НАЧАТ СБРОС ЛЕГЕНДАРНОГО ЗАДАНИЯ У " +
                        "ВСЕХ ИГРОКОВ!` :warning:").queue();
                return;
            }
        }

        if (subContent[1].equalsIgnoreCase("exp")) {
            int amount = Integer.parseInt(subContent[2]);
            rpgPlayer.addExperience(amount);
            messageChannel.sendMessage(sender.getAsMention() + ", `опыт выдан!`")
                    .queue();
            return;
        }

        if (subContent[1].equalsIgnoreCase("items")) {
            if (subContent[2].equalsIgnoreCase("add")) {
                String name = content.replace("a!dev items add ", "").toLowerCase();
                Message.Attachment attachment = sendedMessage.getAttachments().get(0);
                File itemFile = new File(itemsFolder, name + ".json");
                boolean isDownloaded = attachment.download(itemFile);

                messageChannel.sendMessage(sender.getAsMention() + ", `предмет был загружен? : " + isDownloaded + "`").queue();
                try {
                    attachment.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            if (subContent[2].equalsIgnoreCase("remove")) {
                String name = content.replace("a!dev items remove ", "").toLowerCase();
                File itemFile = new File(itemsFolder, name + ".json");

                if (itemFile.exists()) {
                    boolean isDeleted = itemFile.delete();
                    messageChannel.sendMessage(sender.getAsMention() + ", `предмет был удален? : " + isDeleted + "`").queue();
                }
                return;
            }
            if (subContent[2].equalsIgnoreCase("load")) {
                String name = content.replace("a!dev items load ", "").toLowerCase();
                File itemFile = new File(itemsFolder, name + ".json");

                if (itemFile.exists()) {
                    try {
                        ItemList.loadItem(itemFile);
                        messageChannel.sendMessage(sender.getAsMention() + ", `предмет был загружен в список предметов!`").queue();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                        messageChannel.sendMessage(sender.getAsMention() + ", `предмет не был загружен!`").queue();
                    }
                } else {
                    messageChannel.sendMessage(sender.getAsMention() + ", `такого предмета не существует!`").queue();
                }
            }

            if (subContent[2].equalsIgnoreCase("give")) {
                String name = content.replace("a!dev items give ", "").toLowerCase();
                ItemBase itemBase = ItemList.getItemByName(name);
                if (itemBase == null) {
                    messageChannel.sendMessage(sender.getAsMention() + ", `такого предмета не существует!`")
                            .queue();
                    return;
                }


                rpgPlayer.getInventory().addItemToInventory(itemBase);
                messageChannel.sendMessage(sender.getAsMention() + ", `предмет был выдан!`")
                        .queue();
                return;
            }
        }

        if (subContent[1].equalsIgnoreCase("pet")) {
            if (subContent[2].equalsIgnoreCase("set")) {
                String name = content.replace("a!dev pet set ", "").toLowerCase();
                Pet pet = PetList.pets.get(name);

                if (pet == null) {
                    messageChannel.sendMessage(sender.getAsMention() + ", `такого питомца не существует!`")
                            .queue();
                    return;
                }

                rpgPlayer.setPetData(new RPGPet(rpgPlayer, pet));
                messageChannel.sendMessage(sender.getAsMention() + ", `питомец был установлен!`")
                        .queue();
            }
            if (subContent[2].equalsIgnoreCase("setanother")) {
                Member mentionedMember = sendedMessage.getMentionedMembers().get(0);
                String name = content.replace("a!dev pet setanother " + mentionedMember.getAsMention() + " ",
                        "").toLowerCase();
                Pet pet = PetList.pets.get(name);

                if (pet == null) {
                    messageChannel.sendMessage(sender.getAsMention() + ", `такого питомца не существует!`")
                            .queue();
                    return;
                }

                RPGPlayer anotherRpgPlayer = RPGCore.getPlayer(mentionedMember);
                anotherRpgPlayer.setPetData(new RPGPet(anotherRpgPlayer, pet));
                messageChannel.sendMessage(sender.getAsMention() + ", `питомец был установлен!`")
                        .queue();
                return;
            }
            if (!rpgPlayer.hasPet()) {
                messageChannel.sendMessage(sender.getAsMention() + ", `нет питомца!`")
                        .queue();
                return;
            }
            if (subContent[2].equalsIgnoreCase("exp")) {
                int amount = Integer.parseInt(subContent[3]);
                rpgPlayer.getPetData().addExperience(amount);
                messageChannel.sendMessage(sender.getAsMention() + ", `опыт выдан!`")
                        .queue();
                return;
            }
        }
    }

    @Override
    public List<String> getPermissionedUsers() {
        return Arrays.asList(AdminHelper.ownerId, AdminHelper.craftaId);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Collections.singletonList("console");
    }

    @Override
    public String getCommand() {
        return "dev";
    }
}
