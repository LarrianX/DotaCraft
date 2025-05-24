package com.larrian.dotacraft.item;

import com.larrian.dotacraft.rune.DotaRune;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public abstract class RuneItem extends DotaItem {
    private final DotaRune rune;

    public RuneItem(DotaRune rune) {
        super(new FabricItemSettings().maxCount(1), "rune_of_" + rune.getCustomId());
        this.rune = rune;
    }

    public DotaRune getRune() {
        return rune;
    }
}
