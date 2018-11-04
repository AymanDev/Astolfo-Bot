package jp.aymandev.astolfo.rpg.items;

import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.EnumInventorySlot;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by AymanDev on 29.07.2018.
 */
public class ItemList {

    public static final HashMap<String, ItemBase> itemList = new HashMap<>();
    private static final JSONParser parser = new JSONParser();

    public ItemList() {
        File itemsFolder = new File("data/items");

        for (File itemFile : Objects.requireNonNull(itemsFolder.listFiles())) {
            try {
                loadItem(itemFile);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }
       /* registerItem(new ItemBase("Лук Авантюриста", EnumCharacteristic.AGILITY)
                .setItemShortDescription("Лук выдающийся всем начинающим авантюристам")
                .addCharacteristic(EnumCharacteristic.AGILITY, 4)
                .setItemSlot(EnumInventorySlot.RIGHTHAND)
                .setItemType(EnumItemType.BOW)
                .setItemIcon("http://gurps.iddqd.club/gurps_weapons/img/1910.png")
        );
        registerItem(new ItemBase("Посох Авантюриста", EnumCharacteristic.INTELLIGENCE)
                .setItemShortDescription("Посох выдающийся всем начинающим авантюристам")
                .addCharacteristic(EnumCharacteristic.INTELLIGENCE, 4)
                .setItemSlot(EnumInventorySlot.RIGHTHAND)
                .setItemType(EnumItemType.STAFF)
                .setItemIcon("https://cdna.artstation.com/p/assets/images/images/003/565/822/large/george-k-wizard-staff.jpg")
        );
        registerItem(new ItemBase("Меч Авантюриста", EnumCharacteristic.STRENGTH)
                .setItemShortDescription("Посох выдающийся всем начинающим авантюристам")
                .addCharacteristic(EnumCharacteristic.STRENGTH, 1)
                .addCharacteristic(EnumCharacteristic.AGILITY, 1)
                .setItemSlot(EnumInventorySlot.BOTHHAND)
                .setItemType(EnumItemType.ONEHAND)
                .setItemIcon("https://d1u5p3l4wpay3k.cloudfront.net/pathofexile_ru_gamepedia/d/d1/%D0%A1%D0%BF%D0%BB%D0%BE%D1%88%D0%BD%D0%BE%D0%B5_%D0%BB%D0%B5%D0%B7%D0%B2%D0%B8%D0%B5.png")
        );
        registerItem(new ItemBase("Двуручный Меч Авантюриста", EnumCharacteristic.STRENGTH)
                .setItemShortDescription("Меч выдающийся всем начинающим авантюристам")
                .addCharacteristic(EnumCharacteristic.STRENGTH, 4)
                .setItemSlot(EnumInventorySlot.RIGHTHAND)
                .setItemType(EnumItemType.TWOHAND)
                .setItemIcon("https://d1u5p3l4wpay3k.cloudfront.net/pathofexile_ru_gamepedia/a/aa/Two-Handed_Sword.png")
        );
        registerItem(new ItemBase("Кинжал Авантюриста", EnumCharacteristic.AGILITY)
                .setItemShortDescription("Кинжал выдающийся всем начинающим авантюристам")
                .addCharacteristic(EnumCharacteristic.AGILITY, 1)
                .setItemSlot(EnumInventorySlot.BOTHHAND)
                .setItemType(EnumItemType.DAGGER)
                .setItemIcon("https://d1u5p3l4wpay3k.cloudfront.net/pathofexile_ru_gamepedia/7/77/%D0%9C%D0%BD%D0%BE%D0%B3%D0%BE%D1%86%D0%B2%D0%B5%D1%82%D0%BD%D0%BE%D0%B5_%D0%B7%D0%B0%D1%82%D0%BC%D0%B5%D0%BD%D0%B8%D0%B5.png")
        );
        registerItem(new ItemBase("Перчатки Архимага", EnumCharacteristic.INTELLIGENCE)
                .setItemShortDescription("Перчатки носивший Великий Архимаг")
                .addCharacteristic(EnumCharacteristic.INTELLIGENCE, 10)
                .setMagicMod(2)
                .setItemSlot(EnumInventorySlot.ARTIFACT)
                .setItemIcon("https://d1u5p3l4wpay3k.cloudfront.net/skyrim_de_gamepedia/2/26/MysticTuningGloves.png")
        );*/
    }

    public static void loadItem(File itemFile) throws IOException, ParseException {
        FileReader fileReader = new FileReader(itemFile);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser
                .parse(fileReader);
        fileReader.close();

        JSONObject itemData = new JSONObject(jsonObject.toJSONString());
        String name = itemData.getString("name");
        EnumCharacteristic primaryCharacteristic =
                EnumCharacteristic.valueOf(itemData.getString("primaryCharacteristic"));
        EnumInventorySlot enumInventorySlot = EnumInventorySlot.valueOf(itemData.getString("slot"));
        EnumItemType enumItemType = EnumItemType.valueOf(itemData.getString("type"));
        ItemBase itemBase = new ItemBase(name, primaryCharacteristic);
        itemBase.setItemShortDescription(itemData.getString("description"));
        itemBase.setItemIcon(itemData.getString("icon"));
        itemBase.setItemSlot(enumInventorySlot);
        itemBase.setItemType(enumItemType);

        JSONObject characteristicsData = itemData.getJSONObject("characteristics");
        for (String charKey : characteristicsData.keySet()){
            EnumCharacteristic characteristic = EnumCharacteristic.valueOf(charKey);
            int value = characteristicsData.getInt(charKey);
            itemBase.addCharacteristic(characteristic, value);
        }

        JSONObject characteristicsModsData = itemData.getJSONObject("mods");
        itemBase.setStrengthMod(characteristicsModsData.getInt("strength"));
        itemBase.setAgilityMod(characteristicsModsData.getInt("agility"));
        itemBase.setMagicMod(characteristicsModsData.getInt("magic"));
        registerItem(itemBase);
    }

    public static ItemBase getItemByName(String itemName) {
        return itemList.get(itemName.toLowerCase());
    }

    private static void registerItem(ItemBase itemBase) {
        itemList.put(itemBase.getItemName().toLowerCase(), itemBase);
    }
}
