package com.larrian.dotacraft.hero.custom;

import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.hero.*;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.entity.custom.MeatHookEntity;
import com.larrian.dotacraft.hero.tick.CommonTickingHero;
import com.larrian.dotacraft.hero.tick.ServerTickingHero;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.larrian.dotacraft.attribute.ModAttributes.STRENGTH;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public class PudgeHero extends DotaHero implements ServerTickingHero {
    public static final String ID = "pudge";

    public static final MainAttributes MAIN_ATTRIBUTE = MainAttributes.STRENGTH;
    public static final double STRENGTH_BONUS = 3.0;
    public static final double AGILITY_BONUS = 1.4;
    public static final double INTELLIGENCE_BONUS = 1.8;

    public static final EnumMap<Skill.Type, Skill> SKILLS = new EnumMap<>(Skill.Type.class);

    static {
        SKILLS.put(Skill.Type.FIRST, new MeatHookSkill());
        SKILLS.put(Skill.Type.SECOND, new RotSkill());
        SKILLS.put(Skill.Type.THIRD, new MeatShieldSkill());
        SKILLS.put(Skill.Type.ULT, new DismemberSkill());
    }

    private static class MeatHookSkill extends Skill {
        private static final double SPEED = 0.7;
        private static final double MANA = 110;
        private static final int[] COOLDOWNS = new int[]{18, 16, 14, 12};

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            int index = (int) ((double)(level - 1) / (30F / (double) COOLDOWNS.length));
            index = index < 0 || index > COOLDOWNS.length - 1 ? 0 : index;
            return COOLDOWNS[index] * 20;
        }

        @Override
        public void use(PlayerEntity source, HeroComponent component) {
            if (source.getWorld() instanceof ServerWorld world) {
                float yaw = source.getYaw();

                double yawRad = Math.toRadians(yaw);
                double x = -Math.sin(yawRad);
                double z = Math.cos(yawRad);
                Vec3d direction = new Vec3d(x, 0.0, z).normalize();

                MeatHookEntity entity = new MeatHookEntity(world, source);
                entity.setNoGravity(true);
                entity.setVelocity(direction.multiply(SPEED)); // apply horizontal velocity
                entity.setPos(source.getX(), source.getY() + 0.25F, source.getZ());

                world.spawnEntity(entity);
            }
        }
    }

    private static class RotSkill extends ToggleSkill {
        private static final double MANA = 0;
        private static final int COOLDOWN = 0;

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            return COOLDOWN * 20;
        }

        @Override
        public void use(PlayerEntity source, HeroComponent component) {}
    }

    private static class MeatShieldSkill extends Skill {
        private static final double MANA = 80;
        private static final int COOLDOWN = 17;

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            return COOLDOWN * 20;
        }

        @Override
        public void use(PlayerEntity source, HeroComponent component) {
            component.deactivateSkill(Type.THIRD);
        }
    }

    private static class DismemberSkill extends Skill {
        private static final double MANA = 170;
        private static final int COOLDOWN = 20;

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            return COOLDOWN * 20;
        }

        @Override
        public void use(PlayerEntity source, HeroComponent component) {
            component.deactivateSkill(Type.ULT);
        }
    }

    PlayerEntity provider;

    byte delayTick;
    HeroComponent _component; // cache

    public PudgeHero(DotaHeroType<PudgeHero> type, AttributesComponent attributes,
                     PlayerEntity provider) {
        super(type, provider);
        attributes.getAttribute(ModAttributes.DAMAGE).set(48); // average
        attributes.getAttribute(ModAttributes.ARMOR).set(1.8);
        attributes.getAttribute(ModAttributes.MOVEMENT_SPEED).set(280);
        attributes.getAttribute(ModAttributes.ATTACK_SPEED).set(111);
        attributes.getAttribute(ModAttributes.ATTACK_INTERVAL).set(1.7);

        attributes.getAttribute(STRENGTH).set(25);
        attributes.getAttribute(ModAttributes.AGILITY).set(11);
        attributes.getAttribute(ModAttributes.INTELLIGENCE).set(16);

        attributes.getAttribute(ModAttributes.MAX_HEALTH).set(120);
        attributes.getAttribute(ModAttributes.MAX_MANA).set(75);
        attributes.getAttribute(ModAttributes.REGENERATION_HEALTH).set(2.75);
        attributes.getAttribute(ModAttributes.REGENERATION_MANA).set(0);

        this.provider = provider;
        this._component = provider.getComponent(HERO_COMPONENT);
    }

    public static List<Entity> getEntitiesInCircle(PlayerEntity playerSource, double radius) {
        World world = playerSource.getWorld();

        Box box = new Box(
                playerSource.getX() - radius, playerSource.getY() - radius, playerSource.getZ() - radius,
                playerSource.getX() + radius, playerSource.getY() + radius, playerSource.getZ() + radius
        );

        List<LivingEntity> entitiesInBox = world.getEntitiesByClass(LivingEntity.class, box, entity -> true);

        Vec3d playerPos = playerSource.getPos();
        return entitiesInBox.stream()
                .filter(entity -> {
                    double distanceXZ = Math.sqrt(
                            Math.pow(playerPos.x - entity.getX(), 2) +
                                    Math.pow(playerPos.z - entity.getZ(), 2)
                    );
                    if (entity instanceof PlayerEntity player && player.isTeammate(playerSource)) {
                        return false;
                    }

                    return distanceXZ <= radius;
                })
                .collect(Collectors.toList());
    }

    public void serverTick() {
        // damage per 2 ticks
        if (_component.isSkillActive(Skill.Type.SECOND)) {
            if (delayTick == 0) {
                for (Entity entity : getEntitiesInCircle(provider, 5.0)) {
                    DamageSources damageSources = provider.getWorld().getDamageSources();
                    DamageSource damageSource = damageSources.create(DamageTypes.PLAYER_ATTACK, provider);
                    entity.damage(damageSource, (float)(3 * _component.getLevel()));
                }
                delayTick = 1;
            } else {
                delayTick--;
            }
        }
    }

    public void readFromNbt(NbtCompound tag) {
         delayTick = tag.getByte("delayTick");
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putByte("delayTick", delayTick);
    }
}
