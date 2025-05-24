package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class RecDaedalusItem extends DotaItem {
    private static final String ID = "rec_daedalus";

    public RecDaedalusItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }
}
