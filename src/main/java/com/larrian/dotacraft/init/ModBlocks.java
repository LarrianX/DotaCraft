package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.block.PortalBlock;
import com.larrian.dotacraft.block.PressurePlateBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlocks {
    public static final PortalBlock PORTAL = new PortalBlock();
    public static final PressurePlateBlock PRESSURE_PLATE = new PressurePlateBlock();

    public static final Block[] BLOCKS = {
            PORTAL,
            PRESSURE_PLATE
    };

    private static void registerBlocks() {
        for (Block block : BLOCKS) {
            if (block instanceof Custom) {
                registerBlock(((Custom) block).getId(), block);
            }
        }
    }

    private static void registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        Registry.register(Registries.BLOCK, new Identifier(DotaCraft.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        DotaCraft.LOGGER.info("Registering Mod Blocks for " + DotaCraft.MOD_ID);
        registerBlocks();
    }
}