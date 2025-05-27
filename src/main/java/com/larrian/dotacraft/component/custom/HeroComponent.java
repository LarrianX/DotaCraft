package com.larrian.dotacraft.component.custom;

import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.hero.DotaHeroType;
import com.larrian.dotacraft.hero.Skill;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.EnumMap;
import java.util.Set;

public interface HeroComponent extends ComponentV3, ServerTickingComponent, ClientTickingComponent {
    DotaHero getHero();

    DotaHeroType<?> getHeroType();

    boolean isHero();

    void setHero(DotaHero hero);

    // For backward compatibility, we are keeping these methods and redirecting them to DotaHero
    double getHealth();
    boolean isFullHealth();
    void setHealth(double health);
    void addHealth(double amount);
    double getMana();
    boolean isFullMana();
    void setMana(double mana);
    void addMana(double amount);
    int getLevel();
    void setLevel(int level);
    void addLevel(int level);
    EnumMap<Skill.Type, Integer> getSkillCooldowns();
    void useSkill(Skill.Type skillType);
    void deactivateSkill(Skill.Type skillType);
    boolean isSkillActive(Skill.Type skillType);
    @Environment(EnvType.CLIENT)
    void setBlock(int slot, boolean blocked);
    @Environment(EnvType.CLIENT)
    boolean isBlocked(int slot);
    @Environment(EnvType.CLIENT)
    Set<Integer> getBlocked();

    void sync();
}