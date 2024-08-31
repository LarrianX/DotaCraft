package com.dota2.components;

import com.dota2.DotaCraft;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public final class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<HeroAttributes> HERO_ATTRIBUTES =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "hero_attributes"), HeroAttributes.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // Add the component to every PlayerEntity instance, and copy it on respawn with keepInventory
        registry.registerForPlayers(HERO_ATTRIBUTES, player -> new PlayerComponent(player), RespawnCopyStrategy.INVENTORY);
    }
}