package com.dota2.item;

import com.dota2.DotaCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // Даже несмотря на то, что правильно называть Healing Salve, его id - flask
    public static final Item FLASK = registerItem("flask", new Item(new FabricItemSettings().maxCount(8)));
    public static final Item CLARITY = registerItem("clarity", new Item(new FabricItemSettings().maxCount(8)));
    public static final Item MANGO = registerItem("mango", new Item(new FabricItemSettings().maxCount(8)));
    public static final Item BOTTLE = registerItem("bottle", new Bottle(new FabricItemSettings().maxCount(1)));
    public static final Item DAEDALUS = registerItem("daedalus", new Item(new FabricItemSettings().maxCount(1)));

    public static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(FLASK);
        entries.add(CLARITY);
        entries.add(MANGO);
        entries.add(DAEDALUS);

        ItemStack fullBottleStack = new ItemStack(BOTTLE);
        Bottle.setFullness(fullBottleStack, Bottle.MAX_FULLNESS);
        entries.add(fullBottleStack);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, name), item);
    }

    public static void registerModItems() {
        DotaCraft.LOGGER.info("Registering Mod Items for " + DotaCraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
