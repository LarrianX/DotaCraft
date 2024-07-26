package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Daedalus extends Item implements CustomItem {
    public static final String ID = "daedalus";

    public Daedalus() {
        super(new FabricItemSettings().maxCount(1));
    }

    public String getId() {
        return ID;
    }

    public ItemStack getForTabItemGroup() {
        return new ItemStack(this);
    }
}
