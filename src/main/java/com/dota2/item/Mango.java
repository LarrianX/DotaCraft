package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Mango extends Item implements CustomItem {
    private static final String ID = "mango";

    public Mango() {
        super(new FabricItemSettings()
                .maxCount(8)
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Воспроизводим нужные действия
        applyEffects(user);
        // Получаем стак, и уменьшаем его на один
        ItemStack stack = user.getStackInHand(hand);
        // Если человек не в креативе - уменьшаем стак на один
        if (!user.isCreative()) {
            stack.decrement(1);
        }
        // Успех
        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity user) {
        // Воспроизводим звуки и эффекты
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.0F);
        HungerManager hunger = (user.getHungerManager());
        hunger.setFoodLevel(hunger.getFoodLevel() + 6);
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
