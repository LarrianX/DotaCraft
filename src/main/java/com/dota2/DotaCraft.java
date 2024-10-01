package com.dota2;

import com.dota2.block.ModBlocks;
import com.dota2.command.ModCommands;
import com.dota2.component.HeroComponent.HeroComponent;
import com.dota2.component.HeroComponent.SyncedHeroComponent;
import com.dota2.component.HeroComponent.ValuesComponent;
import com.dota2.effect.ModEffects;
import com.dota2.item.ModItemGroups;
import com.dota2.item.ModItems;
import com.dota2.item.Weapon;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static com.dota2.component.ModComponents.HERO_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public class DotaCraft implements ModInitializer {
    public static final String MOD_ID = "dotacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Random random = new Random();

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ModEffects.registerModEffects();
        ModCommands.registerModCommands();
        AttackEntityCallback.EVENT.register(DotaCraft::onAttackEntity);
    }

    private static int calculateDamage(PlayerEntity provider) {
        int totalDamage = 0;

        for (int i = 0; i < provider.getInventory().size(); i++) {
            ItemStack stack = provider.getInventory().getStack(i);

            if (stack.getItem() instanceof Weapon weapon) {
                totalDamage += weapon.getDamage();
            }
        }

        return totalDamage;
    }

    public static ActionResult onAttackEntity(PlayerEntity playerSource, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        int damage = calculateDamage(playerSource);

        HeroComponent heroComponentSource = playerSource.getComponent(HERO_COMPONENT);
        if (entity instanceof PlayerEntity playerTarget) {
            HeroComponent heroComponentTarget = playerTarget.getComponent(HERO_COMPONENT);
            if (heroComponentTarget.isHero() && heroComponentSource.isHero()) {
                // Уменьшение хп
                ValuesComponent valuesComponentSource = playerSource.getComponent(VALUES_COMPONENT);
                ValuesComponent valuesComponentTarget = playerTarget.getComponent(VALUES_COMPONENT);
                double randomValue = damage + (damage * ((valuesComponentSource.getCrit() / 100) * random.nextDouble()));
                valuesComponentTarget.addHealth(-randomValue);
                valuesComponentTarget.sync();
                // Убирание эффекта невидимости если есть
                playerTarget.removeStatusEffect(StatusEffects.INVISIBILITY);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }
}