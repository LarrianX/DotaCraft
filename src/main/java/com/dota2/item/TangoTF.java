package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TangoTF extends Item implements CustomItem {
    private static final String ID = "tango_tf";

    public TangoTF() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        return null;
    }
}
