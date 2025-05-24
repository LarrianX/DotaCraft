package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class TpScrollItem extends DotaItem {
    private static final String ID = "tp_scroll";

    public TpScrollItem() {
        super(new FabricItemSettings().maxCount(64), ID);
    }
}
