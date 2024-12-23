package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

public interface AttributesComponent extends ComponentV3, ServerTickingComponent {
    double getStrength();

    void setStrength(double strength);

    void addStrength(double strength);

    double getAgility();

    void setAgility(double agility);

    void addAgility(double agility);

    double getIntelligence();

    void setIntelligence(double intelligence);

    void addIntelligence(double intelligence);

    void sync();
}
