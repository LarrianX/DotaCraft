package com.dota2.attributes;

import com.dota2.Custom;
import com.dota2.DotaCraft;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModAttributes {
    public static final CritChance CRIT_CHANCE = new CritChance();

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