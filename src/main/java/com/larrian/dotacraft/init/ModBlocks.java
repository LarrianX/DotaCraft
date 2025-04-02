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
import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final PortalBlock PORTAL = register(new PortalBlock());
    public static final PressurePlateBlock PRESSURE_PLATE = register(new PressurePlateBlock());

    private static <T extends Block & Custom> T register(T block) {
        String id = block.getId();
        registerBlockItem(id, block);
        Registry.register(Registries.BLOCK, new Identifier(DotaCraft.MOD_ID, id), block);
        BLOCKS.add(block);
        return block;
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {}
}
