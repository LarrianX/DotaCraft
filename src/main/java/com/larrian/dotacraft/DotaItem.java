package com.larrian.dotacraft;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.dota.DotaAttributeInstance;
import net.minecraft.item.Item;

import java.util.Map;

public abstract class DotaItem extends Item implements Custom {
    private final String id;

    public DotaItem(Settings settings, String id) {
        super(settings);
        this.id = id;
    }

    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {

    }

    public void removeModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot) {
        for (DotaAttributeInstance attribute : attributes.values()) {
            attribute.removeModifier(String.valueOf(slot));
        }
    }

    @Override
    public String getCustomId() {
        return id;
    }
}
