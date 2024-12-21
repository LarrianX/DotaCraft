package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.hero.HeroComponent;
import com.larrian.dotacraft.component.hero.ManaComponent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.MANA_COMPONENT;

public class MangoItem extends Item implements Custom {
    private static final String ID = "mango";
    private static final int REGENERATION = 100;

    public MangoItem() {
        super(new FabricItemSettings().maxCount(3)
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Получаем стак, который игрок держит в руке
        ItemStack stack = user.getStackInHand(hand);

        // Максимальная дистанция, на которую игрок может взаимодействовать с сущностями
        Entity targetedEntity = DotaCraft.getTargetedEntity(world, user, 5.0D);

        if (targetedEntity instanceof PlayerEntity playerTarget && user.isTeammate(playerTarget)) {
            // Применяем эффекты на игрока, на которого смотрят
            applyEffects(playerTarget);
        } else {
            // Если игрок ни на кого не смотрит, применяем эффекты на самого пользователя
            applyEffects(user);
        }

        // Если игрок не в креативе - уменьшаем стак на один
        if (!user.isCreative()) {
            user.getItemCooldownManager().set(this, 10);
            stack.decrement(1);
        }

        // Успех
        return TypedActionResult.success(stack);
    }


    private void applyEffects(PlayerEntity user) {
        // Воспроизводим звуки и эффекты
        HeroComponent heroComponent = user.getComponent(HERO_COMPONENT);

        if (heroComponent.isHero()) {
            ManaComponent manaComponent = user.getComponent(MANA_COMPONENT);
            manaComponent.addMana(REGENERATION);
            manaComponent.sync();
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
