package com.larrian.dotacraft.component;

import com.larrian.dotacraft.dota.DotaHero;
import com.larrian.dotacraft.dota.Skill;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.EnumMap;
import java.util.Set;

public interface HeroComponent extends ComponentV3, ServerTickingComponent, ClientTickingComponent {
    DotaHero getHero();

    boolean isHero();

    void setHero(DotaHero hero);

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

    void sync();

    void useSkill(Skill.Type type);

    @Environment(EnvType.CLIENT)
    void setBlock(int slot, boolean blocked);

    @Environment(EnvType.CLIENT)
    boolean isBlocked(int slot);

    @Environment(EnvType.CLIENT)
    Set<Integer> getBlocked();
}
