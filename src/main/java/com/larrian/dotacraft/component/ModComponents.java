package com.larrian.dotacraft.component;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.component.custom.SyncedAttributesComponent;
import com.larrian.dotacraft.component.custom.SyncedHeroComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;


public final class ModComponents implements EntityComponentInitializer {// Hero components
    public static final ComponentKey<HeroComponent> HERO_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "hero"), HeroComponent.class);
    public static final ComponentKey<AttributesComponent> ATTRIBUTES_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(DotaCraft.MOD_ID, "attributes"), AttributesComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(HERO_COMPONENT, SyncedHeroComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(ATTRIBUTES_COMPONENT, SyncedAttributesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}