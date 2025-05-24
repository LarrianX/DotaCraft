package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumMap;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;

public class DotaHeroType<T extends DotaHero> implements Custom {
    private final String id;
    private final HeroFactory<T> factory;

    private final EnumMap<Skill.Type, Skill> skills;
    private final MainAttributes mainAttribute;
    private final double strengthBonus;
    private final double agilityBonus;
    private final double intelligenceBonus;

    public DotaHeroType(String id, HeroFactory<T> factory, EnumMap<Skill.Type, Skill> skills, MainAttributes mainAttribute,
                        double strengthBonus, double agilityBonus, double intelligenceBonus) {
        this.id = id;
        this.factory = factory;
        this.skills = skills;
        this.mainAttribute = mainAttribute;
        this.strengthBonus = strengthBonus;
        this.agilityBonus = agilityBonus;
        this.intelligenceBonus = intelligenceBonus;
    }

    public DotaHero become(PlayerEntity player) {
        return factory.create(this, player.getComponent(ATTRIBUTES_COMPONENT), player);
    }

    @Override
    public String getCustomId() {
        return id;
    }

    public Skill getSkill(Skill.Type type) {
        return skills.get(type);
    }

    public MainAttributes getMainAttribute() {
        return mainAttribute;
    }

    public double getStrengthLevelBonus() {
        return strengthBonus;
    }

    public double getAgilityLevelBonus() {
        return agilityBonus;
    }

    public double getIntelligenceLevelBonus() {
        return intelligenceBonus;
    }

    public interface HeroFactory<T extends DotaHero> {
        T create(DotaHeroType<T> type, AttributesComponent attributes, PlayerEntity player);
    }
}
