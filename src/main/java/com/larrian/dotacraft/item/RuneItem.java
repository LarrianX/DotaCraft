package com.larrian.dotacraft.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.rune.Rune;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class RuneItem extends Item implements Custom {
    public RuneItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    public abstract Rune getRune();

    @Override
    public String getId() {
        return "rune_of_" + getRune().getId();
    }
}
