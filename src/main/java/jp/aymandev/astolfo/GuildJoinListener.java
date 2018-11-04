package jp.aymandev.astolfo;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by AymanDev on 31.07.2018.
 */
public class GuildJoinListener extends ListenerAdapter {

    private static final String channelId = "473910702478852096";
    private static final String rulesId = "473817752382078986";
    private static final String nsfwId = "473886557783326722";

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        MessageChannel channel = guild.getTextChannelById(channelId);
        TextChannel rulesChannel = guild.getTextChannelById(rulesId);
        TextChannel nsfwRulesChannel = guild.getTextChannelById(nsfwId);

        channel.sendMessage("Привет " + member.getAsMention() + "! Ты Мой Будущий Пирожочек! " +
                "Обязательно прочитай " + rulesChannel.getAsMention() + " и " + nsfwRulesChannel.getAsMention() +
                ", а иначе тебе не поиграть со мной и не пообщаться со всеми посетителями клуба!").queue();
    }
}
