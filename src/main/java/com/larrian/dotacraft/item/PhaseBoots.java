package com.larrian.dotacraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class PhaseBoots extends Item implements CustomItem {
    private static final String ID = "phase_boots";

    public PhaseBoots() {super(new FabricItemSettings().maxCount(64));}

    @Override
    public String getId() {
        return ID;
    }
}