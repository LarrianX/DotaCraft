package com.larrian.dotacraft.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class SyncedAttributesComponent implements AttributesComponent, AutoSyncedComponent {
    private static final double LIMIT = 30000.0;
    private static final int LEVEL_LIMIT = 30;
    private static final int LEVEL_BOOST = 2;

    private final PlayerEntity provider;

    private int level; // 0 - 30

    private double strength;
    private double agility;
    private double intelligence;

    // TODO: speed and damage

    public SyncedAttributesComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    @Override
    public void sync() {
        provider.syncComponent(ATTRIBUTES_COMPONENT);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void resetLevel() {
        addStrength(-LEVEL_BOOST * getLevel());
        addAgility(-LEVEL_BOOST * getLevel());
        addIntelligence(-LEVEL_BOOST * getLevel());
        this.level = 0;
    }

    @Override
    public void setLevel(int level) {
        level = Math.max(0, Math.min(level, LEVEL_LIMIT));
        int forAdd = level - getLevel();
        addStrength(LEVEL_BOOST * forAdd);
        addAgility(LEVEL_BOOST * forAdd);
        addIntelligence(LEVEL_BOOST * forAdd);
        this.level = level;
    }

    @Override
    public void addLevel(int level) {
        setLevel(getLevel() + level);
    }

    @Override
    public double getStrength() {
        return this.strength;
    }

    @Override
    public void setStrength(double strength) {
        this.strength = Math.max(0, Math.min(strength, LIMIT));
        applyStrengthModifiers();
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
        applyAgilityModifiers();
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
        applyIntelligenceModifiers();
    }

    @Override
    public void addIntelligence(double intelligence) {
        setIntelligence(getIntelligence() + intelligence);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.level = tag.getInt("level");
        this.strength = tag.getDouble("strength");
        this.agility = tag.getDouble("agility");
        this.intelligence = tag.getDouble("intelligence");
        applyAllModifiers();
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("level", this.level);
        tag.putDouble("strength", this.strength);
        tag.putDouble("agility", this.agility);
        tag.putDouble("intelligence", this.intelligence);
    }

    private void applyStrengthModifiers() {
        addModifier(MAX_HEALTH, "38F520DA-4892-4F79-A0F9-141781473B8F", strength * 22);
        addModifier(REGENERATION_HEALTH, "31BC4398-D5BC-4035-AC59-E2A253E825CF", strength * (0.1 / 20));
    }

    private void applyAgilityModifiers() {
        addModifier(CRIT_CHANCE, "B85B8328-3312-4631-B9D4-F16C9815ABB9", agility);
    }

    private void applyIntelligenceModifiers() {
        addModifier(MAX_MANA, "E5676606-EBF3-44C6-ABD7-D01FF3DAA6B0", intelligence * 12);
        addModifier(REGENERATION_MANA, "1C6BC529-8580-4CF6-B773-40A9D55FFFFC", intelligence * (0.05 / 20));
    }

    private void applyAllModifiers() {
        applyStrengthModifiers();
        applyAgilityModifiers();
        applyIntelligenceModifiers();
    }

    private void addModifier(EntityAttribute attribute, String uuidString, double amount) {
        UUID uuid = UUID.fromString(uuidString);
        EntityAttributeModifier modifier = new EntityAttributeModifier(uuid, "SyncedAttributesComponent", amount, EntityAttributeModifier.Operation.ADDITION);
        EntityAttributeInstance attributeInstance = provider.getAttributeInstance(attribute);
        if (attributeInstance != null) {
            removeModifier(attributeInstance, uuid);
            if (amount > 0) {
                attributeInstance.addPersistentModifier(modifier);
            }
        }
    }

    private void removeModifier(EntityAttributeInstance attribute, UUID uuid) {
        for (EntityAttributeModifier modifier : attribute.getModifiers()) {
            if (modifier.getId().equals(uuid)) {
                attribute.removeModifier(modifier);
            }
        }
    }
}
