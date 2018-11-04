package jp.aymandev.astolfo.rpg.player;

import jp.aymandev.astolfo.helpers.EmbedHelper;
import jp.aymandev.astolfo.rpg.items.EnumItemType;
import jp.aymandev.astolfo.rpg.items.ItemBase;
import jp.aymandev.astolfo.rpg.items.ItemList;
import jp.aymandev.astolfo.rpg.items.ItemStack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by AymanDev on 29.07.2018.
 */
public class RPGInventory {

    private RPGPlayer rpgPlayer;

    private HashMap<String, ItemStack> bagsInventory = new HashMap<>();
    private LinkedHashMap<String, ItemBase> armorInventory = new LinkedHashMap<>();

    private JSONObject inventoryData = new JSONObject();

    public RPGInventory(RPGPlayer rpgPlayer) {
        this.rpgPlayer = rpgPlayer;
        armorInventory.put(EnumInventorySlot.HEAD.name(), null);
        armorInventory.put(EnumInventorySlot.ARTIFACT.name(), null);
        armorInventory.put(EnumInventorySlot.LEFTHAND.name(), null);
        armorInventory.put(EnumInventorySlot.RIGHTHAND.name(), null);
    }

    public void loadData(JSONObject playerData) {
        inventoryData = playerData.getJSONObject("inventory");
        if (inventoryData.has("armor")) {
            JSONObject armorData = inventoryData.getJSONObject("armor");
            for (String slotKey : armorData.keySet()) {
                ItemBase itemBase = ItemList.getItemByName(armorData.getString(slotKey));
                if (itemBase != null) {
                    armorInventory.put(slotKey, itemBase);
                }
            }
        }

        if (inventoryData.has("bags")) {
            JSONObject bagsData = inventoryData.getJSONObject("bags");
            for (String itemKey : bagsData.keySet()) {
                JSONObject itemData = bagsData.getJSONObject(itemKey);
                ItemBase itemBase = ItemList.getItemByName(itemKey);
                if (itemBase == null) continue;

                int stackSize = itemData.getInt("stackSize");
                ItemStack itemStack = new ItemStack(itemBase);

                itemStack.setStackSize(stackSize);
                bagsInventory.put(itemKey, itemStack);
            }
        }
    }

    public JSONObject writeData(JSONObject playerData) {
        JSONObject armorData = new JSONObject();
        for (String slotKey : armorInventory.keySet()) {
            EnumInventorySlot slot = EnumInventorySlot.valueOf(slotKey);
            ItemBase itemBase = armorInventory.get(slotKey);
            armorData.put(slot.name(), (itemBase != null) ? itemBase.getItemName() : null);
        }
        updateZeroStacks();

        JSONObject bagsData = new JSONObject();
        for (String itemKey : bagsInventory.keySet()) {
            JSONObject itemData = new JSONObject();
            ItemStack itemStack = bagsInventory.get(itemKey);
            itemData.put("stackSize", itemStack.getStackSize());
            bagsData.put(itemKey, itemData);
        }

        inventoryData.put("armor", armorData);
        inventoryData.put("bags", bagsData);

       /* inventoryData.put("armor", armorInventory);
        inventoryData.put("bags", bagsInventory);*/
        playerData.put("inventory", inventoryData);
        return playerData;
    }

    public void addItemToInventory(ItemBase itemBase) {
        if (hasItem(itemBase)) {
            ItemStack itemInInventory = getItemInInventory(itemBase.getItemName().toLowerCase());
            itemInInventory.setStackSize(itemInInventory.getStackSize() + 1);
            return;
        }
        bagsInventory.put(itemBase.getItemName().toLowerCase(), new ItemStack(itemBase));
    }

    public ItemBase getEquippedItem(EnumInventorySlot slot) {
        if (!armorInventory.containsKey(slot.name())) return null;

        return armorInventory.get(slot.name());
    }

    public ItemStack getItemInInventory(String itemKey) {
        itemKey = itemKey.toLowerCase();
        if (!hasItem(itemKey)) return null;
        return bagsInventory.get(itemKey);
    }

    public boolean hasItem(ItemBase itemBase) {
        return hasItem(itemBase.getItemName());
    }

    public boolean hasItem(String itemKey) {
        return bagsInventory.containsKey(itemKey.toLowerCase());
    }

    public boolean canEquipItem(ItemBase itemBase) {
        ItemBase leftHandItem = getEquippedItem(EnumInventorySlot.LEFTHAND);
        ItemBase rightHandItem = getEquippedItem(EnumInventorySlot.RIGHTHAND);

        if (itemBase.getItemType().name().equalsIgnoreCase(EnumItemType.DEFAULT.name())) return true;

        if (itemBase.getItemType().name().equals(EnumItemType.BOW.name()) ||
                itemBase.getItemType().name().equals(EnumItemType.TWOHAND.name()) ||
                itemBase.getItemType().name().equals(EnumItemType.STAFF.name())) {
            if (leftHandItem != null) {
                return false;
            }
            if (rightHandItem != null) {
                if (rightHandItem.getItemType().name().equals(EnumItemType.ONEHAND.name()) ||
                        rightHandItem.getItemType().name().equals(EnumItemType.DAGGER.name())) {
                    return false;
                }
            }
        } else {
            return rightHandItem == null;
        }

        return true;
    }

