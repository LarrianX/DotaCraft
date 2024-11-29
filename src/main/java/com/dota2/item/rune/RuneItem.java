package com.dota2.item.rune;

import com.dota2.item.CustomItem;
import com.dota2.rune.Rune;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class RuneItem extends Item implements CustomItem {
    public RuneItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    public abstract Rune getRune();

    @Override
    public String getId() {
        return "rune_of_" + getRune().getId();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
//        stack.decrement(1);
    }
}
