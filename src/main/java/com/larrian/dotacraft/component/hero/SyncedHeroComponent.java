package com.larrian.dotacraft.component.hero;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attributes.RegenerationHealthAttribute;
import com.larrian.dotacraft.event.AutoCraft;
import com.larrian.dotacraft.event.ServerEvents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.AbstractTeam;

import java.util.HashSet;
import java.util.Set;

import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_MANA;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, AutoSyncedComponent {
    public static final int HEALTH = 100;

    private final PlayerEntity provider;
    private boolean hero;
    private NbtList cache;

    private final Set<Integer> clientBlockedSlots;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtList();
        this.clientBlockedSlots = new HashSet<>();
    }

    @Override
    public void sync() {
        provider.syncComponent(HERO_COMPONENT);
    }

    @Override
    public void serverTick() {
        if (this.hero) {
            provider.setHealth(HEALTH);
            provider.getHungerManager().setFoodLevel(100);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void clientTick() {
        if (this.hero) {
            if (DotaCraft.AUTO_CRAFT) {
                NbtList current = provider.getInventory().writeNbt(new NbtList());
                if (!this.cache.equals(current)) {
                    boolean result = AutoCraft.craft(provider, clientBlockedSlots);
                    if (result) {
                        this.cache = current;
                        ClientPlayNetworking.send(ServerEvents.AUTO_CRAFT_PACKET, new PacketByteBuf(Unpooled.buffer()));
                    }
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

    // Methods to work with clientBlockedSlots
    @Environment(EnvType.CLIENT)
    public void setBlock(int slot, boolean blocked) {
        if (blocked) {
            clientBlockedSlots.add(slot);
        } else {
            clientBlockedSlots.remove(slot);
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean isBlocked(int slot) {
        return clientBlockedSlots.contains(slot);
    }

    @Environment(EnvType.CLIENT)
    public Set<Integer> getBlocked() {
        return clientBlockedSlots;
    }
}
