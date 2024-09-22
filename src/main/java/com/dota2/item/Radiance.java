package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Radiance extends Item implements CustomItem {
    public static final String ID = "radiance";

    public Radiance() {super(new FabricItemSettings().maxCount(1));}

    @Override
    public String getId(){return ID;}

    @Override
    public ItemStack getForTabItemGroup(){return new ItemStack(this);}
    }

