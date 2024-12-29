package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;

public interface ManaComponent extends ComponentV3, CommonTickingComponent {
    double get();

    void set(double mana);

    void add(double mana);

    boolean isFull();

    void sync();
}
