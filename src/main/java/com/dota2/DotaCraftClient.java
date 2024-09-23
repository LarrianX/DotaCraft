package com.dota2;

import com.dota2.item.*;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import org.slf4j.Logger;

public class DotaCraftClient implements ClientModInitializer {
    private static final Logger LOGGER = DotaCraft.LOGGER;

    public static final BottlePredicate BOTTLE_PREDICATE = new BottlePredicate();
    public static final TangoPredicate TANGO_PREDICATE = new TangoPredicate();
//
    public static final Predicate[] PREDICATES = {
        BOTTLE_PREDICATE,
        TANGO_PREDICATE
    };


    @Override
    public void onInitializeClient() {
        for (Predicate predicate : PREDICATES) {
            if (predicate.getItem() instanceof CustomItem) {
                ModelPredicateProviderRegistry.register(
                        predicate.getItem(),
                        predicate.getId(),
                        predicate.getProvider()
                );

                LOGGER.info("Registered predicate for {}", ((CustomItem) predicate.getItem()).getId());
            }
        }
    }
}
