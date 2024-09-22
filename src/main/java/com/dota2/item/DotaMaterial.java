package com.dota2.item;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DotaMaterial implements ToolMaterial {
    public static final DotaMaterial INSTANCE = new DotaMaterial();

    @Override
    public int getDurability() {
        return -1; // Прочность
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 6.0F; // Скорость добычи
    }

    @Override
    public float getAttackDamage() {
        return 1.0F; // Урон
    }

    @Override
    public int getMiningLevel() {
        return 0; // Уровень добычи (например, камень — 1, железо — 2)
    }

    @Override
    public int getEnchantability() {
        return 0; // Зачаровываемость
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.AIR); // Материал для починки
    }

}
