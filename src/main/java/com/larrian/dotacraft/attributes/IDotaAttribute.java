package com.larrian.dotacraft.attributes;

public interface IDotaAttribute {
    void set(double value);
    double getBase();
    void add(double value);
    double get();
    void addModifier(String key, double amount);
    void removeModifier(String key);
    void clearModifiers();
}
