package com.larrian.dotacraft.component.attributes;

import com.mojang.datafixers.util.Function3;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.larrian.dotacraft.component.attributes.SyncedAttributesComponent.LEVEL_BOOST;
import static com.larrian.dotacraft.init.ModEffects.RUNE_DOUBLE_DAMAGE_EFFECT;
import static com.larrian.dotacraft.init.ModEffects.RUNE_REGENERATION_EFFECT;

public enum DotaAttributeType {
    DAMAGE((PlayerEntity player, AttributesComponent attributes, Double value) ->
            player.hasStatusEffect(RUNE_DOUBLE_DAMAGE_EFFECT) ? value : 0),

    CRIT_CHANCE,
    ATTACK_SPEED,
    MOVEMENT_SPEED,
    ARMOR,
    STRENGTH((AttributesComponent attributes) -> attributes.getLevel() * LEVEL_BOOST),
    AGILITY((AttributesComponent attributes) -> attributes.getLevel() * LEVEL_BOOST),
    INTELLIGENCE((AttributesComponent attributes) -> attributes.getLevel() * LEVEL_BOOST),
    MAX_HEALTH((AttributesComponent attributes) -> attributes.getAttribute(DotaAttributeType.STRENGTH).get() * 22),
    MAX_MANA((AttributesComponent attributes) -> attributes.getAttribute(DotaAttributeType.INTELLIGENCE).get() * 12),

    REGENERATION_HEALTH((PlayerEntity player, AttributesComponent attributes, Double value) ->
            player.hasStatusEffect(RUNE_REGENERATION_EFFECT) ? (
                    attributes.getAttribute(MAX_HEALTH).get() * 0.06) : 0 + // TODO: decrease regeneration by 5% when taking damage
            attributes.getAttribute(DotaAttributeType.STRENGTH).get() * 0.09),

    REGENERATION_MANA((PlayerEntity player, AttributesComponent attributes, Double value) ->
            player.hasStatusEffect(RUNE_REGENERATION_EFFECT) ? (
                    attributes.getAttribute(MAX_MANA).get() * 0.06) : 0 +
            attributes.getAttribute(DotaAttributeType.INTELLIGENCE).get() * 0.05);

    private final Function3<PlayerEntity, AttributesComponent, Double, Double> fullFunction;
    private final Function<AttributesComponent, Double> functionWithAttributes;

    DotaAttributeType() {
        this.fullFunction = null;
        this.functionWithAttributes = null;
    }

    DotaAttributeType(Function3<PlayerEntity, AttributesComponent, Double, Double> fullFunction) {
        this.fullFunction = fullFunction;
        this.functionWithAttributes = null;
    }

    DotaAttributeType(Function<AttributesComponent, Double> functionWithAttributes) {
        this.fullFunction = null;
        this.functionWithAttributes = functionWithAttributes;
    }

    public double getCorrelationValue(PlayerEntity player, AttributesComponent attributes, double value) {
        if (fullFunction != null) {
            return fullFunction.apply(player, attributes, value) + value;
        } else if (functionWithAttributes != null) {
            return functionWithAttributes.apply(attributes) + value;
        } else {
            return value;
        }
    }
}
