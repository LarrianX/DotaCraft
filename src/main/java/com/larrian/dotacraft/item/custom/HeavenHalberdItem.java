package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.ManaComponent;
import com.larrian.dotacraft.item.Weapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.larrian.dotacraft.init.ModComponents.MANA_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.DISARM_EFFECT;
import static com.larrian.dotacraft.init.ModEffects.FLASK_REGENERATION_HEALTH;

public class HeavenHalberdItem extends Weapon implements Custom {
    private static final String ID = "heaven_halberd";
    private static final int DAMAGE = 40;
    private static final int COOLDOWN = 360;
    private static final int DURATION = 100;
    private static final int MINUS_MANA = 25;

    public HeavenHalberdItem() {
        super(DAMAGE);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Entity targetedEntity = DotaCraft.getTargetedEntity(world, user, 5.0D);

        ManaComponent manaComponent = user.getComponent(MANA_COMPONENT);
        if (
                targetedEntity instanceof PlayerEntity playerTarget && !user.isTeammate(playerTarget) &&
                (manaComponent.get() >= MINUS_MANA || user.isCreative())) {
            if (!user.isCreative()) {
                manaComponent.add(-MINUS_MANA);
                user.getItemCooldownManager().set(this, COOLDOWN);
            }

            applyEffects(playerTarget);

            return TypedActionResult.success(stack);
        } else {
            return TypedActionResult.fail(stack);
        }
    }

    private void applyEffects(PlayerEntity player) {
        player.setStatusEffect(new StatusEffectInstance(DISARM_EFFECT, DURATION, 0), null);
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

