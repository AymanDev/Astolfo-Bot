package jp.aymandev.astolfo.helpers;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class MixFightHelper {
    public static String mixFightMessageId;

    public static void clearMixFight(Guild guild) {
        List<Member> oneShootersList = guild.getMembersWithRoles(RoleHelper.topOneShoters);
        List<Member> ffaWinnersList = guild.getMembersWithRoles(RoleHelper.ffaWinners);

        for (Member member : oneShootersList) {
            guild.getController().removeSingleRoleFromMember(member, RoleHelper.topOneShoters).queue();
        }

        for (Member member : ffaWinnersList) {
            guild.getController().removeSingleRoleFromMember(member, RoleHelper.ffaWinners).queue();
        }
    }
}
