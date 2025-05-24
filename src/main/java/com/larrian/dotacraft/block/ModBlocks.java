package com.larrian.dotacraft.block;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.block.custom.PortalBlock;
import com.larrian.dotacraft.block.custom.PlateBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final PortalBlock PORTAL = register(new PortalBlock());
    public static final PlateBlock PRESSURE_PLATE = register(new PlateBlock());

    private static <T extends Block & Custom> T register(T block) {
        String id = block.getCustomId();
        registerBlockItem(id, block);
        BLOCKS.add(block);
        return Registry.register(Registries.BLOCK, new Identifier(DotaCraft.MOD_ID, id), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {}
}
