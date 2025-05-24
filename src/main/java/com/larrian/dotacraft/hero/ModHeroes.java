package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.ModRegistries;
import com.larrian.dotacraft.hero.custom.PudgeHero;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.EnumMap;

public class ModHeroes {
    public static final DotaHeroType<PudgeHero> PUDGE = register(
            PudgeHero.ID,
            PudgeHero::new,
            PudgeHero.SKILLS,
            PudgeHero.MAIN_ATTRIBUTE,
            PudgeHero.STRENGTH_BONUS,
            PudgeHero.AGILITY_BONUS,
            PudgeHero.INTELLIGENCE_BONUS
    );

    private static <T extends DotaHero> DotaHeroType<T> register(
            String id, DotaHeroType.HeroFactory<T> factory, EnumMap<Skill.Type, Skill> skills, MainAttributes mainAttribute,
            double strengthBonus, double agilityBonus, double intelligenceBonus) {
        return Registry.register(
                ModRegistries.HEROES,
                new Identifier(DotaCraft.MOD_ID, id),
                new DotaHeroType<>(id, factory, skills, mainAttribute, strengthBonus, agilityBonus, intelligenceBonus)
        );
    }

    public static void registerModHeroes() {}
}
