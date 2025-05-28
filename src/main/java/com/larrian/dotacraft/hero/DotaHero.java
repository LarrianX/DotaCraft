

package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public abstract class DotaHero implements Custom {
    private final DotaHeroType<?> type;

    protected DotaHero(DotaHeroType<?> type, PlayerEntity provider) {
        this.type = type;
    }

    public DotaHeroType<?> getType() {
        return type;
    }

    public String getAdditionalInfo() {
        return "";
    }

    @Override
    public String getCustomId() {
        return type.getCustomId();
    }

    public abstract void readFromNbt(NbtCompound tag);

    public abstract void writeToNbt(NbtCompound tag);
}