package com.dota2.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;

public class Portal extends Block implements CustomBlock{
    private static final String ID = "portal";

    public Portal() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK));
    }

    @Override
    public String getId() {
        return ID;
    }
}
