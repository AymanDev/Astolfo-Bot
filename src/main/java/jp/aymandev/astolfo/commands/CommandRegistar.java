package jp.aymandev.astolfo.commands;

import jp.aymandev.astolfo.commands.anime.*;
import jp.aymandev.astolfo.commands.fun.CommandCoub;
import jp.aymandev.astolfo.commands.fun.CommandFine;
import jp.aymandev.astolfo.commands.fun.CommandPidor;
import jp.aymandev.astolfo.commands.fun.CommandTrap;
import jp.aymandev.astolfo.commands.general.CommandAbout;
import jp.aymandev.astolfo.commands.general.CommandHelp;
import jp.aymandev.astolfo.commands.misc.CommandPing;
import jp.aymandev.astolfo.commands.misc.CommandProof;
import jp.aymandev.astolfo.commands.rpg.CommandRPG;
import jp.aymandev.astolfo.commands.special.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class CommandRegistar {

    public static final HashMap<String, Command> commands = new HashMap<>();

    public static void loadCommands() throws IOException, ParseException {
        //anime
        registerCommand(new CommandRandom());
        registerCommand(new CommandHentai());
        registerCommand(new CommandYandere());
        registerCommand(new CommandAstolfo());
        registerCommand(new CommandAnime());

        //fun
        registerCommand(new CommandFine());
        registerCommand(new CommandPidor());
        registerCommand(new CommandCoub());
        registerCommand(new CommandTrap());

        //general
        registerCommand(new CommandHelp());
        registerCommand(new CommandAbout());

        //misc
        registerCommand(new CommandPing());
        registerCommand(new CommandProof());

        //special
        registerCommand(new CommandInvite());
        registerCommand(new CommandSay());
        registerCommand(new CommandLastUpdate());
        registerCommand(new CommandMix());
        registerCommand(new CommandDev());

        //rpg
        registerCommand(new CommandRPG());
    }

    public static void registerCommand(Command command) {
        commands.put(command.getCommand(), command);
    }
}
