package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class TpScrollItem extends Item implements Custom {
    private static final String ID = "tp_scroll";

    public TpScrollItem() {
        super(new FabricItemSettings().maxCount(64));
    }

    @Override
    public String getId() {
        return ID;
    }
}
