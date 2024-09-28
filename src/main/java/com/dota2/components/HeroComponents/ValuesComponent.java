package com.dota2.components.HeroComponents;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface ValuesComponent extends ComponentV3 {
    double getHealth();

    void setHealth(double health);

    void addHealth(double health);

    boolean isNotFullHealth();

    double getMana();

    void setMana(double mana);

    void addMana(double mana);

    boolean isNotFullMana();
}
