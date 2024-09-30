package com.dota2.component.HeroComponent;

import com.dota2.component.ModComponents;
import com.dota2.item.Weapon;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.dota2.component.ModComponents.HERO_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, ServerTickingComponent, AutoSyncedComponent {
    public static final int HEALTH = 100;
    private final PlayerEntity provider;
    private boolean hero;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
        AttackEntityCallback.EVENT.register(this::onAttackEntity);
    }

    private int calculateDamage() {
        int totalDamage = 0;

        for (int i = 0; i < this.provider.getInventory().size(); i++) {
            ItemStack stack = this.provider.getInventory().getStack(i);

            if (stack.getItem() instanceof Weapon weapon) {
                totalDamage += weapon.getDamage();
            }
        }

        return totalDamage;
    }

    private ActionResult onAttackEntity(PlayerEntity playerEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        int damage = calculateDamage();
        provider.sendMessage(Text.literal("Урон: " + damage), false);

        if (this.hero && playerEntity.equals(provider) && entity instanceof PlayerEntity playerTarget) {
            HeroComponent heroComponent = playerTarget.getComponent(HERO_COMPONENT);
            if (heroComponent.isHero()) {
                ValuesComponent valuesComponent = playerTarget.getComponent(VALUES_COMPONENT);
                valuesComponent.addHealth(-50);
                valuesComponent.sync();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    public void sync() {
        HERO_COMPONENT.sync(this.provider);
    }

    @Override
    public void serverTick() {
        if (this.hero) {
            provider.setHealth(HEALTH);
            provider.getHungerManager().setFoodLevel(100);
        }
    }

    @Override
    public boolean isHero() {
        return hero;
    }

    @Override
    public void setHero(boolean hero) {
        this.hero = hero;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hero = tag.getBoolean("hero");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("hero", hero);
    }
}
