package com.dota2.components.HeroComponents;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface MaxValuesComponent extends ComponentV3 {
    int getMaxMana();

    void setMaxMana(int maxMana);

    int getMaxHealth();

    void setMaxHealth(int maxHealth);
}
