package jp.aymandev.astolfo.commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.*;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class Command implements ICommand {

    protected static final Random random = new Random();
    private Timer cooldownTimer = new Timer();
    private int currentCooldown = 0;

    /**
     * Checking for channel and users equally.
     *
     * @param channel Channel in which preparing to use command.
     * @param sender  User which trying to use command.
     */
    @Override
    public boolean prepareExecuteCommand(MessageChannel channel, Member sender) {
        if (hasCooldown()) {
            if (currentCooldown > 0) {
                channel.sendMessage(sender.getAsMention() +
                        ", `время до повторного использования команды: " + currentCooldown + "c` :warning:")
                        .queue();
                return false;
            }
        }

        if (getPermissionedUsers() != null) {
            if (getPermissionedUsers().contains(sender.getUser().getId())) {
                if (getAvailableChannels() != null) {
                    return getAvailableChannels().contains(channel.getName());
                }
                return getPermissionedUsers().contains(sender.getUser().getId());
            }
        } else {
            if (getAvailableChannels() != null) {
                return getAvailableChannels().contains(channel.getName());
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
       // return;
    }

    @Override
    public List<String> getPermissionedUsers() {
        return null;
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("console", "anime", "arts", "commands");
    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public boolean hasCooldown() {
        return false;
    }

    @Override
    public void startCooldown() {
        if (hasCooldown()) {
            currentCooldown = getCooldown();

            cooldownTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    currentCooldown--;

                    if (currentCooldown <= 0) {
                        cooldownTimer.cancel();
                        cooldownTimer = new Timer();
                    }
                }
            }, 1000, 1000);
        }
    }
}
