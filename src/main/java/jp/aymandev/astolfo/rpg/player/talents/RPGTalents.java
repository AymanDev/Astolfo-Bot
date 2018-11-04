package jp.aymandev.astolfo.rpg.player.talents;

import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AymanDev on 29.07.2018.
 */
public class RPGTalents {

    private HashMap<String, Talent> learnedTalents = new HashMap<>();
    private int talentPoints = 0;

    private JSONObject talentsData = new JSONObject();
    private RPGPlayer rpgPlayer;

    public RPGTalents(RPGPlayer rpgPlayer) {
        this.rpgPlayer = rpgPlayer;
        talentPoints = 1;
    }


    public void loadData(JSONObject playerData) {
        if (playerData.has("talents")) {
            talentsData = playerData.getJSONObject("talents");
            talentPoints = talentsData.getInt("talentPoints");
            JSONArray learnedArray = talentsData.getJSONArray("learned");
            for (int i = 0; i < learnedArray.length(); i++) {
                String talentKey = learnedArray.getString(i);
                Talent talent = TalentList.levelAllTalents.get(talentKey);
                learnedTalents.put(talentKey, talent);
            }
        }
    }

    public JSONObject writeData(JSONObject playerData) {
        talentsData.put("talentPoints", talentPoints);
        talentsData.put("learned", learnedTalents.keySet());
        playerData.put("talents", talentsData);
        return playerData;
    }

    public boolean isLearned(String talentName) {
        Talent talent = TalentList.levelAllTalents.get(talentName);
        return isLearned(talent);
    }

    public boolean isLearned(Talent talent) {
        return getLearnedTalents().contains(talent);
    }

    public List<Talent> getLearnedTalents() {
        return Arrays.asList(learnedTalents.values().toArray(new Talent[0]));
    }

    public boolean hasTalentPoints() {
        return talentPoints > 0;
    }

    public void learnTalent(Talent talent) {
        talent.onTalentLearn(rpgPlayer);
        learnedTalents.put(talent.getTalentName().toLowerCase(), talent);
        talentPoints--;

        rpgPlayer.setHasToLearnTalents(false);
        rpgPlayer.addExperience(0);
    }

    public int getTalentPoints() {
        return talentPoints;
    }

    public RPGTalents setTalentPoints(int talentPoints) {
        this.talentPoints = talentPoints;
        return this;
    }
}
