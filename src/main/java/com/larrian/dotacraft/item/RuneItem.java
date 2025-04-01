package com.larrian.dotacraft.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.rune.DotaRune;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public abstract class RuneItem extends Item implements Custom {
    public RuneItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    public abstract DotaRune getRune();

    @Override
    public String getId() {
        return "rune_of_" + getRune().getId();
    }
}
