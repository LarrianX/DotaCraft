package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Map;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public class MangoItem extends DotaItem {
    private static final String ID = "mango";
    private static final int REGENERATION = 100;
    private static final double REGENERATION_HEALTH = 0.4;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.REGENERATION_HEALTH, REGENERATION_HEALTH
    );

    public MangoItem() {
        super(new FabricItemSettings().maxCount(64), MODIFIERS, ID);
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
        HeroComponent component = user.getComponent(HERO_COMPONENT);

        if (component.isHero()) {
            component.addMana(REGENERATION);
            component.sync();
        } else {
            HungerManager hunger = user.getHungerManager();
            hunger.setFoodLevel(hunger.getFoodLevel() + 6);
        }
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
    }
}