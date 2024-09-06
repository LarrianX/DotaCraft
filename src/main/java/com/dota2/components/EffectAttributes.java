package com.dota2.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

public interface EffectAttributes extends ComponentV3, ServerTickingComponent {
    int getTickMana();

    void setTickMana(int tickMana);

    int getTickHealth();

    void setTickHealth(int tickHealth);
}
