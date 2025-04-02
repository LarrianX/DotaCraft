package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.hero.custom.Pudge;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModHeroes {
    public static final Pudge PUDGE = register(new Pudge());

    private static <T extends DotaHero & Custom> T register(T attribute) {
        String id = attribute.getId();
        Registry.register(ModRegistries.HEROES, new Identifier(DotaCraft.MOD_ID, id), attribute);
        return attribute;
    }

    public static void registerModHeroes() {}
}
