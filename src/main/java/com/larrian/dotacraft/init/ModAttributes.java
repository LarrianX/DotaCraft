package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attributes.*;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModAttributes {
    public static final List<ClampedEntityAttribute> ATTRIBUTES = new ArrayList<>();

    public static final CritChanceAttribute CRIT_CHANCE = register(new CritChanceAttribute());
    public static final RegenerationManaAttribute REGENERATION_MANA = register(new RegenerationManaAttribute());
    public static final RegenerationHealthAttribute REGENERATION_HEALTH = register(new RegenerationHealthAttribute());
    public static final MaxManaAttribute MAX_MANA = register(new MaxManaAttribute());
    public static final MaxHealthAttribute MAX_HEALTH = register(new MaxHealthAttribute());

    private static <T extends ClampedEntityAttribute> T register(T attribute) {
        if (attribute instanceof Custom) {
            Registry.register(Registries.ATTRIBUTE, new Identifier(DotaCraft.MOD_ID, ((Custom) attribute).getId()), attribute);
        }
        ATTRIBUTES.add(attribute);
        return attribute;
    }
}
