package com.larrian.dotacraft.attributes;

import com.larrian.dotacraft.attributes.custom.*;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.mojang.datafixers.util.Function3;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.StringIdentifiable;

import java.util.function.BiFunction;

public enum DotaAttributes {
    DAMAGE(DamageAttribute::new),
    CRIT_CHANCE(DotaAttribute::new),
    ATTACK_SPEED(DotaAttribute::new),
    ATTACK_INTERVAL(DotaAttribute::new),
    MOVEMENT_SPEED(DotaAttribute::new),
    ARMOR(DotaAttribute::new),
    STRENGTH(StrengthAttribute::new),
    AGILITY(AgilityAttribute::new),
    INTELLIGENCE(IntelligenceAttribute::new),
    MAX_HEALTH(MaxHealthAttribute::new),
    MAX_MANA(MaxManaAttribute::new),
    REGENERATION_HEALTH(RegenerationHealthAttribute::new),
    REGENERATION_MANA(RegenerationManaAttribute::new);

    private final BiFunction<PlayerEntity, AttributesComponent, DotaAttribute> constructor;

    DotaAttributes(BiFunction<PlayerEntity, AttributesComponent, DotaAttribute> constructor) {
        this.constructor = constructor;
    }

    public DotaAttribute createAttribute(PlayerEntity provider, AttributesComponent attributes) {
        return constructor.apply(provider, attributes);
    }
}
