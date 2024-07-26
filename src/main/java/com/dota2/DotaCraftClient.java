package com.dota2;

import com.dota2.item.Bottle;
import com.dota2.item.CustomItem;
import com.dota2.item.CustomItemWrapper;
import com.dota2.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DotaCraftClient implements ClientModInitializer {
    public static final String MOD_ID = "dotacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
//        Item bottle = null;
//        for (CustomItemWrapper<?> wrapper : ModItems.items) {
//            if (wrapper.item().getId().equals("bottle")) {
//                bottle = wrapper.item();
//            }
//        }

//        if (bottle != null) {
//            ModelPredicateProviderRegistry.register(
//                    bottle,
//                    new Identifier("fullness"),
//                    (stack, world, entity, seed) -> {
//                        float value = (float) Bottle.getFullness(stack) / Bottle.MAX_FULLNESS;
//                        return Math.round(value * 100.0f) / 100.0f;
//                    }
//            );
//            LOGGER.info("Registered predicate for bottle");
//        }
    }
}
