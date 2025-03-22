package com.larrian.dotacraft.component;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.attributes.AttributesComponent;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
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

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private boolean hero;
    private double health;
    private double mana;
    private NbtList cache;

    private final Set<Integer> clientBlockedSlots;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtList();
        this.clientBlockedSlots = new HashSet<>();
    }

    private AttributesComponent getAttributesComponent() {
        return this.provider.getComponent(ATTRIBUTES_COMPONENT);
    }

    @Override
    public void sync() {
        provider.syncComponent(HERO_COMPONENT);
    }

    private void regeneration() {
        AttributesComponent attributes = getAttributesComponent();
        addHealth(attributes.getAttribute(DotaAttributeType.REGENERATION_HEALTH).get() / 20);
        addMana(attributes.getAttribute(DotaAttributeType.REGENERATION_MANA).get() / 20);
    }

    @Override
    public void serverTick() {
        regeneration();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void clientTick() {
        regeneration();
        if (this.hero && DotaCraft.AUTO_CRAFT) {
            NbtList current = provider.getInventory().writeNbt(new NbtList());
            if (!this.cache.equals(current)) {
                boolean result = AutoCraft.craft(provider, clientBlockedSlots);
                if (result) {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    buf.writeInt(clientBlockedSlots.size());
                    for (Integer slot : clientBlockedSlots) {
                        buf.writeInt(slot);
                    }
                    ClientPlayNetworking.send(ServerEvents.AUTO_CRAFT_PACKET, buf);
                    this.cache = current;
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
    public double getHealth() {
        return health;
    }

    @Override
    public boolean isFullHealth() {
        return getHealth() == getAttributesComponent().getAttribute(DotaAttributeType.MAX_HEALTH).get();
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.max(0, Math.min(health, getAttributesComponent().getAttribute(DotaAttributeType.MAX_HEALTH).get()));
    }

    @Override
    public void addHealth(double amount) {
        setHealth(this.health + amount);
    }

    @Override
    public double getMana() {
        return mana;
    }

    @Override
    public boolean isFullMana() {
        return getMana() == getAttributesComponent().getAttribute(DotaAttributeType.MAX_MANA).get();
    }

    @Override
    public void setMana(double mana) {
        this.mana = Math.max(0, Math.min(mana, getAttributesComponent().getAttribute(DotaAttributeType.MAX_MANA).get()));
    }

    @Override
    public void addMana(double amount) {
        setMana(this.mana + amount);
    }

    @Override
    public AbstractTeam getTeam() {
        return provider.getScoreboardTeam();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hero = tag.getBoolean("hero");
        this.health = tag.getDouble("health");
        this.mana = tag.getDouble("mana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("hero", hero);
        tag.putDouble("health", health);
        tag.putDouble("mana", mana);
    }

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
