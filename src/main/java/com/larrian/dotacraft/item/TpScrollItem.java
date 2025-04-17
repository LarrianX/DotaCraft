package com.larrian.dotacraft.item;

import com.larrian.dotacraft.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class TpScrollItem extends DotaItem {
    private static final String ID = "tp_scroll";

    public TpScrollItem() {
        super(new FabricItemSettings().maxCount(64), ID);
    }
}
