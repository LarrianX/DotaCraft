package com.larrian.dotacraft.item;

import com.larrian.dotacraft.DotaCraft;

import com.larrian.dotacraft.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.larrian.dotacraft.ModEffects.FLASK_REGENERATION_HEALTH;

public class FlaskItem extends DotaItem {
    private static final String ID = "flask";

    public FlaskItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Entity targetedEntity = DotaCraft.getTargetedEntity(world, user, 5.0D);

        if (targetedEntity instanceof PlayerEntity playerTarget && user.isTeammate(playerTarget)) {
            applyEffects(playerTarget, 0.5);
        } else {
            applyEffects(user, 1.0);
        }

        if (!user.isCreative()) {
            stack.decrement(1);
        }

        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity player, double multiplier) {
        player.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
        player.setStatusEffect(new StatusEffectInstance(FLASK_REGENERATION_HEALTH, (int) (260 * multiplier), 0), null);
    }
}
