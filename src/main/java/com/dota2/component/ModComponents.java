package com.dota2.component;

import com.dota2.DotaCraft;
import com.dota2.component.hero.*;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;


public final class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<EffectComponent> EFFECT_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "effect"), EffectComponent.class);
    // Hero components
    public static final ComponentKey<HeroComponent> HERO_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "hero"), HeroComponent.class);
    public static final ComponentKey<ValuesComponent> VALUES_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "values"), ValuesComponent.class);
    public static final ComponentKey<MaxValuesComponent> MAX_VALUES_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "max_values"), MaxValuesComponent.class);
    public static final ComponentKey<OldValuesComponent> OLD_VALUES_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "old_values"), OldValuesComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(EFFECT_COMPONENT, SyncedEffectComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(OLD_VALUES_COMPONENT, SyncedOldValuesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(HERO_COMPONENT, SyncedHeroComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(MAX_VALUES_COMPONENT, SyncedMaxValuesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(VALUES_COMPONENT, SyncedValuesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}