package com.dota2.item;

import com.dota2.DotaCraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    private static final CustomItemWrapper<?>[] ITEMS = {
            new CustomItemWrapper<>(new Flask()),
            new CustomItemWrapper<>(new Clarity()),
            new CustomItemWrapper<>(new Mango()),
            new CustomItemWrapper<>(new Daedalus()),
            new CustomItemWrapper<>(new Scepter()),
            new CustomItemWrapper<>(new BattleFury()),
    };
    private static final CustomPredicateItemWrapper<?>[] PREDICATE_ITEMS = {
            new CustomPredicateItemWrapper<>(new Bottle()),
            new CustomPredicateItemWrapper<>(new Tango())
    };

    private static void addItemsToTabItemGroup(FabricItemGroupEntries entries) {
        for (CustomItemWrapper<?> wrapper : ITEMS) {
            entries.add(wrapper.getItem().getForTabItemGroup());
        }
        for (CustomItemWrapper<?> wrapper : PREDICATE_ITEMS) {
            entries.add(wrapper.getItem().getForTabItemGroup());
        }
    }

    private static void registerItems(CustomItemWrapper<?>[] items) {
        for (CustomItemWrapper<?> wrapper : items) {
            registerItem(wrapper.getItem().getId(), wrapper.getItem());
        }
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, name), item);
    }

    public static void registerModItems() {
        DotaCraft.LOGGER.info("Registering Mod Items for " + DotaCraft.MOD_ID);
        registerItems(ITEMS);
        registerItems(PREDICATE_ITEMS);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToTabItemGroup);
    }

    public static CustomItemWrapper<?>[] getItems() {
        return ITEMS;
    }

    public static CustomPredicateItemWrapper<?>[] getPredicateItems() {
        return PREDICATE_ITEMS;
    }
}