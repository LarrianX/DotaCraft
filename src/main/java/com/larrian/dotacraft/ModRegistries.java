package com.larrian.dotacraft;

import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.hero.DotaHeroType;
import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ModRegistries {
    public static final RegistryKey<Registry<DotaAttribute>> ATTRIBUTES_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(DotaCraft.MOD_ID, "attributes"));
    public static final Registry<DotaAttribute> ATTRIBUTES = new SimpleRegistry<>(ATTRIBUTES_REGISTRY_KEY, Lifecycle.experimental());
    public static final RegistryKey<Registry<DotaHeroType<?>>> HERO_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(DotaCraft.MOD_ID, "heroes"));
    public static final Registry<DotaHeroType<?>> HERO_TYPES = new SimpleRegistry<>(HERO_REGISTRY_KEY, Lifecycle.experimental());

    public static void registerModRegistries() {}
}
