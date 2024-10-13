package com.dota2.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import com.dota2.Custom;

public class PressurePlate extends PressurePlateBlock implements Custom {
    private static final String ID = "pressure_plate";

    public PressurePlate() {
        super(
                ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE),
                BlockSetType.GOLD
        );
    }

//    public PressurePlate() {
//        super(FabricBlockSettings.copyOf(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).sounds(BlockSoundGroup.AMETHYST_BLOCK));
//    }


    @Override
    public String getId() {
        return ID;
    }
}
