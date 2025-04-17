package com.larrian.dotacraft;

import com.larrian.dotacraft.dota.DotaHero;
import com.larrian.dotacraft.dota.hero.PudgeHero;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModHeroes {
    public static final PudgeHero PUDGE = register(new PudgeHero());

    private static <T extends DotaHero> T register(T attribute) {
        String id = attribute.getCustomId();
        return Registry.register(ModRegistries.HEROES, new Identifier(DotaCraft.MOD_ID, id), attribute);
    }

    public static void registerModHeroes() {}
}
