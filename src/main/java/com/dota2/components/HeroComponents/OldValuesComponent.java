package com.dota2.components.HeroComponents;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface OldValuesComponent extends ComponentV3 {
    int getOldHealth();

    void setOldHealth(int oldHealth);

    int getOldMaxHealth();

    void setOldMaxHealth(int oldMaxHealth);
}
