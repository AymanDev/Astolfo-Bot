package jp.aymandev.astolfo.helpers;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by AymanDev on 26.07.2018.
 */
public class RoleHelper {
    public static Role animeRole;
    public static Role ffaWinners;
    public static Role topOneShoters;

    public static Role approvedRole;
    public static Role trustedRole;

    public static Role nsfwRole;
    public static Role coubRole;
    public static Role rpgRole;

    public RoleHelper(Guild guild) {
       /* animeRole = guild.getRolesByName("anime_viewers", true).get(0);
        ffaWinners = guild.getRolesByName("FFA Winners", true).get(0);
        topOneShoters = guild.getRolesByName("Top Oneshoters", true).get(0);*/
        approvedRole = guild.getRoleById("473816518648659968");
        trustedRole = guild.getRoleById("474606905155780638");
        nsfwRole = guild.getRoleById("473814142898405378");
        rpgRole = guild.getRoleById("473815702722052106");
        coubRole = guild.getRoleById("473916547497328650");
    }
}
