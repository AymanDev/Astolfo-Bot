package jp.aymandev.astolfo.commands.anime;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
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
public class CommandAnime extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        messageChannel.sendTyping().queue();

        try {
            HttpResponse<String> response = Unirest.get("https://tv-v2.api-fetch.website/random/anime").asString();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String slug = jsonObject.getString("slug");
            String genres = jsonObject.getJSONArray("genres").toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")
                    .replace(",", ", ");

            response = Unirest.get("https://kitsu.io/api/edge/anime?filter[text]=" + slug).asString();
            jsonObject = new JSONObject(response.getBody());

            JSONArray data = jsonObject.getJSONArray("data");
            JSONObject animeData = data.getJSONObject(0);
            JSONObject animeAttributes = animeData.getJSONObject("attributes");

            EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
            embedBuilder.setTitle(animeAttributes.getString("canonicalTitle"), "https://kitsu.io/anime/" +
                    animeData.getString("id"));

            String description = animeAttributes.getString("synopsis");
            description = description.substring(0, (description.length() > 1024) ? 1024 : description.length());
            embedBuilder.setThumbnail(animeAttributes.getJSONObject("posterImage").getString("medium"));

            embedBuilder.addField("Описание:", description, false);
            embedBuilder.addBlankField(false);
            embedBuilder.addField("Тип: ", animeAttributes.getString("subtype"), true);
            embedBuilder.addField("Статус: ", animeAttributes.getString("status"), true);
            embedBuilder.addField("Жанры: ", genres, true);

            boolean nsfw = animeAttributes.getBoolean("nsfw");
            embedBuilder.addField("Возрастной рейтинг:",
                    animeAttributes.getString("ageRating") + ", " +
                            animeAttributes.getString("ageRatingGuide") + (nsfw ? ", NSFW" : ", SFW"),
                    false);

            if (!animeAttributes.isNull("episodeCount")) {
                String episodes = animeAttributes.getInt("episodeCount") + " серии(й)";

                if (!animeAttributes.isNull("episodeLength")) {
                    episodes += " по " +
                            (animeAttributes.getInt("episodeLength") / 60) + " минут(ы)";
                }
                embedBuilder.addField("Эпизоды: ", episodes, true);
            }

            if (!animeAttributes.isNull("coverImage")) {
                embedBuilder.setImage(animeAttributes.getJSONObject("coverImage").getString("large"));
            }

            messageChannel.sendMessage(embedBuilder.build()).queue();
            startCooldown();
        } catch (Exception e) {
            e.printStackTrace();
            messageChannel.sendMessage(sender.getAsMention() + " :warning:`На серверах произошла неизвестная ошибка!`:warning:").queue();
        }
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public int getCooldown() {
        return 5;
    }

    @Override
    public boolean hasCooldown() {
        return true;
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("anime", "console");
    }

    @Override
    public String getCommand() {
        return "anime";
    }
}
