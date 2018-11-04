package jp.aymandev.astolfo;

import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.commands.CommandRegistar;
import jp.aymandev.astolfo.helpers.EmoteHelper;
import jp.aymandev.astolfo.helpers.RoleHelper;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by AymanDev on 26.07.2018.
 */
public class MessageListener extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        if (!event.getAuthor().isBot()) {
            channel.sendMessage("Простите, но хозяин мне не разрешил общаться тайно с кем-то!").queue();
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        JDA jda = event.getJDA();
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        Member member = message.getMember();
        String content = message.getContentDisplay().toLowerCase();

        if (member.getUser().isBot()) return;
        if (!content.startsWith("a!")) return;

        String localContent = message.getContentDisplay().toLowerCase();
        localContent = localContent.replace("a!", "");

        String[] splittedContent = localContent.split(" ");
        Command command = CommandRegistar.commands.get(splittedContent[0].toLowerCase());
        if (command != null) {
            boolean canUseCommand = command.prepareExecuteCommand(channel, member);
            if (canUseCommand) {
                command.executeCommand(jda, guild, channel, message, member);
            }
        } else {
            channel.sendMessage(member.getAsMention() + ", `неверная команда!` :warning:").queue();
        }
    }


    private static final String rulesChannelId = "473817752382078986";
    private static final String rulesApproveMessageId = "473937276141961229";

    private static final String nsfwRulesChannelId = "473886557783326722";
    private static final String nsfwApproveMessageId = "473937597165600768";

    private static final String coubRulesChannelId = "473914585922142248";
    private static final String coubApproveMessageId = "473917011156664330";

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        MessageChannel messageChannel = event.getChannel();
        Guild guild = event.getGuild();
        Member member = event.getMember();

        if (messageChannel.getId().equalsIgnoreCase(rulesChannelId)) {
            if (event.getMessageId().equalsIgnoreCase(rulesApproveMessageId)) {
                if (event.getReactionEmote().getId().equalsIgnoreCase(EmoteHelper.astolfoWink.getId())) {
                    guild.getController().addSingleRoleToMember(member, RoleHelper.approvedRole).queue();
                }
            }
        }
        if (messageChannel.getId().equalsIgnoreCase(nsfwRulesChannelId)) {
            if (event.getMessageId().equalsIgnoreCase(nsfwApproveMessageId)) {
                if (event.getReactionEmote().getId().equalsIgnoreCase(EmoteHelper.astolfoWink.getId())) {
                    guild.getController().addSingleRoleToMember(member, RoleHelper.nsfwRole).queue();
                }
            }
        }
        if (messageChannel.getId().equalsIgnoreCase(coubRulesChannelId)) {
            if (event.getMessageId().equalsIgnoreCase(coubApproveMessageId)) {
                if (event.getReactionEmote().getId().equalsIgnoreCase(EmoteHelper.astolfoWink.getId())) {
                    guild.getController().addSingleRoleToMember(member, RoleHelper.coubRole).queue();
                }
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        MessageChannel messageChannel = event.getChannel();
        Guild guild = event.getGuild();
        Member member = event.getMember();

        if (messageChannel.getId().equalsIgnoreCase(rulesChannelId)) {
            if (event.getMessageId().equalsIgnoreCase(rulesApproveMessageId)) {
                if (event.getReactionEmote().getId().equalsIgnoreCase(EmoteHelper.astolfoWink.getId())) {
                    guild.getController().removeSingleRoleFromMember(member, RoleHelper.approvedRole).queue();
                }
            }
        }

        if (messageChannel.getId().equalsIgnoreCase(nsfwRulesChannelId)) {
            if (event.getMessageId().equalsIgnoreCase(nsfwApproveMessageId)) {
                if (event.getReactionEmote().getId().equalsIgnoreCase(EmoteHelper.astolfoWink.getId())) {
                    guild.getController().removeSingleRoleFromMember(member, RoleHelper.nsfwRole).queue();
                }
            }
        }
        if (messageChannel.getId().equalsIgnoreCase(coubRulesChannelId)) {
            if (event.getMessageId().equalsIgnoreCase(coubApproveMessageId)) {
                if (event.getReactionEmote().getId().equalsIgnoreCase(EmoteHelper.astolfoWink.getId())) {
                    guild.getController().removeSingleRoleFromMember(member, RoleHelper.coubRole).queue();
                }
            }
        }
    }
}
