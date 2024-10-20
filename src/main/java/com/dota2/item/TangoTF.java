package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class TangoTF extends Item implements CustomItem {
    private static final String ID = "tango_tf";
    private static final String TIME_KEY = "time";
    private static final int TIME = 800;

    public TangoTF() {
        super(new FabricItemSettings().maxCount(1));
    }

    public long getTime(ItemStack stack) {
        return stack.getOrCreateNbt().getLong(TIME_KEY);
    }

    public void setTime(ItemStack stack, long time) {
        stack.getOrCreateNbt().putLong(TIME_KEY, time);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        NbtCompound nbt = stack.getOrCreateNbt();

        long worldTime = world.getTime();
        if (!nbt.contains(TIME_KEY)) {
            setTime(stack, worldTime);
        }
        long startTime = getTime(stack);

        int tickPassed = (int) (world.getTime() - startTime);
        int secondsLeft = (TIME / 20) - (tickPassed / 20);

        if (entity.isPlayer()) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.getStackInHand(player.getActiveHand()) == stack) {
                MinecraftClient mc = MinecraftClient.getInstance();
                mc.inGameHud.setOverlayMessage(Text.translatable("text.dotacraft.tango_timer", secondsLeft), false);
            }
        }

        // Проверяем, прошло ли 20 тиков (1 секунда)
        if (tickPassed > TIME) {
            stack.decrement(1);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Проверка, на что нацелился игрок (дерево)
        BlockHitResult hitResult = (BlockHitResult) user.raycast(5.0D, 0.0F, false);
        if (hitResult.getType() == BlockHitResult.Type.BLOCK && world.getBlockState(hitResult.getBlockPos()).getBlock() == Blocks.OAK_LOG) {

            Tango.removeTree(world, hitResult.getBlockPos());
            Tango.applyEffects(user);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        return null;
    }
}
