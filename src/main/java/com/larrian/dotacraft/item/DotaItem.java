package com.larrian.dotacraft.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.ModRegistries;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.attribute.DotaAttributeInstance;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DotaItem extends Item implements Custom {
    private final String id;
    private final Map<DotaAttribute, Double> modifiers;

    public DotaItem(Settings settings, String id) {
        super(settings);
        this.modifiers = Collections.emptyMap();
        this.id = id;
    }

    public DotaItem(Settings settings, Map<DotaAttribute, Double> modifiers, String id) {
        super(settings);
        this.id = id;
        this.modifiers = modifiers;
    }

    protected String getModifierKey(int slot) {
        return String.valueOf(slot);
    }

    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        for (Map.Entry<DotaAttribute, Double> entry : modifiers.entrySet()) {
            DotaAttribute attribute = entry.getKey();
            double value = entry.getValue() * count;
            attributes.get(attribute).addModifier(getModifierKey(slot), value);
        }
    }

    public void removeModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot) {
        for (DotaAttributeInstance attribute : attributes.values()) {
            attribute.removeModifier(String.valueOf(slot));
        }
    }

    public Double getAttributeModifier(DotaAttribute attribute) {
        return modifiers.getOrDefault(attribute, null);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        for (Map.Entry<DotaAttribute, Double> entry : modifiers.entrySet()) {
            DotaAttribute type = entry.getKey();
            double value = entry.getValue();
            if (value != 0) {
                tooltip.add(Text.literal(String.format(" +%d %s", (int) value, type.getCustomId()))
                        .formatted(Formatting.GRAY));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public String getCustomId() {
        return id;
    }
}