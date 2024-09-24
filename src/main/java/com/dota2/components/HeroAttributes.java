package com.dota2.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

public interface HeroAttributes extends ComponentV3, ServerTickingComponent {
    boolean isHero();
    void setHero(boolean hero);

    int getHealth();
    void setHealth(int health);
    void addHealth(int health);
    boolean isNotFullHealth();

    int getMaxHealth();
    void setMaxHealth(int maxHealth);


    int getMana();
    void setMana(int mana);
    void addMana(int mana);
    boolean isNotFullMana();

    int getMaxMana();
    void setMaxMana(int maxMana);

    // поля, отвечающие за старые значения
    int getOldHealth();
    void setOldHealth(int oldHealth);

    int getOldMaxHealth();
    void setOldMaxHealth(int oldMaxHealth);
}