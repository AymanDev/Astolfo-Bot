package jp.aymandev.astolfo.commands.general;

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
public class CommandHelp extends Command {

    @Override
    public void executeCommand(JDA jda, Guild guild, MessageChannel messageChannel, Message sendedMessage, Member sender) {
        String content = sendedMessage.getContentRaw();
        String[] subContent = content.split(" ");

        if (subContent.length > 1) {
            EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
            if (subContent[1].equalsIgnoreCase("general")) {
                embedBuilder.addField("a!help", "Список всех команд", false);
                embedBuilder.addField("a!about", "Немного информации о боте", false);
            } else if (subContent[1].equalsIgnoreCase("anime")) {
                embedBuilder.addField("a!anime", "Случайная аниме с краткой информацией о ней", false);
                embedBuilder.addField("a!astolfo", "Случайный арт с Астольфо", false);
                embedBuilder.addField("a!astolfo add <art-link>", "Предложить добавить арт с Астольфо", false);
                embedBuilder.addField("a!hentai", "Случайный аниме хентай арт [ОТКЛЮЧЕНА]", false);
                embedBuilder.addField("a!random", "Случайный аниме арт", false);
                embedBuilder.addField("a!random add <art-link>", "Предложить аниме арт в список случайных", false);
                embedBuilder.addField("a!yandere", "Поиск случайного арта на yande.re", false);
                embedBuilder.addField("a!yandere <tags>", "Поиск случайного арта по тегам на yande.re", false);
            } else if (subContent[1].equalsIgnoreCase("fun")) {
                embedBuilder.addField("a!coub best", "Лучший коуб по мнению Астольфо", false);
                embedBuilder.addField("a!coub cool", "Классные коубу по мнению Астольфо", false);
                embedBuilder.addField("a!coub funny", "Смешные/веселые коубы по мнению Астольфо", false);
                embedBuilder.addField("a!coub add <coub-link>", "Предложить добавить коуб в список", false);
                embedBuilder.addField("a!fine", "This is fine", false);
                embedBuilder.addField("a!pidor", "Тут и так все ясно", false);
                embedBuilder.addField("a!trap", "Для любителей острых ощущений", false);
            } else if (subContent[1].equalsIgnoreCase("misc")) {
                embedBuilder.addField("a!ping", "Задержка от бота к Discord API", false);
                embedBuilder.addField("a!proof", "Картинка максимального потраченого времени за раз на бота", false);
            } else if (subContent[1].equalsIgnoreCase("rpg")) {
                embedBuilder.addField("Разрешенные каналы для использования", "#rpg, #rpg-2, #rpg-commands", false);
                embedBuilder.addField("a!rpg", "Получение текущей информации о персонаже", false);
                embedBuilder.addField("a!rpg guide", "Гайд как играть", false);
                embedBuilder.addField("a!rpg create <name>", "Создание персонажа", false);
                embedBuilder.addField("a!rpg suicide", "Удалить персонажа", false);
                embedBuilder.addField("a!rpg setup <characteristic> <amount>",
                        "Распределение очков прокачки на характеристики", false);
                embedBuilder.addField("a!rpg inventory",
                        "Инвентарь игрока", false);
                embedBuilder.addField("a!rpg inventory equip <slot-name> <item-name>",
                        "Экипировка предмета из инвентаря", false);
                embedBuilder.addField("a!rpg inventory unequip <slot-name>",
                        "Снятие предмета с игрока в инвентарь", false);
                embedBuilder.addField("a!rpg inventory info <item-name>",
                        "Более подробная информация о предмете в инвентаре", false);
                embedBuilder.addField("a!rpg talents",
                        "Отображает доступные для изучения или уже изученные таланты", false);
                embedBuilder.addField("a!rpg talents learn <talent-number>",
                        "Изучает доступный талант из списка `a!rpg talents`", false);
                embedBuilder.addField("a!rpg talents info <talent-name>",
                        "Выводит более подробную информацию о таланте", false);
                embedBuilder.addField("a!rpg quests",
                        "Список доступных заданий", false);
                embedBuilder.addField("a!rpg quests info <quest-name>",
                        "Информация о задании", false);
                embedBuilder.addField("a!rpg quests take <quest-name>",
                        "Начать выполнение задания", false);
                embedBuilder.addField("a!rpg quests legendary",
                        "Информация о текущем легендарном задании", false);
                embedBuilder.addField("a!rpg quests legendary take",
                        "Начать выполнение легендарного задания", false);
                embedBuilder.addField("a!rpg quests active",
                        "Список активных заданий", false);
                embedBuilder.addField("a!rpg  pet",
                        "Информация о текущем спутнике", false);
                embedBuilder.addField("a!rpg pet talents",
                        "Информация о талантах спутника", false);
                embedBuilder.addField("a!rpg pet release",
                        "Отпустить текущего спутника", false);
            } else {
                messageChannel.sendMessage(sender.getAsMention() + ", `неверная команда!` :warning:").queue();
                return;
            }

            embedBuilder.setImage("https://vignette.wikia.nocookie.net/anime-traps/images/8/8a/Rider_of_Black3.jpg/revision/latest?cb=20170827051831");
            messageChannel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setTitle("Список категорий команд:");

        embedBuilder.addField("General:", "Основные команды `a!help general`", false);
        embedBuilder.addField("Anime:", "Команды связанные с ОНЮМЭ `a!help anime`", false);
        embedBuilder.addField("FUN:", "Команды для веселья и не только `a!help fun`", false);
        embedBuilder.addField("RPG:", "Команды для RPG `a!help rpg`", false);
        embedBuilder.addField("Misc:", "Прочие команды `a!help misc`", false);

        embedBuilder.setImage("https://vignette.wikia.nocookie.net/anime-traps/images/8/8a/Rider_of_Black3.jpg/revision/latest?cb=20170827051831");
        messageChannel.sendMessage(embedBuilder.build()).queue();
        super.executeCommand(jda, guild, messageChannel, sendedMessage, sender);
    }

    @Override
    public List<String> getAvailableChannels() {
        return Arrays.asList("commands", "rpg", "rpg-2", "rpg-commands", "console");
    }

    @Override
    public String getCommand() {
        return "help";
    }
}
