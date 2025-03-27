package com.larrian.dotacraft.component.attributes;

import net.minecraft.entity.player.PlayerEntity;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.DoubleUnaryOperator;

import static com.larrian.dotacraft.component.attributes.SyncedAttributesComponent.LEVEL_BOOST;
import static com.larrian.dotacraft.init.ModEffects.RUNE_DOUBLE_DAMAGE_EFFECT;

public enum DotaAttributeType {
    DAMAGE((PlayerEntity player, Double value) -> {
        if (player.hasStatusEffect(RUNE_DOUBLE_DAMAGE_EFFECT)) {
            return value * 2;
        } else {
            return value;
        }
    }),
    CRIT_CHANCE,
    ATTACK_SPEED,
    MOVEMENT_SPEED,
    ARMOR,
    STRENGTH            ((AttributesComponent attributes) -> attributes.getLevel() * LEVEL_BOOST),
    AGILITY             ((AttributesComponent attributes) -> attributes.getLevel() * LEVEL_BOOST),
    INTELLIGENCE        ((AttributesComponent attributes) -> attributes.getLevel() * LEVEL_BOOST),
    MAX_HEALTH          ((AttributesComponent attributes) -> attributes.getAttribute(DotaAttributeType.STRENGTH).get() * 22),
    MAX_MANA            ((AttributesComponent attributes) -> attributes.getAttribute(DotaAttributeType.INTELLIGENCE).get() * 12),
    REGENERATION_HEALTH ((AttributesComponent attributes) -> attributes.getAttribute(DotaAttributeType.STRENGTH).get() * 0.09),
    REGENERATION_MANA   ((AttributesComponent attributes) -> attributes.getAttribute(DotaAttributeType.INTELLIGENCE).get() * 0.05);

    private final BiFunction<PlayerEntity, Double, Double> doubleFunctionWithPlayer;
    private final Function<AttributesComponent, Double> functionWithAttributes;

    DotaAttributeType() {
        this.doubleFunctionWithPlayer = null;
        this.functionWithAttributes = null;
    }

    DotaAttributeType(BiFunction<PlayerEntity, Double, Double> doubleFunctionWithPlayer) {
        this.doubleFunctionWithPlayer = doubleFunctionWithPlayer;
        this.functionWithAttributes = null;
    }

    DotaAttributeType(Function<AttributesComponent, Double> functionWithAttributes) {
        this.doubleFunctionWithPlayer = null;
        this.functionWithAttributes = functionWithAttributes;
    }

    public double getCorrelationValue(PlayerEntity player, AttributesComponent attributes, double value) {
        if (doubleFunctionWithPlayer != null) {
            return doubleFunctionWithPlayer.apply(player, value);
        } else if (functionWithAttributes != null) {
            return functionWithAttributes.apply(attributes) + value;
        } else {
            return value;
        }
    }
}
