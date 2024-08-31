package com.dota2.item;

import com.dota2.DotaCraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.dota2.block.ModBlocks.BLOCKS;
import static com.dota2.item.ModItems.ITEMS;


public class ModItemGroups {
    public static final ItemGroup DOTA_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DotaCraft.MOD_ID, "dota"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.dota"))
                    .icon(() -> new ItemStack(ModItems.BOTTLE)).entries(((displayContext, entries) -> {
                        for (Item item : ITEMS) {
                            if (item instanceof CustomItem) {
                                entries.add(((CustomItem) item).getForTabItemGroup());
                            }
                        }
                        for (Block block : BLOCKS) {
                            entries.add(block);
                        }
                    })).build());

    public static void registerItemGroups() {
        DotaCraft.LOGGER.info("Registering Item Groups for " + DotaCraft.MOD_ID);
    }
}
