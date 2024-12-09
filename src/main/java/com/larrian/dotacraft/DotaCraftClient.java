package com.larrian.dotacraft;

import com.larrian.dotacraft.init.ModEvents;
import com.larrian.dotacraft.item.predicate.BottlePredicate;
import com.larrian.dotacraft.item.Predicate;
import com.larrian.dotacraft.item.predicate.TangoPredicate;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import org.slf4j.Logger;

public class DotaCraftClient implements ClientModInitializer {
    public static final BottlePredicate BOTTLE_PREDICATE = new BottlePredicate();
    public static final TangoPredicate TANGO_PREDICATE = new TangoPredicate();

    public static final Predicate[] PREDICATES = {
            BOTTLE_PREDICATE,
            TANGO_PREDICATE
    };

    @Override
    public void onInitializeClient() {
        for (Predicate predicate : PREDICATES) {
            if (predicate.getItem() instanceof Custom) {
                ModelPredicateProviderRegistry.register(
                        predicate.getItem(),
                        predicate.getId(),
                        predicate.getProvider()
                );
            }
        }
        ModEvents.registerClient();
    }
}
