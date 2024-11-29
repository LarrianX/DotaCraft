package com.dota2.component.hero;

import com.dota2.DotaCraft;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.scoreboard.AbstractTeam;

import static com.dota2.component.ModComponents.HERO_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, ServerTickingComponent, AutoSyncedComponent {
    public static final int HEALTH = 100;

    private final PlayerEntity provider;
    private boolean hero;
    private NbtList cache;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtList();
    }

    @Override
    public void sync() {
        provider.syncComponent(HERO_COMPONENT);
    }

    private void autocraft(PlayerInventory inventory) {
        for (Item[] items : DotaCraft.RECIPES.keySet()) {
            boolean canCraft = true;
            for (Item item : items) {
                if (!inventory.contains(new ItemStack(item))) {
                    canCraft = false;
                    break;
                }
            }
            if (canCraft) {
                for (Item item : items) {
                    inventory.removeOne(new ItemStack(item));
                }
                inventory.insertStack(DotaCraft.RECIPES.get(items).getDefaultStack());
            }
        }
        DotaCraft.LOGGER.info("Tried to autocraft");
    }


    @Override
    public void serverTick() {
        DotaCraft.LOGGER.warn("Tried to serverTick");
        if (this.hero) {
            provider.setHealth(HEALTH);
            provider.getHungerManager().setFoodLevel(100);
            // autocraft
            if (DotaCraft.AUTO_CRAFT) {
                NbtList current = provider.getInventory().writeNbt(new NbtList());
                if (!this.cache.equals(current)) {
                    this.cache = current;
                    autocraft(provider.getInventory());
                }
            }
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
    public AbstractTeam getTeam() {
        return provider.getScoreboardTeam();
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
