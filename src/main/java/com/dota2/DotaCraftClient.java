package com.dota2;

import com.dota2.item.Bottle;
import com.dota2.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DotaCraftClient implements ClientModInitializer {
    public static final String MOD_ID = "dotacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(
            ModItems.BOTTLE,
            new Identifier("fullness"),
            (stack, world, entity, seed) -> {
                float value = (float) Bottle.getFullness(stack) / Bottle.MAX_FULLNESS;
                return Math.round(value * 100.0f) / 100.0f;
            }
        );


    }
}
