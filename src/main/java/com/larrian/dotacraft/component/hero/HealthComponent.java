package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;

public interface HealthComponent extends ComponentV3, CommonTickingComponent {
    double getHealth();

    void setHealth(double health);

    void addHealth(double health);

    boolean isNotFullHealth();

    void sync();
}
