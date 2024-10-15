package com.dota2.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;

public interface HeroComponent extends ComponentV3 {
    boolean isHero();

    void setHero(boolean hero);

    AbstractTeam getTeam();

    void sync();
}
