package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Mango extends Item implements CustomItem {
    public static final String ID = "mango";

    public Mango() {
        super(new FabricItemSettings().maxCount(8).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F).build()));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        return new ItemStack(this);
    }

}
