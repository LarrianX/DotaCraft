package com.dota2.components.HeroComponents;

import com.dota2.components.ModComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class SyncedHeroComponent implements HeroComponent, AutoSyncedComponent, ServerTickingComponent {
    public static final int HEALTH = 100;
    private final PlayerEntity provider;
    private boolean hero;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    private void sync() {
        ModComponents.HERO_COMPONENT.sync(this.provider);
    }

    @Override
    public void serverTick() {
        if (this.hero) {
            if (provider.getHealth() != HEALTH)
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
        if (this.hero != hero) {
            this.hero = hero;
            sync();
        }
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
