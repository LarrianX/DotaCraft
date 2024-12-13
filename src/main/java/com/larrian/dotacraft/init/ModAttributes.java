package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attributes.*;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModAttributes {
    public static final CritChanceAttribute CRIT_CHANCE = new CritChanceAttribute();
    public static final RegenerationManaAttribute REGENERATION_MANA = new RegenerationManaAttribute();
    public static final RegenerationHealthAttribute REGENERATION_HEALTH = new RegenerationHealthAttribute();
    public static final MaxManaAttribute MAX_MANA = new MaxManaAttribute();
    public static final MaxHealthAttribute MAX_HEALTH = new MaxHealthAttribute();

    public static final ClampedEntityAttribute[] ATTRIBUTES = {
            CRIT_CHANCE,
            REGENERATION_MANA,
            REGENERATION_HEALTH,
            MAX_MANA,
            MAX_HEALTH
    };

    private static void registerAttributes() {
        for (EntityAttribute attributes : ATTRIBUTES) {
            if (attributes instanceof Custom) {
                registerAttribute(((Custom) attributes).getId(), attributes);
            }
        }
    }

    private static void registerAttribute(String name, EntityAttribute attributes) {
        Registry.register(Registries.ATTRIBUTE, new Identifier(DotaCraft.MOD_ID, name), attributes);
    }

    public static void registerModAttributes() {
        DotaCraft.LOGGER.info("Registering Mod Attributes for " + DotaCraft.MOD_ID);
        registerAttributes();
    }
}