    public boolean equipItem(ItemBase itemBase, EnumInventorySlot slotEquip) {
        EnumInventorySlot slot = itemBase.getItemSlot();

        if (slot.name().equals(EnumInventorySlot.BOTHHAND.name())) {
            ItemBase itemInSlot = getEquippedItem(slotEquip);

            if (itemInSlot != null) {
                addItemToInventory(itemInSlot);
                itemInSlot.removeCharacteristicsFromPlayer(rpgPlayer);
            }
            armorInventory.put(slotEquip.name(), itemBase);
            consumeItem(itemBase.getItemName(), 1);
            return true;
        }

       /* if (slot.name().equals(EnumInventorySlot.BOTHHAND.name())) {
            ItemBase itemInRightHand = getEquippedItem(EnumInventorySlot.RIGHTHAND);
            ItemBase itemInLeftHand = getEquippedItem(EnumInventorySlot.LEFTHAND);

            if (itemInRightHand != null) {
                if (itemInRightHand.getItemType().name().equals(EnumItemType.BOW.name()) ||
                        itemInRightHand.getItemType().name().equals(EnumItemType.TWOHAND.name()) ||
                        itemInRightHand.getItemType().name().equals(EnumItemType.STAFF.name())) {
                    return false;
                } else if (itemInLeftHand != null) {
                    armorInventory.put(EnumInventorySlot.LEFTHAND.name(), itemBase);
                    consumeItem(itemBase.getItemName(), 1);
                    return true;
                }
            } else {
                armorInventory.put(EnumInventorySlot.RIGHTHAND.name(), itemBase);
                consumeItem(itemBase.getItemName(), 1);
                return true;
            }
        }*/

        if (!slot.name().equals(EnumInventorySlot.DEFAULT.name())) {
            ItemBase itemInSlot = getEquippedItem(slot);

            if (itemInSlot != null) {
                addItemToInventory(itemInSlot);
            }
            armorInventory.put(slot.name(), itemBase);
            consumeItem(itemBase.getItemName(), 1);
            return true;
        }
        return false;
    }

    private void consumeItem(String itemKey, int amount) {
        ItemStack itemStack = bagsInventory.get(itemKey.toLowerCase());
        itemStack.setStackSize(itemStack.getStackSize() - amount);

        updateZeroStacks();
    }

    public ItemBase unequipItem(EnumInventorySlot slot) {
        ItemBase itemBase = getEquippedItem(slot);

        if (itemBase != null) {
            armorInventory.put(slot.name(), null);
            addItemToInventory(itemBase);
            return itemBase;
        }
        return null;
    }

    public void updateZeroStacks() {
        Set<String> keysToRemove = new HashSet<>();
        for (String itemKey : bagsInventory.keySet()) {
            ItemStack itemStack = bagsInventory.get(itemKey);
            if (itemStack.getStackSize() <= 0) {
                keysToRemove.add(itemKey);
                //bagsInventory.remove(itemKey);
            }
        }
        bagsInventory.keySet().removeAll(keysToRemove);
    }

    public EmbedBuilder getInventoryEmbed() {
        Member member = rpgPlayer.getGuild().getMemberById(rpgPlayer.getUserID());
        EmbedBuilder embedBuilder = EmbedHelper.generateDefaultEmbed();
        embedBuilder.setAuthor(rpgPlayer.getName(), "http://twitch.com/aymandev", member.getUser().getAvatarUrl());
        embedBuilder.setThumbnail(member.getUser().getAvatarUrl());
        embedBuilder.addField("Экипировка:", "", false);

        for (String slotKey : armorInventory.keySet()) {
            EnumInventorySlot slot = EnumInventorySlot.valueOf(slotKey);
            ItemBase itemInSlot = armorInventory.get(slotKey);
            String itemName = (itemInSlot != null) ? itemInSlot.getItemName() : "пусто";

            embedBuilder.addField(slot.slotName, itemName, false);
        }
        embedBuilder.addBlankField(false);
        embedBuilder.addField("Сумки:", "", false);

        for (String itemKey : bagsInventory.keySet()) {
            ItemStack itemInSlot = bagsInventory.get(itemKey);
            if (itemInSlot == null) continue;

            embedBuilder.addField(itemInSlot.getItemBase().getItemName() + " x" + itemInSlot.getStackSize(),
                    itemInSlot.getItemBase().getItemShortDescription(), true);
        }
        return embedBuilder;
    }
}
