package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class RecDaedalusItem extends Item implements Custom {
    private static final String ID = "rec_daedalus";

    public RecDaedalusItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }
}