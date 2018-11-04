package jp.aymandev.astolfo.helpers;

import jp.aymandev.astolfo.rpg.items.EnumItemType;
import jp.aymandev.astolfo.rpg.items.ItemBase;
import jp.aymandev.astolfo.rpg.pets.RPGPet;
import jp.aymandev.astolfo.rpg.pets.talents.PetTalent;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;
import jp.aymandev.astolfo.rpg.player.EnumInventorySlot;
import jp.aymandev.astolfo.rpg.player.RPGPlayer;
import jp.aymandev.astolfo.rpg.player.talents.Talent;

import java.util.Collection;

/**
 * Created by AymanDev on 28.07.2018.
 */
public class RPGHelper {

    public static int calculatePowerPoints(RPGPlayer rpgPlayer) {
        int powerPoints = 0;
        int melee = 0;
        int range = 0;
        int magic = 0;
        int strength = rpgPlayer.getCharacteristic(EnumCharacteristic.STRENGTH);
        int intelligence = rpgPlayer.getCharacteristic(EnumCharacteristic.INTELLIGENCE);
        int agility = rpgPlayer.getCharacteristic(EnumCharacteristic.AGILITY);
        ItemBase leftHand = rpgPlayer.getInventory().getEquippedItem(EnumInventorySlot.LEFTHAND);
        ItemBase rightHand = rpgPlayer.getInventory().getEquippedItem(EnumInventorySlot.RIGHTHAND);
        boolean isOneHanded = false;
        int weaponAmount = 0;

        if (leftHand != null) {
            isOneHanded = leftHand.getItemType().name().equals(EnumItemType.ONEHAND.name()) ||
                    leftHand.getItemType().name().equals(EnumItemType.DAGGER.name());
            weaponAmount++;

            if (leftHand.getItemType().name().equals(EnumItemType.DAGGER.name())) {
                strength /= 2;
            }
        }
        if (rightHand != null) {
            isOneHanded = rightHand.getItemType().name().equals(EnumItemType.ONEHAND.name()) ||
                    rightHand.getItemType().name().equals(EnumItemType.DAGGER.name());
            weaponAmount++;

            if (rightHand.getItemType().name().equals(EnumItemType.DAGGER.name())) {
                strength /= 2;
            }
            if (rightHand.getItemType().name().equals(EnumItemType.TWOHAND.name())) {
                agility /= 2;
            }
        }

        melee = (int)
                (strength * (isOneHanded ? 0.5f : 1f) *
                        weaponAmount + agility * (isOneHanded ? 0.5f : 1.0f) *
                        weaponAmount) * 2;

        if (rightHand != null) {
            if (rightHand.getItemType().name().equals(EnumItemType.BOW.name())) {
                range = (agility) * 2;
                melee *= 0.25;
            }
        }
        if (rightHand != null) {
            if (rightHand.getItemType().name().equals(EnumItemType.STAFF.name())) {
                magic = (intelligence) * 2;
                melee *= 0.25;
            }
        }

        rpgPlayer.setCharacteristic(EnumCharacteristic.MAGIC, magic);
        rpgPlayer.setCharacteristic(EnumCharacteristic.RANGE, range);
        rpgPlayer.setCharacteristic(EnumCharacteristic.MELEE, melee);

        powerPoints = melee + range + magic;
        powerPoints += rpgPlayer.getCharacteristic(EnumCharacteristic.ENDURANCE);

        if (powerPoints == 0) {
            powerPoints = 1;
        }

        return powerPoints;
    }


    public static int calculateTalentPowerPoints(RPGPlayer rpgPlayer) {
        int powerPoints = 0;
        for (Talent talent : rpgPlayer.getTalents().getLearnedTalents()) {
            powerPoints += talent.calculateTalentPower(rpgPlayer);
        }
        return powerPoints;
    }

    public static int calculatePetPower(RPGPlayer rpgPlayer) {
        if (rpgPlayer.hasPet()) {
            return rpgPlayer.getPetData().getPetPower() + rpgPlayer.getPetData().getTalentsPower();
        }
        return 0;
    }

    public static int calculatePetTalentsPower(RPGPet rpgPet) {
        Collection<PetTalent> talents = rpgPet.getLearnedTalents();
        int power = 0;

        for (PetTalent petTalent : talents) {
            power += petTalent.onTalentPowerCalculate(rpgPet);
        }
        return power;
    }
}
