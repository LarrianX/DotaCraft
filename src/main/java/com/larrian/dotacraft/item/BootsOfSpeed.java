package com.larrian.dotacraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class BootsOfSpeed extends Item implements CustomItem {
    private static final String ID = "boots_of_speed";

    public BootsOfSpeed() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }
}
