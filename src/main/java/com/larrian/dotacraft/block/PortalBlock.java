package com.larrian.dotacraft.block;

import com.larrian.dotacraft.Custom;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;

public class PortalBlock extends Block implements Custom {
    private static final String ID = "portal";

    public PortalBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK));
    }

    @Override
    public String getCustomId() {
        return ID;
    }
}
