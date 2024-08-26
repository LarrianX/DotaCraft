package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ShadowBlade extends Item implements CustomItem {
    private static final String ID = "shadowblade";

    public ShadowBlade() {
        super(new FabricItemSettings().maxCount(1));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Применяем эффекты
        applyEffects(user);

        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
        user.setStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 340, 0),null);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        return new ItemStack(this);
    }
}
