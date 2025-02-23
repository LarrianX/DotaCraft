package com.larrian.dotacraft.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface AttributesComponent extends ComponentV3 {
    int getLevel();
    void resetLevel();
    void setLevel(int level);
    void addLevel(int level);

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
