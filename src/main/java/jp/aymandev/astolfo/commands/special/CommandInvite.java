package jp.aymandev.astolfo.commands.special;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.AdminHelper;
import jp.aymandev.astolfo.helpers.RoleHelper;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandInvite extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        sendedMessage.delete().queue();

        List<Member> invitedMembers = sendedMessage.getMentionedMembers();
        for (Member invitedMember : invitedMembers) {
            guild.getController().addSingleRoleToMember(invitedMember, RoleHelper.animeRole).queue();
        }
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getPermissionedUsers() {
        return Arrays.asList(AdminHelper.ownerId, AdminHelper.craftaId);
    }

    @Override
    public String getCommand() {
        return "invite";
    }
}
