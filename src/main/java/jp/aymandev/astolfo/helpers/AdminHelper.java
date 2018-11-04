package jp.aymandev.astolfo.helpers;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class AdminHelper {
    public static final String ownerId = "219054622592073729";
    public static Member ownerMember;
    public static String craftaId = "236120245159329792";
    public static Member craftaMember;

    public AdminHelper(Guild guild) {
        ownerMember = guild.getMemberById(ownerId);
        craftaMember = guild.getMemberById(craftaId);
    }
}
