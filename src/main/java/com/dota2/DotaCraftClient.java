package com.dota2;

import com.dota2.item.CustomItem;
import com.dota2.item.HasPredicate;
import com.dota2.item.Predicate;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;

import static com.dota2.item.ModItems.ITEMS;

public class DotaCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (Item item : ITEMS) {
            if (item instanceof CustomItem && item instanceof HasPredicate) {
                Predicate predicate = ((HasPredicate) item).getPredicate();
                ModelPredicateProviderRegistry.register(
                        item,
                        predicate.getId(),
                        predicate.getProvider()
                );

                DotaCraft.LOGGER.info("Registered predicate for {}", ((CustomItem) item).getId());
            }
        }
    }
}
