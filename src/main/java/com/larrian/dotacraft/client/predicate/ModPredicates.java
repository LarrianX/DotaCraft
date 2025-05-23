package com.larrian.dotacraft.client.predicate;

import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.client.predicate.custom.BottlePredicate;
import com.larrian.dotacraft.client.predicate.custom.TangoPredicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModPredicates {
    public static final BottlePredicate BOTTLE = register(new BottlePredicate());
    public static final TangoPredicate TANGO = register(new TangoPredicate());

    private static <T extends Predicate> T register(T predicate) {
        if (predicate.getItem() instanceof DotaItem) {
            ModelPredicateProviderRegistry.register(
                    predicate.getItem(),
                    new Identifier(predicate.getCustomId()),
                    predicate.getProvider()
            );
        }
        return predicate;
    }

    @Environment(EnvType.CLIENT)
    public static void registerModPredicates() {}
}
