package com.dota2;

import com.dota2.item.*;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

import static com.dota2.item.ModItems.BOTTLE;
import static com.dota2.item.ModItems.TANGO;

public class DotaCraftClient implements ClientModInitializer {
    private static final CustomPredicateItemWrapper<?>[] PREDICATE_ITEMS = {
            new CustomPredicateItemWrapper<>(BOTTLE),
            new CustomPredicateItemWrapper<>(TANGO)
    };

    @Override
    public void onInitializeClient() {
        for (CustomPredicateItemWrapper<?> item : PREDICATE_ITEMS) {

            Predicate predicate = item.getItem().getPredicate();
            ModelPredicateProviderRegistry.register(
                    item.getItem(),
                    predicate.getId(),
                    predicate.getProvider()
            );

            DotaCraft.LOGGER.info("Registered predicate for {}", item.getItem().getId());

        }
    }
}
