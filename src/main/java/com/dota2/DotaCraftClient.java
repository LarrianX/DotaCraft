package com.dota2;

import com.dota2.item.CustomItem;
import com.dota2.item.HasPredicate;
import com.dota2.item.Predicate;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;

import static com.dota2.item.ModItems.ITEMS;

public class DotaCraftClient implements ClientModInitializer {
    private static int X = -91;
    private static int Y = -39;
    private static double HEALTH = 10.0;

    public static void setX(int x) {
        X = x;
    }

    public static void setY(int y) {
        Y = y;
    }

    public static void setHealth(double health) {
        HEALTH = health;
    }

    public static int getX() {
        return X;
    }

    public static int getY() {
        return Y;
    }

    public static double getHealth() {
        return HEALTH;
    }

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
