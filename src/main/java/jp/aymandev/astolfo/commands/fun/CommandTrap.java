package jp.aymandev.astolfo.commands.fun;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.EmoteHelper;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandTrap extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        messageChannel.sendMessage(sender.getAsMention() +
                ", братек, войди в меня, а потом я в тебя " +
                EmoteHelper.astolfoWink.getAsMention())
                .queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public String getCommand() {
        return "trap";
    }
}
