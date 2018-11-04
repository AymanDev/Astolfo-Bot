package jp.aymandev.astolfo.rpg;

import jp.aymandev.astolfo.rpg.items.ItemList;
import jp.aymandev.astolfo.rpg.pets.PetList;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import jp.aymandev.astolfo.rpg.player.talents.TalentList;
import jp.aymandev.astolfo.rpg.quests.QuestList;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class RPGCore {

    //private RPGClasses rpgClasses;
    private ItemList itemList;
    private TalentList talentList;
    private QuestList questList;
    private PetList petList;
    public static final HashMap<String, RPGPlayer> players = new HashMap<>();

    public RPGCore(Guild guild) {
        //rpgClasses = new RPGClasses();
        itemList = new ItemList();
        talentList = new TalentList();
        questList = new QuestList();
        petList = new PetList();

        loadAllPlayers(guild);
    }

    private void loadAllPlayers(Guild guild) {
        File playersFolder = new File("data/players/");
        File[] playerFiles = playersFolder.listFiles();

        for (File playerFile : playerFiles != null ? playerFiles : new File[0]) {
            JSONObject playerDataJson = loadPlayerDataFromFile(playerFile.getName());
            if (playerDataJson.getJSONObject("questData").has("currentQuest")) {
                loadPlayerFromFile(playerFile.getName(), guild);
            }
        }
    }

    public static void loadPlayerInDatabase(RPGPlayer rpgPlayer) {
        players.put(rpgPlayer.getUserID(), rpgPlayer);
    }

    public static RPGPlayer getPlayer(Member member) {
        if (players.containsKey(member.getUser().getId())) {
            return players.get(member.getUser().getId());
        } else {
            return loadPlayerFromFile(member.getUser().getId() + ".json", member.getGuild());
        }
    }

    public static JSONObject loadDataFromFile(String fileName) {
        JSONParser parser = new JSONParser();
        File file = new File("data/", fileName);
        if (!file.exists()) return null;

        try {
            FileReader fileReader = new FileReader(file);
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser
                    .parse(fileReader);
            fileReader.close();
            return new JSONObject(jsonObject.toJSONString());
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject loadPlayerDataFromFile(String fileName) {
        return loadDataFromFile("players/" + fileName);
    }

    public static RPGPlayer loadPlayerFromFile(String fileName, Guild guild) {
        File playerFile = new File("data/players", fileName);
        if (!playerFile.exists()) return null;

        String userId = fileName.replace(".json", "");
        RPGPlayer rpgPlayer = new RPGPlayer(userId, guild);
        rpgPlayer = rpgPlayer.loadData(loadPlayerDataFromFile(fileName));
        loadPlayerInDatabase(rpgPlayer);
        return rpgPlayer;
    }

    public static void savePlayer(RPGPlayer rpgPlayer) {
        saveFile(rpgPlayer.writeData(), "players/" + rpgPlayer.getUserID());
        /*JSONObject playerData = rpgPlayer.writeData();
        try {
            FileWriter playerFile = new FileWriter("data/players/" + rpgPlayer.getUserID() + ".json");
            playerFile = (FileWriter) playerData.write(playerFile);
            playerFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void saveFile(JSONObject jsonObject, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter("data/" + fileName + ".json");
            fileWriter = (FileWriter) jsonObject.write(fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteMemberPlayer(Member member) {
        return deletePlayer(getPlayer(member));
    }

    public static boolean deletePlayer(RPGPlayer rpgPlayer) {
        if (rpgPlayer == null) return false;

        File playerFile = new File("data/players/" + rpgPlayer.getUserID() + ".json");
        if (!playerFile.exists()) return false;

        if (rpgPlayer.getQuestData().hasCurrentQuest()) rpgPlayer.getQuestData().getQuestPlayerData().cancelTimer();
        players.remove(rpgPlayer.getUserID());
        return playerFile.delete();
    }
}
