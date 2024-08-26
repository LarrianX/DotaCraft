package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TpScroll extends Item implements CustomItem {
    private static final String ID = "tpscroll";

    public TpScroll() {
        super(new FabricItemSettings().maxCount(64));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        return new ItemStack(this);
    }
}
