package com.dota2.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface CustomItem {
    double ERROR = 0.00000000001;
    String getId();

    default ItemStack getForTabItemGroup() {
        if (this instanceof Item item) {
            return new ItemStack(item);
        } else {
            return null;
        }
    }
}