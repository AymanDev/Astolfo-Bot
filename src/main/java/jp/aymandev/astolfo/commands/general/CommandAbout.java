package jp.aymandev.astolfo.commands.general;

import jp.aymandev.astolfo.AstolfoBot;
import jp.aymandev.astolfo.commands.Command;
import jp.aymandev.astolfo.helpers.EmbedHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandAbout extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setThumbnail("https://cdn.discordapp.com/emojis/457495492696211462.gif");
        embedBuilder.addField("Инфо:", "Дискорд бот для Anime Club", false);
        embedBuilder.addBlankField(false);
        embedBuilder.addField("Дата начала разработки:", "27.7.2018 19:00", false);
        DateTime timeNow = DateTime.now();
        Period period = new Period(AstolfoBot.startDate, timeNow);
        embedBuilder.addField("Времени с последнего перезапуска/обновления:",
                period.getDays() + " дней " +
                        period.getHours() + " часов " +
                        period.getMinutes() + " минут " +
                        period.getSeconds() + " секунд",
                false);
        embedBuilder.addField("Текущая версия бота:", AstolfoBot.VERSION, false);

        messageChannel.sendMessage(embedBuilder.build()).queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }



    @Override
    public String getCommand() {
        return "about";
    }
}
