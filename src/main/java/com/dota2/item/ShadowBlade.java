package com.dota2.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class ShadowBlade extends Weapon implements CustomItem {
    private static final String ID = "shadowblade";
    private static final int DAMAGE = 25;

    public ShadowBlade() {
        super(DAMAGE);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Применяем эффекты
        applyEffects(user);

        // Устанавливаем кулдаун на 25 секунд для выживача (500 тиков)
        if (!user.isCreative()) {
            user.getItemCooldownManager().set(this, 500);
        }

        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);

        // Создаем эффект невидимости без частиц
        StatusEffectInstance invisibilityEffect = new StatusEffectInstance(
                StatusEffects.INVISIBILITY, 340, 0, false, false
        );

        user.addStatusEffect(invisibilityEffect);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }
}
