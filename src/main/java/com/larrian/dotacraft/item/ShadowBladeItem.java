package com.larrian.dotacraft.item;

import com.larrian.dotacraft.component.HeroComponent;

import com.larrian.dotacraft.dota.ModAttributes;
import com.larrian.dotacraft.DotaItem;
import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.dota.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Map;

import static com.larrian.dotacraft.ModComponents.HERO_COMPONENT;

public class ShadowBladeItem extends DotaItem {
    private static final String ID = "shadow_blade";
    private static final int MINUS_MANA = 75;

    private static final double DAMAGE = 25;
    private static final double ATTACK_SPEED = 35;

    public ShadowBladeItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        HeroComponent component = user.getComponent(HERO_COMPONENT);
        if (component.getMana() >= MINUS_MANA || user.isCreative()) {
            if (!user.isCreative()) {
                component.addMana(-MINUS_MANA);
                user.getItemCooldownManager().set(this, 500);
            }
            applyEffects(user);
            return TypedActionResult.success(stack);
        } else {
            return TypedActionResult.fail(stack);
        }
    }

    private void applyEffects(PlayerEntity player) {
        player.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
        StatusEffectInstance invisibilityEffect = new StatusEffectInstance(
                StatusEffects.INVISIBILITY, 340, 0, false, false
        );
        player.addStatusEffect(invisibilityEffect);
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
        attributes.get(ModAttributes.ATTACK_SPEED).addModifier(String.valueOf(slot), ATTACK_SPEED);
    }
}
