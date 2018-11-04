package jp.aymandev.astolfo.commands.special;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.AdminHelper;
import jp.aymandev.astolfo.helpers.MixFightHelper;
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
public class CommandMix extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        sendedMessage.delete().queue();

        String content = sendedMessage.getContentRaw();
        String[] subContent = content.split(" ");

        if (subContent[1].equalsIgnoreCase("announce")) {
            messageChannel.sendMessage("@everyone Сбор на MixFight начинается с 7:00PM!")
                    .queue();
            return;
        }

        if (subContent[1].equalsIgnoreCase("start")) {
            messageChannel.sendMessage("@everyone Доступен вход в голосовой канал Organization для всех желающих!")
                    .queue();
            return;
        }

        if (subContent[1].equalsIgnoreCase("clear")) {
            MixFightHelper.clearMixFight(guild);
            return;
        }

        if (subContent[1].equalsIgnoreCase("win")) {
            List<Member> memberList = sendedMessage.getMentionedMembers();
            StringBuilder winnersMessage = new StringBuilder("Победившие пирожки: ");
            if(memberList.size() > 6){
                winnersMessage = new StringBuilder("Участвовавшие пирожки: ");
            }

            for (Member localMember : memberList) {
                winnersMessage.append(localMember.getAsMention()).append(", ");
                guild.getController().addSingleRoleToMember(localMember, RoleHelper.topOneShoters).queue();
            }
            winnersMessage.append("\n");
            winnersMessage.append(":kiss: Целую вас в пузико пока хозин не видит! ")
                    .append(":KappaPride:");
            messageChannel.sendMessage(winnersMessage).queue();
        }

        if (subContent[1].equalsIgnoreCase("ffawin")) {
            StringBuilder winnersMessage = new StringBuilder("Победители в FFA кексики: ");
            List<Member> memberList = sendedMessage.getMentionedMembers();

            for (Member localMember : memberList) {
                winnersMessage.append(localMember.getAsMention()).append(", ");
                guild.getController().addSingleRoleToMember(localMember, RoleHelper.ffaWinners).queue();
            }
            winnersMessage.append("\n");
            winnersMessage.append(":cake: Поздравляю вас, целовать не буду! (;¬_¬)");
            messageChannel.sendMessage(winnersMessage).queue();
        }

        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getPermissionedUsers() {
        return Arrays.asList(AdminHelper.ownerId, AdminHelper.craftaId);
    }

    @Override
    public String getCommand() {
        return "mix";
    }
}
