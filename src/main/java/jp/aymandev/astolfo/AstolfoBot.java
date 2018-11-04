package jp.aymandev.astolfo;

import jp.aymandev.astolfo.commands.CommandRegistar;
import jp.aymandev.astolfo.helpers.AdminHelper;
import jp.aymandev.astolfo.helpers.EmoteHelper;
import jp.aymandev.astolfo.helpers.RoleHelper;
import jp.aymandev.astolfo.rpg.RPGCore;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import org.joda.time.DateTime;

import javax.security.auth.login.LoginException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AymanDev on 26.07.2018.
 */
public class AstolfoBot {

    public static DateTime startDate;
    public static final String VERSION = "0.6.2 [RPG WIP]";
    public static Guild currentGuild;

    public static void main(String[] args)
            throws LoginException, InterruptedException {
        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken("token")
                .buildBlocking();
        jda.addEventListener(new MessageListener());
        jda.addEventListener(new GuildJoinListener());

        new Timer().schedule(new TimerTask() {
            boolean watchingAnime = false;

            @Override
            public void run() {
                if (!watchingAnime) {
                    //jda.getPresence().setGame(Game.watching("как Хозяин пишет версию 0.5.8 ≧◡≦ (ノ^∇^)"));
                    jda.getPresence().setGame(Game.watching("(reading) Shingeki no Kyojin (ノ*゜▽゜*)"));
                    //jda.getPresence().setGame(Game.watching("как Хозяин в осу играет (ノ*゜▽゜*)"));
                    watchingAnime = true;
                } else {
                    jda.getPresence().setGame(Game.streaming("a!help - список команд. Версия: " + VERSION,
                            "https://www.twitch.tv/aymandev"));
                    watchingAnime = false;
                }
            }
        }, 0, 30000);

        startDate = DateTime.now();
        currentGuild = jda.getGuilds().get(0);

        new EmoteHelper(currentGuild);
        new RoleHelper(currentGuild);
        new AdminHelper(currentGuild);
        try {
            CommandRegistar.loadCommands();

        }catch (Exception exception){
            exception.printStackTrace();
        }

        //Player data saver
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (RPGPlayer rpgPlayer : RPGCore.players.values()) {
                    RPGCore.savePlayer(rpgPlayer);
                    System.out.println("[RPG] Saving " + rpgPlayer.getName() + ", ID: " + rpgPlayer.getUserID());
                }
            }
        }, 0, 15000);

        new RPGCore(currentGuild);
    }
}
