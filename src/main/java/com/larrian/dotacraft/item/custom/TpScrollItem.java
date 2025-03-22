package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class TpScrollItem extends DotaItem implements Custom {
    private static final String ID = "tp_scroll";

    public TpScrollItem() {
        super(new FabricItemSettings().maxCount(64));
    }

    @Override
    public String getId() {
        return ID;
    }
}
