package com.dota2.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface HeroAttributes extends ComponentV3 {
    boolean isHero();

    void setHero(boolean hero);

    int getHealth();

    void setHealth(int health);

    // Методы для переменной 'max_health'
    int getMaxHealth();

    void setMaxHealth(int maxHealth);

    // Методы для переменной 'hunger'
    int getMana();

    void setMana(int hunger);

    // Методы для переменной 'max_hunger'
    int getMaxMana();

    void setMaxMana(int maxHunger);
}
