package com.larrian.dotacraft.init;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.item.CustomItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup DOTA_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DotaCraft.MOD_ID, "dota"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.dota"))
                    .icon(ModItems.BOTTLE::getForTabItemGroup).entries(((displayContext, entries) -> {
                        for (Item item : ModItems.ITEMS) {
                            if (item instanceof CustomItem customItem) {
                                ItemStack entry = customItem.getForTabItemGroup();
                                if (entry != null) {
                                    entries.add(entry);
                                }
                            }
                        }
                        for (Block block : ModBlocks.BLOCKS) {
                            entries.add(block);
                        }
                    })).build());

    public static void registerItemGroups() {
        DotaCraft.LOGGER.info("Registering Item Groups for " + DotaCraft.MOD_ID);
    }
}
