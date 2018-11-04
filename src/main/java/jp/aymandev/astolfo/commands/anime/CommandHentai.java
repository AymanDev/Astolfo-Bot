package jp.aymandev.astolfo.commands.anime;

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
public class CommandHentai extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        messageChannel.sendMessage(sender.getAsMention() +
                " :warning: `Функция временно отключена!` :warning:").queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("nsfw-arts", "console");
    }

    @Override
    public String getCommand() {
        return "hentai";
    }
}
