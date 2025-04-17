package com.larrian.dotacraft;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public abstract class DotaEntity extends LivingEntity implements Custom {
    private final String id;

    protected DotaEntity(EntityType<? extends LivingEntity> entityType, World world, String id) {
        super(entityType, world);
        this.id = id;
    }

    @Override
    public String getCustomId() {
        return id;
    }
}
