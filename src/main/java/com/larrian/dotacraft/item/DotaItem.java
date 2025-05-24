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

import java.util.HashMap;
import java.util.List;
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Item item = stack.getItem();
        if (!(item instanceof DotaItem dotaItem)) {
            return;
        }

        Map<DotaAttribute, DotaAttributeInstance> attributes = new HashMap<>();
        Map<DotaAttribute, Double> modifiersMap = new HashMap<>();

        for (DotaAttribute type : ModRegistries.ATTRIBUTES) {
            attributes.put(type, new DotaAttributeInstance(type, null) {
                @Override
                public void set(double value) {}

                @Override
                public double getBase() {
                    return 0;
                }

                @Override
                public void add(double value) {}

                @Override
                public double get() {
                    return 0;
                }

                @Override
                public void addModifier(String key, double amount) {
                    modifiersMap.put(type, modifiersMap.getOrDefault(type, 0.0) + amount);
                }

                @Override
                public void removeModifier(String key) {}

                @Override
                public void clearModifiers() {}
            });
        }

        dotaItem.addModifiers(attributes, 0, 1);
//        tooltip.add(Text.literal("Attributes:").formatted(Formatting.GRAY, Formatting.BOLD));

        for (var entry : modifiersMap.entrySet()) {
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
