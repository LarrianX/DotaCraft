package com.larrian.dotacraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class Scepter extends Item implements CustomItem {
    private static final String ID = "scepter";

    public Scepter() {super(new FabricItemSettings().maxCount(64));}

    @Override
    public String getId() {
        return ID;
    }
}