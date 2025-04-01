package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.attributes.IDotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.EnumMap;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class MangoItem extends DotaItem implements Custom {
    private static final String ID = "mango";
    private static final int REGENERATION = 100;

    private static final double REGENERATION_HEALTH = 0.4;

    public MangoItem() {
        super(new FabricItemSettings());
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
        // Воспроизводим звуки и эффекты
        HeroComponent component = user.getComponent(HERO_COMPONENT);

        if (component.isHero()) {
            component.addMana(REGENERATION);
            component.sync();
        } else {
            HungerManager hunger = (user.getHungerManager());
            hunger.setFoodLevel(hunger.getFoodLevel() + 6);
        }
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributes, IDotaAttribute> attributes, int slot, int count) {
        attributes.get(DotaAttributes.REGENERATION_HEALTH).addModifier(String.valueOf(slot), REGENERATION_HEALTH * count);
    }

    @Override
    public String getId() {
        return ID;
    }
}
