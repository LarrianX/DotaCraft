package com.dota2.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface MaxValuesComponent extends ComponentV3 {
    double getMaxMana();

    void setMaxMana(double maxMana);

    double getMaxHealth();

    void setMaxHealth(double maxHealth);

    void sync();
}
