package com.dota2.item;

import com.dota2.DotaCraft;
import com.dota2.block.CustomBlockWrapper;
import com.dota2.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
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
                        for (CustomItemWrapper<?> wrapper : ITEMS) {
                            entries.add(wrapper.getItem().getForTabItemGroup());
                        }
                        for (CustomBlockWrapper<?> wrapper: BLOCKS) {
                            entries.add(wrapper.getBlock());
                        }
                    })).build());

    public static void registerItemGroups() {
        DotaCraft.LOGGER.info("Registering Item Groups for " + DotaCraft.MOD_ID);
    }
}
