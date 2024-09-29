package com.dota2.item;

import com.dota2.components.HeroComponents.HeroComponent;
import com.dota2.components.HeroComponents.ValuesComponent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.dota2.components.ModComponents.HERO_COMPONENT;
import static com.dota2.components.ModComponents.VALUES_COMPONENT;

public class Mango extends Item implements CustomItem {
    private static final String ID = "mango";
    private static final int REGENERATION = 100;

    public Mango() {
        super(new FabricItemSettings().maxCount(3)
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Воспроизводим нужные действия
        applyEffects(user);
        // Получаем стак, и уменьшаем его на один
        ItemStack stack = user.getStackInHand(hand);
        // Если человек не в креативе - уменьшаем стак на один
        if (!user.isCreative()) {
            stack.decrement(1);
//            user.getItemCooldownManager().set(this, 10);
        }
        // Успех
        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity user) {
        // Воспроизводим звуки и эффекты
        HeroComponent heroComponent = user.getComponent(HERO_COMPONENT);

        if (heroComponent.isHero()) {
            ValuesComponent valuesComponent = user.getComponent(VALUES_COMPONENT);
            valuesComponent.addMana(REGENERATION);
        } else {
            HungerManager hunger = (user.getHungerManager());
            hunger.setFoodLevel(hunger.getFoodLevel() + 6);
        }
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
    }

    @Override
    public String getId() {
        return ID;
    }
}
