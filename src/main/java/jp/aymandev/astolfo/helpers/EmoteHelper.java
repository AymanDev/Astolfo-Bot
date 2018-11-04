package jp.aymandev.astolfo.helpers;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;

/**
 * Created by AymanDev on 26.07.2018.
 */
public class EmoteHelper {
   // public static Emote acceptEmote;
    //public static Emote kappaPrideEmote;
    public static Emote astolfoWink;

    public EmoteHelper(Guild guild) {
        astolfoWink = guild.getEmoteById("472446267113603084");
        //acceptEmote = guild.getEmoteById("456514867000770560");
        //kappaPrideEmote = guild.getEmoteById("324561481351954432");
    }
}
