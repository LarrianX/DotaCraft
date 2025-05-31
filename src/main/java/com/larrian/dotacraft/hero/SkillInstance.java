package com.larrian.dotacraft.hero;

import net.minecraft.nbt.NbtCompound;

public class SkillInstance {
    private int cooldown;
    private boolean active;
    private int level;
    private int maxLevel;

    public SkillInstance() {}

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, Math.min(level, maxLevel));
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        setLevel(getLevel());
    }

    public void readFromNbt(NbtCompound tag) {
        this.cooldown = tag.getInt("cooldown");
        this.active = tag.getBoolean("active");
        setLevel(tag.getByte("level"));
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putInt("cooldown", cooldown);
        tag.putBoolean("active", active);
        tag.putByte("level", (byte)level);
    }
}
