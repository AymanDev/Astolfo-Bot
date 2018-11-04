package jp.aymandev.astolfo.commands.anime;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.AdminHelper;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandRandom extends Command {

    private static JSONArray imagesData;

    public CommandRandom() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader("data/arts.json");
        org.json.simple.JSONArray jsonObject = (org.json.simple.JSONArray) parser
                .parse(fileReader);
        fileReader.close();
        imagesData = new JSONArray(jsonObject.toJSONString());
    }

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        String content = sendedMessage.getContentRaw();
        String[] subContent = content.split(" ");

        if (subContent.length > 1 && subContent[1].equalsIgnoreCase("add")) {
            AdminHelper.ownerMember.getUser()
                    .openPrivateChannel()
                    .queue(privateChannel ->
                            privateChannel.sendMessage(sender.getAsMention() + " `" + subContent[2] + "`").queue()
                    );

            messageChannel.sendMessage(sender.getAsMention() +
                    ", ваш запрос на новый арт был отправлен на проверку! :sparkling_heart:")
                    .queue();
            return;
        }

        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        int randomInt = random.nextInt(imagesData.length());

        embedBuilder.setImage(imagesData.getString(randomInt));
        messageChannel.sendMessage(embedBuilder.build()).queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("arts", "console");
    }

    @Override
    public String getCommand() {
        return "random";
    }
}
