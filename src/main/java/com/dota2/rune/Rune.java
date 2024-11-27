package com.dota2.rune;

import com.dota2.Custom;
import com.dota2.item.CustomItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;

public interface Rune extends Custom {
    int getDuration();
    StatusEffect getEffect();
}
