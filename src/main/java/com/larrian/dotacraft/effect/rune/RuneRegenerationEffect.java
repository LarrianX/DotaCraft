package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.RegenerationHealthAttribute;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RuneRegenerationEffect extends StatusEffect implements Custom {
    private static final String ID = "rune_regeneration";
    public RuneRegenerationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
        addAttributeModifier(ModAttributes.REGENERATION_HEALTH, "609AA5BA-DA4B-48E0-A85D-1C53C9B8940C", 1.06, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(ModAttributes.REGENERATION_MANA, "202B7DBE-6360-4FC2-9B81-A8F284A916BC", 1.06, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public String getId() {
        return ID; }
}