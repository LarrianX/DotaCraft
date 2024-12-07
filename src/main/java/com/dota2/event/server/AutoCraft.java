package com.dota2.event.server;

import com.dota2.DotaCraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class AutoCraft {
    public static ItemStack itemInInventory(PlayerInventory inventory, Item item, Set<Integer> blockedSlots) {
        for (int i = 0; i < inventory.main.size(); i++) {
            if (blockedSlots.contains(i)) {
                continue;
            }
            ItemStack stack = inventory.getStack(i);
            if (stack.getItem() == item && stack.getCount() > 0) {
                return stack;
            }
        }
        return null;
    }

    public static boolean craft(PlayerEntity player, Set<Integer> blockedSlots) {
        boolean canCraft = true;
        PlayerInventory inventory = player.getInventory();
        for (Item[] recipe : DotaCraft.RECIPES.keySet()) {
            for (Item recipeItem : recipe) {
                if (itemInInventory(inventory, recipeItem, blockedSlots) == null) {
                    canCraft = false;
                    break;
                }
            }
            if (canCraft) {
                for (Item recipeItem : recipe) {
                    inventory.removeOne(itemInInventory(inventory, recipeItem, blockedSlots));
                }
                Item resultItem = DotaCraft.RECIPES.get(recipe);
                inventory.insertStack(resultItem.getDefaultStack());
                player.getItemCooldownManager().set(resultItem, 10);
            }
        }
        return canCraft;
    }
}
