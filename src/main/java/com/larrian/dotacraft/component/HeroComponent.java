package com.larrian.dotacraft.component;

import com.larrian.dotacraft.hero.DotaHero;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.scoreboard.AbstractTeam;

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

    AbstractTeam getTeam();

    void sync();

    @Environment(EnvType.CLIENT)
    void setBlock(int slot, boolean blocked);

    @Environment(EnvType.CLIENT)
    boolean isBlocked(int slot);

    @Environment(EnvType.CLIENT)
    Set<Integer> getBlocked();
}
