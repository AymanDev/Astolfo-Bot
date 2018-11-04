package jp.aymandev.astolfo.commands.fun;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.AdminHelper;
import jp.aymandev.astolfo.helpers.CoubPoolHelper;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.helpers.RoleHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandCoub extends Command {

    private static JSONObject coubData;

    public CommandCoub() throws IOException, ParseException {
        FileReader fileReader = new FileReader("data/coubs.json");
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser
                .parse(fileReader);
        coubData = new JSONObject(jsonObject.toJSONString());
        fileReader.close();
    }

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        String content = sendedMessage.getContentRaw();
        String[] subContent = content.split(" ");

        if (subContent.length > 1) {
            if (subContent[1].equalsIgnoreCase("poll")) {
                sendedMessage.delete().queue();

                //Poll announce subcmd
                if (subContent.length > 2 && subContent[2].equalsIgnoreCase("announce")) {
                    //Poll end announce subcmd
                    if (subContent.length > 3 && subContent[3].equalsIgnoreCase("end")) {
                        messageChannel.sendMessage(RoleHelper.coubRole.getAsMention() + ", " +
                                jda.getSelfUser().getAsMention() +
                                " будет ждать вас до 9:00PM MSK, после чего будет выбран лучший " +
                                "коуб будущих 3-х дней.")
                                .queue();
                        return;
                    }
                    return;
                }

                //Poll start subcmd
                if (subContent.length > 2 && subContent[2].equalsIgnoreCase("start")) {
                    String localMsg = "`Astolfo Coub Poll:` " + RoleHelper.coubRole.getAsMention() +
                            ", " + jda.getSelfUser().getAsMention() +
                            " выбрал самые заинтересовавшие его коубы. Поставьте реакцию с " +
                            " соответствующей напротив коуба!";
                    messageChannel.sendMessage(localMsg).queue();

                    EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();

                    for (int i = 0; i < CoubPoolHelper.coubLinks.length; i++) {
                        String link = CoubPoolHelper.coubLinks[i];
                        String emojiStr = CoubPoolHelper.emojisList[i];
                        embedBuilder.addField(emojiStr, link, true);
                    }

                    messageChannel.sendMessage(embedBuilder.build()).queue(coubPoolStartMsg -> {
                        coubPoolStartMsg.addReaction("\uD83C\uDF47").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF48").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF49").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF4A").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF4B").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF4C").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF4D").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF4E").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF4F").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF50").queue();
                        coubPoolStartMsg.addReaction("\uD83C\uDF51").queue();
                        CoubPoolHelper.messageId = coubPoolStartMsg.getId();
                    });

                    embedBuilder = EmbedHelper.generateDefaultEmbed();
                    embedBuilder.setImage("https://cdn.discordapp.com/emojis/470972105123561483.gif?v=1");
                    messageChannel.sendMessage(embedBuilder.build()).queue();
                    return;
                }

                //Poll end subcmd
                if (subContent.length > 2 && subContent[2].equalsIgnoreCase("end")) {
                    messageChannel.getMessageById(CoubPoolHelper.messageId).queue(pollMessage -> {
                        String bestCoubLink = "null";
                        int lastCoubEmotes = 0;
                        List<MessageReaction> messageReactions = pollMessage.getReactions();
                        for (MessageReaction messageReaction : messageReactions) {
                            String reactionEmoteName = messageReaction.getReactionEmote().getName();
                            if (!Arrays.asList(CoubPoolHelper.emojisList).contains(reactionEmoteName))
                                continue;

                            if (messageReaction.getCount() > lastCoubEmotes) {
                                List<String> emojiList = Arrays.asList(CoubPoolHelper.emojisList);
                                int index = emojiList.indexOf(messageReaction.getReactionEmote().getName());
                                String link = CoubPoolHelper.coubLinks[index];
                                lastCoubEmotes = messageReaction.getCount();
                                bestCoubLink = link;
                            }
                        }

                        messageChannel.sendMessage(RoleHelper.coubRole.getAsMention() + ", " +
                                jda.getSelfUser().getAsMention() + " подводит итоги голосования, коуб: " +
                                bestCoubLink + ", набрал: " + lastCoubEmotes + " голоса(ов)!").queue();
                    });
                }
                return;
            }

            if (subContent[1].equalsIgnoreCase("add")) {
                AdminHelper.ownerMember.getUser()
                        .openPrivateChannel().queue(privateChannel ->
                        privateChannel.sendMessage(sender.getAsMention() + " `" +
                                content.replace("a!coub add", "") + "`")
                                .queue());
                messageChannel.sendMessage(sender.getAsMention() +
                        ", ваш запрос на новый коуб был отправлен на проверку! :sparkling_heart:")
                        .queue();
                return;
            }

            if (subContent[1].equalsIgnoreCase("set")) {
                String messageId = subContent[2];
                CoubPoolHelper.messageId = messageId;
                return;
            } /*else if (subContent[1].equalsIgnoreCase("answer")) {

                return;
            }*/

            if (subContent[1].equalsIgnoreCase("funny")) {
                JSONArray jsonArray = coubData.getJSONArray("funny");
                int randomId = random.nextInt(jsonArray.length());

                messageChannel.sendMessage(sender.getAsMention() + ", один из смешных коубов: " +
                        jsonArray.getString(randomId))
                        .queue();
                return;
            }

            if (subContent[1].equalsIgnoreCase("cool")) {
                JSONArray jsonArray = coubData.getJSONArray("cool");
                int randomId = random.nextInt(jsonArray.length());

                messageChannel.sendMessage(sender.getAsMention() + ", один из классных коубов: " +
                        jsonArray.getString(randomId))
                        .queue();
                return;
            }

            if (subContent[1].equalsIgnoreCase("best")) {
                messageChannel.sendMessage(sender.getAsMention() + ", лучший коуб по итогам голосования: " +
                        coubData.getString("best"))
                        .queue();
                return;
            }
        } else {
            messageChannel.sendMessage(sender.getAsMention() + ", неверная команда! :warning:").queue();
        }
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("coub-news", "coubs", "coubs-2", "console");
    }

    @Override
    public String getCommand() {
        return "coub";
    }
}
