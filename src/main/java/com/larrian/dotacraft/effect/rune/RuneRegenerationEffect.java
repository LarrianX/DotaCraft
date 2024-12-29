package com.larrian.dotacraft.effect.rune;

import com.google.common.collect.Maps;
import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.RegenerationHealthAttribute;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.Map;
import java.util.UUID;

import static com.larrian.dotacraft.init.ModAttributes.*;

public class RuneRegenerationEffect extends StatusEffect implements Custom {
    private static final String ID = "rune_regeneration";

    public RuneRegenerationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
    }

    private void addModifier(AttributeContainer attributes, EntityAttribute attribute, EntityAttributeModifier entityAttributeModifier, int amplifier) {
        // shitty code
        EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance(attribute);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(entityAttributeModifier);
            entityAttributeInstance.addPersistentModifier(
                    new EntityAttributeModifier(
                            entityAttributeModifier.getId(),
                            this.getTranslationKey() + " " + amplifier,
                            this.adjustModifierAmount(amplifier, entityAttributeModifier),
                            entityAttributeModifier.getOperation()
                    )
            );
        }
    }

    private void removeModifier(AttributeContainer attributes, EntityAttribute attribute, UUID uuid) {
        EntityAttributeInstance attributeInstance = attributes.getCustomInstance(attribute);
        if (attributeInstance != null) {
            for (EntityAttributeModifier modifier : attributeInstance.getModifiers()) {
                if (modifier.getId().equals(uuid)) {
                    attributeInstance.removeModifier(modifier);
                }
            }
        }
    }


    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        addModifier(attributes, REGENERATION_MANA, new EntityAttributeModifier(UUID.fromString("609AA5BA-DA4B-48E0-A85D-1C53C9B8940C"),
                this::getTranslationKey, attributes.getValue(MAX_HEALTH) * 0.06 / 20, EntityAttributeModifier.Operation.ADDITION), amplifier);
        addModifier(attributes, REGENERATION_HEALTH, new EntityAttributeModifier(UUID.fromString("202B7DBE-6360-4FC2-9B81-A8F284A916BC"),
                this::getTranslationKey, attributes.getValue(MAX_MANA) * 0.06 / 20, EntityAttributeModifier.Operation.ADDITION), amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        removeModifier(attributes, REGENERATION_MANA, UUID.fromString("609AA5BA-DA4B-48E0-A85D-1C53C9B8940C"));
        removeModifier(attributes, REGENERATION_HEALTH, UUID.fromString("202B7DBE-6360-4FC2-9B81-A8F284A916BC"));
    }

    @Override
    public String getId() {
        return ID;
    }
}