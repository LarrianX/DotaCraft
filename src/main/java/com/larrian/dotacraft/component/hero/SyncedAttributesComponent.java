package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class SyncedAttributesComponent implements AttributesComponent, AutoSyncedComponent {
    private static final double LIMIT = 30000.0;

    private final PlayerEntity provider;
    private final Map<EntityAttribute, UUID> attributeModifiers = new HashMap<>();

    private double strength;
    private double agility;
    private double intelligence;
    private boolean changed;

    public SyncedAttributesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.changed = false;
    }

    @Override
    public void sync() {
        provider.syncComponent(ATTRIBUTES_COMPONENT);
    }

    @Override
    public double getStrength() {
        return this.strength;
    }

    @Override
    public void setStrength(double strength) {
        this.strength = Math.max(0, Math.min(strength, LIMIT));
        this.changed = true;
    }

    @Override
    public void addStrength(double strength) {
        setStrength(getStrength() + strength);
    }

    @Override
    public double getAgility() {
        return this.agility;
    }

    @Override
    public void setAgility(double agility) {
        this.agility = Math.max(0, Math.min(agility, LIMIT));
        this.changed = true;
    }

    @Override
    public void addAgility(double agility) {
        setAgility(getAgility() + agility);
    }

    @Override
    public double getIntelligence() {
        return this.intelligence;
    }

    @Override
    public void setIntelligence(double intelligence) {
        this.intelligence = Math.max(0, Math.min(intelligence, LIMIT));
        this.changed = true;
    }

    @Override
    public void addIntelligence(double intelligence) {
        setIntelligence(getIntelligence() + intelligence);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.strength = tag.getDouble("strength");
        this.agility = tag.getDouble("agility");
        this.intelligence = tag.getDouble("intelligence");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("strength", this.strength);
        tag.putDouble("agility", this.agility);
        tag.putDouble("intelligence", this.intelligence);
    }

    @Override
    public void serverTick() {
        if (changed) {
            removeModifiers();
            applyModifiers();
            changed = false;
        }
    }

    private void removeModifiers() {
        for (Map.Entry<EntityAttribute, UUID> entry : attributeModifiers.entrySet()) {
            EntityAttributeInstance attributeInstance = provider.getAttributeInstance(entry.getKey());
            if (attributeInstance != null) {
                attributeInstance.removeModifier(entry.getValue());
            }
        }
        attributeModifiers.clear();
    }

    private void applyModifiers() {
        addModifier(MAX_HEALTH, "38F520DA-4892-4F79-A0F9-141781473B8F", strength * 22);
        addModifier(REGENERATION_HEALTH, "31BC4398-D5BC-4035-AC59-E2A253E825CF", strength * (0.1 / 20));
        addModifier(MAX_MANA, "E5676606-EBF3-44C6-ABD7-D01FF3DAA6B0", intelligence * 12);
        addModifier(REGENERATION_MANA, "1C6BC529-8580-4CF6-B773-40A9D55FFFFC", intelligence * (0.05 / 20));
        addModifier(CRIT_CHANCE, "B85B8328-3312-4631-B9D4-F16C9815ABB9", agility);
        // Скорость атаки и броня будут добавлены позже
    }

    private void addModifier(EntityAttribute attribute, String uuidString, double amount) {
        UUID uuid = UUID.fromString(uuidString); // Оставляю UUID генерацию, вы замените на свои значения
        EntityAttributeModifier modifier = new EntityAttributeModifier(uuid, "SyncedAttributesComponent", amount, EntityAttributeModifier.Operation.ADDITION);
        EntityAttributeInstance attributeInstance = provider.getAttributeInstance(attribute);
        if (attributeInstance != null) {
            attributeInstance.addPersistentModifier(modifier);
            attributeModifiers.put(attribute, uuid);
        }
    }
}
