package com.dota2.item.rune;

import com.dota2.item.CustomItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public abstract class RuneItem extends Item implements CustomItem {
    public static final int RUNE_AGE = 200;

    public RuneItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    public abstract String getType();
}
