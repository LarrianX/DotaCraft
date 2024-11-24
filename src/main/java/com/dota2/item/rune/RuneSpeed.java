package com.dota2.item.rune;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class RuneSpeed extends RuneItem {
    private static final String ID = "rune_of_speed";
    private static final String RUNE = "SPEED";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getType() {
        return RUNE;
    }
}
