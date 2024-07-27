package com.dota2;

import com.dota2.item.*;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class DotaCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (CustomPredicateItemWrapper<?> item : ModItems.getPredicateItems()) {

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
