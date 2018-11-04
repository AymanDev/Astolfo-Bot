package jp.aymandev.astolfo.commands.anime;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandYandere extends Command {

    private final String yandereUrlStr = "https://yande.re/post.json?limit=1&tags=order:random%20";

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        String content = sendedMessage.getContentRaw();
        String tags = content.replace("a!yandere", "");

        tags = tags.replaceAll("yaoi", "");
        messageChannel.sendTyping().queue();

        try {
            HttpResponse<String> response = Unirest.get(yandereUrlStr + tags.replaceAll(" ", "%20"))
                    .asString();
            JSONArray jsonArray = new JSONArray(response.getBody());
            if (jsonArray.length() <= 0) {
                messageChannel.sendMessage(sender.getAsMention() + ", по данным тегам ничего нет!").queue();
                return;
            }

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String imageUrl = jsonObject.getString("file_url");
            EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();

            embedBuilder.setTitle("Yandere ID: " + jsonObject.getInt("id"));
            embedBuilder.addField("Author:", jsonObject.getString("author"), false);
            embedBuilder.setImage(imageUrl);
            messageChannel.sendMessage(embedBuilder.build()).queue();
            return;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("arts", "console");
    }

    @Override
    public String getCommand() {
        return "yandere";
    }
}
