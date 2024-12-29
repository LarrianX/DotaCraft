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
    private static EntityAttributeModifier manaModifier = null;
    private static EntityAttributeModifier healthModifier = null;

    public RuneRegenerationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
    }

    private void addModifier(AttributeContainer attributes, EntityAttribute attribute, EntityAttributeModifier entityAttributeModifier) {
        // shitty code
        EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance(attribute);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(entityAttributeModifier);
            entityAttributeInstance.addPersistentModifier(
                    new EntityAttributeModifier(
                            entityAttributeModifier.getId(),
                            this.getTranslationKey() + " " + 0,
                            this.adjustModifierAmount(0, entityAttributeModifier),
                            entityAttributeModifier.getOperation()
                    )
            );
        }
    }

    private void removeModifier(AttributeContainer attributes, EntityAttribute attribute, EntityAttributeModifier attributeModifier) {
        EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance(attribute);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(attributeModifier);
        }
    }


    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        manaModifier = new EntityAttributeModifier(UUID.fromString("202B7DBE-6360-4FC2-9B81-A8F284A916BC"),
                this::getTranslationKey, attributes.getValue(MAX_MANA) * 0.06 / 20, EntityAttributeModifier.Operation.ADDITION);
        healthModifier = new EntityAttributeModifier(UUID.fromString("609AA5BA-DA4B-48E0-A85D-1C53C9B8940C"),
                this::getTranslationKey, attributes.getValue(MAX_HEALTH) * 0.06 / 20, EntityAttributeModifier.Operation.ADDITION);
        addModifier(attributes, REGENERATION_MANA, manaModifier);
        addModifier(attributes, REGENERATION_HEALTH, healthModifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        removeModifier(attributes, REGENERATION_MANA, manaModifier);
        removeModifier(attributes, REGENERATION_HEALTH, healthModifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}