package com.larrian.dotacraft.init;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.hero.*;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;


public final class ModComponents implements EntityComponentInitializer {// Hero components
    public static final ComponentKey<HeroComponent> HERO_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "hero"), HeroComponent.class);
    public static final ComponentKey<ManaComponent> MANA_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "mana"), ManaComponent.class);
    public static final ComponentKey<HealthComponent> HEALTH_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "health"), HealthComponent.class);
    public static final ComponentKey<OldValuesComponent> OLD_VALUES_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "old_values"), OldValuesComponent.class);
    public static final ComponentKey<AttributesComponent> ATTRIBUTES_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "attributes"), AttributesComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(OLD_VALUES_COMPONENT, SyncedOldValuesComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(HERO_COMPONENT, SyncedHeroComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(MANA_COMPONENT, SyncedManaComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(HEALTH_COMPONENT, SyncedHealthComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(ATTRIBUTES_COMPONENT, SyncedAttributesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}