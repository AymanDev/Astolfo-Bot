package jp.aymandev.astolfo.commands.fun;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandPidor extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        sendedMessage.delete().queue();
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setImage("https://cdn.discordapp.com/attachments/319576848050814978/472119069420814337/Ztc8vZmE9y4.png");
        messageChannel.sendMessage(embedBuilder.build()).queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public String getCommand() {
        return "pidor";
    }
}
