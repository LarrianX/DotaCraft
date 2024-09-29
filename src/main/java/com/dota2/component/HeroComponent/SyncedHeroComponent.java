package com.dota2.component.HeroComponent;

import com.dota2.component.ModComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class SyncedHeroComponent implements HeroComponent, ServerTickingComponent, AutoSyncedComponent {
    public static final int HEALTH = 100;
    private final PlayerEntity provider;
    private boolean hero;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    @Override
    public void sync() {
        ModComponents.HERO_COMPONENT.sync(this.provider);
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