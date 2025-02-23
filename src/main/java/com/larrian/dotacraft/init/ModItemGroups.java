package com.larrian.dotacraft.init;

import com.larrian.dotacraft.DotaCraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class ModItemGroups {

    // Список зарегистрированных групп предметов
    public static final List<ItemGroup> GROUPS = new ArrayList<>();

    public static final ItemGroup DOTA_GROUP = register(
            Registry.register(Registries.ITEM_GROUP,
                    new Identifier(DotaCraft.MOD_ID, "dota"),
                    FabricItemGroup.builder()
                            .displayName(Text.translatable("itemgroup.dota"))
                            .icon(ModItems.BOTTLE::getDefaultStack)
                            .entries((displayContext, entries) -> {
                                for (Item item : ModItems.ITEMS) {
                                    ItemStack stack = item.getDefaultStack();
                                    if (stack != null) {
                                        entries.add(stack);
                                    }
                                }
                                for (Block block : ModBlocks.BLOCKS) {
                                    entries.add(block);
                                }
                            }).build())
    );

    private static <T extends ItemGroup> T register(T group) {
        GROUPS.add(group);
        return group;
    }

    public static void registerItemGroups() {
        DotaCraft.LOGGER.info("Registering Item Groups for " + DotaCraft.MOD_ID);
        // Регистрация выполнена при инициализации статических полей
    }
}
