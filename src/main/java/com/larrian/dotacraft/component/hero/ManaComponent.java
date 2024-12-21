package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;

public interface ManaComponent extends ComponentV3, CommonTickingComponent {
    double getMana();

    void setMana(double mana);

    void addMana(double mana);

    boolean isNotFullMana();

    void sync();
}
