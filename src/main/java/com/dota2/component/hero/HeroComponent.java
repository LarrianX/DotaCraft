package com.dota2.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.scoreboard.AbstractTeam;

import java.util.Set;

public interface HeroComponent extends ComponentV3, ServerTickingComponent, ClientTickingComponent {
    boolean isHero();

    void setHero(boolean hero);

    AbstractTeam getTeam();

    void sync();

    @Environment(EnvType.CLIENT)
    void setBlock(int slot, boolean blocked);

    @Environment(EnvType.CLIENT)
    boolean isBlocked(int slot);

    @Environment(EnvType.CLIENT)
    Set<Integer> getBlocked();
}
