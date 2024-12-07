package com.larrian.dotacraft.rune;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.item.CustomItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;

public interface Rune extends Custom {
    int getDuration();
    StatusEffect getEffect();
}
