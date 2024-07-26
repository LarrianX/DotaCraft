package com.dota2.item;

import com.dota2.DotaCraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModItems {
    public static CustomItemWrapper<?>[] items = {
            new CustomItemWrapper<>(new Flask()),
            new CustomItemWrapper<>(new Clarity()),
            new CustomItemWrapper<>(new Mango()),
            new CustomItemWrapper<>(new Bottle()),
            new CustomItemWrapper<>(new Daedalus())
    };

    public static void addItemsToTabItemGroup(FabricItemGroupEntries entries) {
        for (CustomItemWrapper<?> wrapper : items) {
            entries.add(wrapper.item().getForTabItemGroup());
        }
    }

    public static void registerItems() {
        for (CustomItemWrapper<?> wrapper : items) {
            registerItem(wrapper.item().getId(), wrapper.item());
        }
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, name), item);
    }

    public static void registerModItems() {
        DotaCraft.LOGGER.info("Registering Mod Items for " + DotaCraft.MOD_ID);
        registerItems();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToTabItemGroup);
    }
}