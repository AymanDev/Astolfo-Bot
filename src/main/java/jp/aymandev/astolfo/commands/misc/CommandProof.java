package jp.aymandev.astolfo.commands.misc;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import net.dv8tion.jda.core.EmbedBuilder;
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
public class CommandProof extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setImage("https://cdn.discordapp.com/attachments/472408819289554965/473828553801203723/unknown.png");
        messageChannel.sendMessage(embedBuilder.build()).queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("commands", "console");
    }

    @Override
    public String getCommand() {
        return "proof";
    }
}
