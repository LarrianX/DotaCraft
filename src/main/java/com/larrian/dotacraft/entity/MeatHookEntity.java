package com.larrian.dotacraft.entity;

import com.larrian.dotacraft.DotaEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

import java.util.Collections;

public class MeatHookEntity extends DotaEntity {
    public static final String ID = "meat_hook";

    public MeatHookEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world, ID);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        // No-op
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

}
