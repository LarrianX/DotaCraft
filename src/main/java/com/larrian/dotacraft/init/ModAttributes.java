package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attributes.CritChanceAttribute;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModAttributes {
    public static final CritChanceAttribute CRIT_CHANCE = new CritChanceAttribute();

    public static final ClampedEntityAttribute[] ATTRIBUTES = {
            CRIT_CHANCE,
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