package jp.aymandev.astolfo.commands.misc;

import jp.aymandev.astolfo.commands.Command;
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
public class CommandPing extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        messageChannel.sendMessage(sender.getAsMention() + " :ping_pong: `Пинг: " + jda.getPing() + "ms`").queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("commands", "console");
    }

    @Override
    public String getCommand() {
        return "ping";
    }
}
