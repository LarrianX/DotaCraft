package com.dota2.item.rune;

import com.dota2.item.CustomItem;
import com.dota2.rune.Rune;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public abstract class RuneItem extends Item implements CustomItem {
    public RuneItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    public abstract Rune getRune();

    @Override
    public String getId() {
        return "rune_of_" + getRune().getId();
    }
}
