package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.item.DotaItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(Item.class)
public class AttributesTooltipMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    private void injectTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || !client.options.advancedItemTooltips) {
            return;
        }

        Item item = stack.getItem();
        if (!(item instanceof DotaItem dotaItem)) {
            return;
        }

        EnumMap<DotaAttributeType, IDotaAttribute> attributes = new EnumMap<>(DotaAttributeType.class);
        Map<DotaAttributeType, Double> modifiersMap = new HashMap<>();

        for (DotaAttributeType type : DotaAttributeType.values()) {
            attributes.put(type, new IDotaAttribute() {
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
            DotaAttributeType type = entry.getKey();
            double value = entry.getValue();

            if (value != 0) {
                tooltip.add(Text.literal(String.format(" +%d %s", (int) value, type.name()))
                        .formatted(Formatting.GRAY));
            }
        }
    }
}
