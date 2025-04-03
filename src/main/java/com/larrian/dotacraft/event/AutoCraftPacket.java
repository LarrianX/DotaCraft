package com.larrian.dotacraft.event;

import com.larrian.dotacraft.DotaCraft;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

import java.util.HashSet;
import java.util.Set;

public class AutoCraftPacket {
    private final Set<Integer> blockedSlots;

    public AutoCraftPacket(Set<Integer> blockedSlots) {
        this.blockedSlots = blockedSlots;
    }

    public AutoCraftPacket(PacketByteBuf buf) {
        int count = buf.readByte();
        this.blockedSlots = new HashSet<>();
        for (int i = 0; i < count; i++) {
            blockedSlots.add((int) buf.readByte());
        }
    }

    public PacketByteBuf toPacketByteBuf() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeByte(blockedSlots.size());
        for (Integer slot : blockedSlots) {
            buf.writeByte(slot);
        }
        return buf;
    }

    public Set<Integer> getBlockedSlots() {
        return blockedSlots;
    }

    public void craft(PlayerEntity player) {
        craft(player, blockedSlots);
    }

    public static boolean craft(PlayerEntity player, Set<Integer> blockedSlots) {
        boolean canCraft = true;
        PlayerInventory inventory = player.getInventory();
        for (Item[] recipe : DotaCraft.RECIPES.keySet()) {
            for (Item recipeItem : recipe) {
                if (findItemInInventory(inventory, recipeItem, blockedSlots) == null) {
                    canCraft = false;
                    break;
                }
            }
            if (canCraft) {
                for (Item recipeItem : recipe) {
                    inventory.removeOne(findItemInInventory(inventory, recipeItem, blockedSlots));
                }
                Item resultItem = DotaCraft.RECIPES.get(recipe);
                inventory.insertStack(resultItem.getDefaultStack());
                player.getItemCooldownManager().set(resultItem, 10);
            }
        }
        return canCraft;
    }

    private static ItemStack findItemInInventory(PlayerInventory inventory, Item item, Set<Integer> blockedSlots) {
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
}
