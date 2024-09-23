package com.dota2.item;

import com.dota2.components.HeroAttributes;
import com.dota2.components.ModComponents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.dota2.components.ModComponents.HERO_ATTRIBUTES;

public class ShadowBlade extends SwordItem implements CustomItem {
    private static final String ID = "shadowblade";
    private static final int DAMAGE = 25;

    public ShadowBlade() {
        super(DotaMaterial.INSTANCE, DAMAGE, 2.0f , new FabricItemSettings().maxCount(1));
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
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof PlayerEntity player) {
            HeroAttributes component = player.getComponent(HERO_ATTRIBUTES);

            if (component.getHealth() >= DAMAGE)
                component.setHealth(component.getHealth() - DAMAGE);
            else
                component.setHealth(0);
        }
        return super.postHit(stack, target, attacker);
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
