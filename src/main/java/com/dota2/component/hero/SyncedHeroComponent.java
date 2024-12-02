package com.dota2.component.hero;

import com.dota2.DotaCraft;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.scoreboard.AbstractTeam;

import static com.dota2.component.ModComponents.HERO_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, AutoSyncedComponent {
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

    public static ItemStack itemInInventory(PlayerInventory inventory, Item item) {
        for (ItemStack stack : inventory.main) {
            if (stack.getItem() == item && stack.getCount() > 0) {
                return stack;
            }
        }
        return null;
    }

    private void autocraft(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        for (Item[] recipe : DotaCraft.RECIPES.keySet()) {
            boolean canCraft = true;
            for (Item recipeItem : recipe) {
                if (itemInInventory(inventory, recipeItem) == null) {
                    canCraft = false;
                    break;
                }
            }
            if (canCraft) {
                for (Item recipeItem : recipe) {
                    inventory.removeOne(itemInInventory(inventory, recipeItem));
                }
                Item resultItem = DotaCraft.RECIPES.get(recipe);
                inventory.insertStack(resultItem.getDefaultStack());
                player.getItemCooldownManager().set(resultItem, 10);
            }
        }
    }


    @Override
    public void serverTick() {
        if (this.hero) {
            provider.setHealth(HEALTH);
            provider.getHungerManager().setFoodLevel(100);
            // autocraft
            if (DotaCraft.AUTO_CRAFT) {
                NbtList current = provider.getInventory().writeNbt(new NbtList());
                if (!this.cache.equals(current)) {
                    this.cache = current;
                    autocraft(provider);
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
