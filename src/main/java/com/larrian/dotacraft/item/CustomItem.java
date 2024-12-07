package com.larrian.dotacraft.item;

import com.larrian.dotacraft.Custom;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface CustomItem extends Custom {
    double ERROR = 0.00000000001;

    default ItemStack getForTabItemGroup() {
        if (this instanceof Item item) {
            return new ItemStack(item);
        } else {
            return null;
        }
    }
}