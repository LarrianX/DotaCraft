package com.dota2.components.HeroComponents;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface HeroComponent extends ComponentV3 {
    boolean isHero();

    void setHero(boolean hero);
}
