package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class ScepterItem extends Item implements Custom {
    private static final String ID = "scepter";

    public ScepterItem() {super(new FabricItemSettings().maxCount(64));}

    @Override
    public String getId() {
        return ID;
    }
}