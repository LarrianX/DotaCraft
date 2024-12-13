package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

public interface ValuesComponent extends ComponentV3, ServerTickingComponent, ClientTickingComponent {
    double getHealth();

    void setHealth(double health);

    void addHealth(double health);

    boolean isNotFullHealth();

    double getMana();

    void setMana(double mana);

    void addMana(double mana);

    boolean isNotFullMana();

    void sync();
}
