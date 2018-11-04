package jp.aymandev.astolfo.rpg.items;

/**
 * Created by AymanDev on 29.07.2018.
 */
public class ItemStack {
    private ItemBase itemBase;
    private int stackSize = 0;

    public ItemStack(ItemBase itemBase) {
        this.itemBase = itemBase;
        stackSize = 1;
    }

    public ItemBase getItemBase() {
        return itemBase;
    }

    public int getStackSize() {
        return stackSize;
    }

    public ItemStack setStackSize(int stackSize) {
        this.stackSize = stackSize;
        return this;
    }
}
