package com.dota2;

import com.dota2.block.ModBlocks;
import com.dota2.command.ModCommands;
import com.dota2.effect.ModEffects;
import com.dota2.item.ModItemGroups;
import com.dota2.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DotaCraft implements ModInitializer {
    public static final String MOD_ID = "dotacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ModEffects.registerModEffects();
        ModCommands.registerModCommands();
    }
}