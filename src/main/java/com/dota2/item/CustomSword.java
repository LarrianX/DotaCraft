package com.dota2.item;

import com.dota2.component.HeroComponent.ValuesComponent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public abstract class CustomSword extends SwordItem {
    private static final float ATTACK_SPEED = 2.0f;

    public CustomSword(int damage) {
        super(DotaMaterial.INSTANCE, damage, ATTACK_SPEED, new FabricItemSettings().maxCount(1));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof PlayerEntity player) {
            ValuesComponent component = player.getComponent(VALUES_COMPONENT);

            component.addHealth(-getDamage());
        }
        return super.postHit(stack, target, attacker);
    }

    public abstract int getDamage();
}
