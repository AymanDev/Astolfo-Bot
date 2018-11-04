package jp.aymandev.astolfo.commands.special;

import jp.aymandev.astolfo.AstolfoBot;
import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.AdminHelper;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.Collections;
import java.util.List;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandLastUpdate extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        sendedMessage.delete().queue();

        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.addField("Изменения в " + AstolfoBot.VERSION,
                "Разработка RPG-модуля", false);
        embedBuilder.addBlankField(false);
        embedBuilder.addField("+ Легендарное задание",
                "Добавлена система легендарных заданий. Раз в день есть шанс выпасть легендарному заданию, оно " +
                        "доступно всем игрокам и после выполнения пропадает у всех. `a!rpg quests legendary`", false);
        embedBuilder.addField("* Система изучения талантов и получения опыта",
                "Теперь ваш уровень не будет повышаться, пока вы не изучите таланты доступные для вашего уровня!",
                false);
        embedBuilder.addField("* Оптимизирована загрузка персонажей",
                "Оптимизирована загрузка персонажей. Теперь некоторые команды будут обрабатываться быстрее.",
                false);
        embedBuilder.addField("* Очистка всех персонажей",
                "Все персонажи были удалены из-за изминения архитектуры сохранений персонажей.",
                false);
        messageChannel.sendMessage(embedBuilder.build()).queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getPermissionedUsers() {
        return Collections.singletonList(AdminHelper.ownerId);
    }

    @Override
    public List<String> getAvailableChannels() {
        return null;
    }

    @Override
    public String getCommand() {
        return "lastupdate";
    }
}
