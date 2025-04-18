
package com.larrian.dotacraft.dota.attribute;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.dota.DotaAttribute;

import static com.larrian.dotacraft.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.ModEffects.RUNE_REGENERATION;

import com.larrian.dotacraft.effect.RegenerationHealthEffect;
import com.larrian.dotacraft.dota.ModAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class RegenerationHealthAttribute extends DotaAttribute {
    private static final String ID = "regeneration_health";

    public RegenerationHealthAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        double regenFromStrength = attributes.getAttribute(ModAttributes.STRENGTH).get() * 0.09;
        double regenFromRune = player.hasStatusEffect(RUNE_REGENERATION)
                ? attributes.getAttribute(ModAttributes.MAX_HEALTH).get() * 0.06 : 0;
        double regenFromEffect = 0;
        for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
            if (effectInstance.getEffectType() instanceof RegenerationHealthEffect effect) {
                regenFromEffect += effect.getAmplifier();
            }
        }
        return value + regenFromStrength + regenFromRune + regenFromEffect;
    }
}
