package com.dota2.block;

import com.dota2.DotaCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlocks {
    public static final Portal PORTAL = new Portal();

    public static final CustomBlockWrapper<?>[] BLOCKS = {
            new CustomBlockWrapper<>(PORTAL),
    };

    private static void registerBlocks() {
        for (CustomBlockWrapper<?> wrapper : BLOCKS) {
            registerBlock(wrapper.getBlock().getId(), wrapper.getBlock());
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