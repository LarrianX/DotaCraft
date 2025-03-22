package com.larrian.dotacraft.component.attributes;

import net.minecraft.entity.player.PlayerEntity;
import java.util.function.BiFunction;

import static com.larrian.dotacraft.component.attributes.SyncedAttributesComponent.LEVEL_BOOST;

public enum DotaAttributeType {
    DAMAGE,
    CRIT_CHANCE,
    ATTACK_SPEED,
    MOVEMENT_SPEED,
    ARMOR,
    STRENGTH((player, attributes) -> attributes.getLevel() * LEVEL_BOOST),
    AGILITY((player, attributes) -> attributes.getLevel() * LEVEL_BOOST),
    INTELLIGENCE((player, attributes) -> attributes.getLevel() * LEVEL_BOOST),
    MAX_HEALTH((player, attributes) -> attributes.getAttribute(DotaAttributeType.STRENGTH).get() * 22),
    MAX_MANA((player, attributes) -> attributes.getAttribute(DotaAttributeType.INTELLIGENCE).get() * 12),
    REGENERATION_HEALTH((player, attributes) -> attributes.getAttribute(DotaAttributeType.STRENGTH).get() * 0.09),
    REGENERATION_MANA((player, attributes) -> attributes.getAttribute(DotaAttributeType.INTELLIGENCE).get() * 0.05);

    private final BiFunction<PlayerEntity, AttributesComponent, Double> correlation;

    DotaAttributeType() {
        this.correlation = null;
    }

    DotaAttributeType(BiFunction<PlayerEntity, AttributesComponent, Double> correlation) {
        this.correlation = correlation;
    }

    public double getCorrelationValue(PlayerEntity player, AttributesComponent attributes) {
        return correlation != null ? correlation.apply(player, attributes) : 0;
    }
}
