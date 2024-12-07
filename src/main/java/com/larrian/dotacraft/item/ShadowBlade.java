package com.larrian.dotacraft.item;

import com.larrian.dotacraft.component.hero.ValuesComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.larrian.dotacraft.component.ModComponents.VALUES_COMPONENT;


public class ShadowBlade extends Weapon implements CustomItem {
    private static final String ID = "shadow_blade";
    private static final int DAMAGE = 25;
    private static final int MINUS_MANA = 75;

    public ShadowBlade() {
        super(DAMAGE);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        ValuesComponent valuesComponent = user.getComponent(VALUES_COMPONENT);
        if (valuesComponent.getMana() >= MINUS_MANA || user.isCreative()) {
            if (!user.isCreative()) {
                valuesComponent.addMana(-MINUS_MANA);
                user.getItemCooldownManager().set(this, 500);
            }
            // Применяем эффекты
            applyEffects(user);
            return TypedActionResult.success(stack);
        } else {
            return TypedActionResult.fail(stack);
        }
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
