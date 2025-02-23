package com.larrian.dotacraft.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;

public interface HealthComponent extends ComponentV3, CommonTickingComponent {
    double get();

    void set(double health);

    void add(double health);

    boolean isFull();

    void sync();
}
