package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.DotaCraft;

import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.larrian.dotacraft.effect.ModEffects.CLARITY_REGENERATION_MANA;

public class ClarityItem extends DotaItem {
    private static final String ID = "clarity";

    public ClarityItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Entity targetedEntity = DotaCraft.getTargetedEntity(world, user, 5.0D);

        if (targetedEntity instanceof PlayerEntity playerTarget && user.isTeammate(playerTarget)) {
            applyEffects(playerTarget);
        } else {
            applyEffects(user);
        }

        if (!user.isCreative()) {
            stack.decrement(1);
        }

        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
        user.setStatusEffect(new StatusEffectInstance(CLARITY_REGENERATION_MANA, 500, 0), null);
    }
}
