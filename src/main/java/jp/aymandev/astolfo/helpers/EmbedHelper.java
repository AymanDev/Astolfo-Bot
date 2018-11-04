package jp.aymandev.astolfo.helpers;

import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class EmbedHelper {

    private static String adminAvatarUrl = "https://cdn.discordapp.com/attachments/472408819289554965/477161004392906752/tumblr_mp7inz8T6M1rjhe3so1_500.gif";
    private static String astolfoAvatarUrl = "https://cdn.discordapp.com/attachments/472408819289554965/472441706608853012/ava.png";

    public static EmbedBuilder generateDefaultEmbed(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("Astolfo", "http://anime-traps.wikia.com/wiki/Astolfo",
                astolfoAvatarUrl);

        Color color = new Color(0xffeb2b89);
        embedBuilder.setColor(color);
        embedBuilder.setFooter("Created by AymanDev",
                adminAvatarUrl);
        return embedBuilder;
    }
}
