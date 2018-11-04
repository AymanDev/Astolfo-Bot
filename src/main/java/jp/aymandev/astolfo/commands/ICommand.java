package jp.aymandev.astolfo.commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public interface ICommand {

    /**
     * Preparing to use command.
     *  @param channel Text channel
     * @param sender  Sender of this command
     */
    boolean prepareExecuteCommand(MessageChannel channel, Member sender);

    /**
     * Command execution method
     *
     * @param jda           Current JDA instance
     * @param guild         Current guild
     * @param sendedMessage Sended message
     * @param sender        Sender of the message
     */
    void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender);

    /**
     * Returning list of users available to use this command.
     */
    List<String> getPermissionedUsers();

    /**
     * Returning list of channels available to use in it.
     */
    List<String> getAvailableChannels();

    /**
     * Returns command name
     * */
    String getCommand();

    /**
     * Returns cooldown of the command
     * */
    int getCooldown();

    /**
     * Returns of needed in cooldown
     * */
    boolean hasCooldown();

    /**
     * Starting cooldown
     * */
    void startCooldown();
}
