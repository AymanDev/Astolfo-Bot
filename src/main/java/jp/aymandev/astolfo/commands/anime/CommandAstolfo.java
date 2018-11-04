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
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandAstolfo extends Command {

    private static JSONObject imagesData;

    public CommandAstolfo() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader("data/astolfo-images.json");
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser
                .parse(fileReader);
        imagesData = new JSONObject(jsonObject.toJSONString());
        fileReader.close();
    }

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        String content = sendedMessage.getContentRaw();
        String[] subContent = content.split(" ");
        messageChannel.sendTyping().queue();

        if (subContent.length > 1 && subContent[1].equalsIgnoreCase("add")) {
            AdminHelper.ownerMember.getUser()
                    .openPrivateChannel()
                    .queue(privateChannel ->
                            privateChannel.sendMessage("Astolfo Art: " +
                                    content.replace("a!astolfo add", ""))
                                    .queue()
                    );
            messageChannel.sendMessage(sender.getAsMention() +
                    ", заявка на новый арт была отправлена на рассмотрение! :sparkling_heart:")
                    .queue();
            return;
        }

        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        JSONArray imagesArray = imagesData.getJSONArray("images");
        int randomInt = random.nextInt(imagesArray.length());

        embedBuilder.setImage(imagesArray.getString(randomInt));
        messageChannel.sendMessage(embedBuilder.build()).queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return null;
    }

    @Override
    public String getCommand() {
        return "astolfo";
    }
}
