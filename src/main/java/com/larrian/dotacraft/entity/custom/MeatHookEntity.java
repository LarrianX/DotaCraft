package com.larrian.dotacraft.entity.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.entity.ModEntities;
import com.larrian.dotacraft.hero.Skill;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public class MeatHookEntity extends PersistentProjectileEntity implements Custom {
    public static final String ID = "meat_hook";
    private final World world;

    public MeatHookEntity(EntityType<? extends MeatHookEntity> entityType, World world) {
        super(entityType, world);
        this.world = world;
    }

    public MeatHookEntity(World world, LivingEntity owner) {
        super(ModEntities.MEAT_HOOK, owner, world);
        this.world = world;
    }

    @Override
    protected void onEntityHit(@NotNull EntityHitResult entityHitResult) {
        Entity hit = entityHitResult.getEntity();
        if (!world.isClient && this.getOwner() != null) {
            Vec3d direction = this.getOwner().getPos().subtract(hit.getPos()).normalize().multiply(3);
            hit.addVelocity(direction.x, direction.y, direction.z);
            _discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        _discard();
    }
    
    private void _discard() {
        if (this.getOwner() != null) {
            this.getOwner().getComponent(HERO_COMPONENT).deactivateSkill(Skill.Type.FIRST);
        }
        this.discard();
    }
    
    @Override
    public void tick() {
        super.tick();
        if (age >= 40) {
            _discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.2F;
    }

    @Override
    public String getCustomId() {
        return ID;
    }
}
