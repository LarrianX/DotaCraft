package com.larrian.dotacraft.effect;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.*;

public abstract class RegenerationMana extends StatusEffect implements Custom {
    private final double amplifier;

    public RegenerationMana(StatusEffectCategory category, int color, double amplifier) {
        super(category, color);
        this.amplifier = amplifier;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier_) {
        super.onApplied(entity, attributes, amplifier_);
        if (entity instanceof PlayerEntity player) {
            player.getComponent(ATTRIBUTES_COMPONENT).getAttribute(DotaAttributeType.REGENERATION_MANA).addModifier(getId(), amplifier);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int effectAmplifier) {
        super.onRemoved(entity, attributes, effectAmplifier);
        if (entity instanceof PlayerEntity player) {
            player.getComponent(ATTRIBUTES_COMPONENT).getAttribute(DotaAttributeType.REGENERATION_MANA).removeModifier(getId());
        }
    }
}
