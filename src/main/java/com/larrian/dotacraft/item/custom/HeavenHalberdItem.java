package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Map;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.effect.ModEffects.DISARM;

public class HeavenHalberdItem extends DotaItem {
    private static final String ID = "heaven_halberd";
    // ability
    private static final int COOLDOWN = 360;
    private static final int DURATION = 100;
    private static final int MINUS_MANA = 25;
    // attributes
    private static final double MAX_HEALTH = 275;
    private static final double REGENERATION_HEALTH = 6;
    private static final double ALLS = 5;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.MAX_HEALTH, MAX_HEALTH,
            ModAttributes.REGENERATION_HEALTH, REGENERATION_HEALTH,
            ModAttributes.STRENGTH, ALLS,
            ModAttributes.AGILITY, ALLS,
            ModAttributes.INTELLIGENCE, ALLS
    );

    public HeavenHalberdItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Entity targetedEntity = DotaCraft.getTargetedEntity(world, user, 5.0D);

        HeroComponent component = user.getComponent(HERO_COMPONENT);
        if (
                targetedEntity instanceof PlayerEntity playerTarget && !user.isTeammate(playerTarget) &&
                        (component.getMana() >= MINUS_MANA || user.isCreative())) {
            if (!user.isCreative()) {
                component.addMana(-MINUS_MANA);
                user.getItemCooldownManager().set(this, COOLDOWN);
            }

            applyEffects(playerTarget);

            return TypedActionResult.success(stack);
        } else {
            return TypedActionResult.fail(stack);
        }
    }

    private void applyEffects(PlayerEntity player) {
        player.setStatusEffect(new StatusEffectInstance(DISARM, DURATION, 0), null);
    }
}