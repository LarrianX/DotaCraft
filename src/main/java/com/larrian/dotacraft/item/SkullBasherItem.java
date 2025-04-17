package com.larrian.dotacraft.item;

import com.larrian.dotacraft.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class SkullBasherItem extends DotaItem {
    private static final String ID = "skull_basher";

    public SkullBasherItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

}
