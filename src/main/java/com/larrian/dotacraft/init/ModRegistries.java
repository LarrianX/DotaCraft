package com.larrian.dotacraft.init;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.hero.DotaHero;
import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ModRegistries {
    public static final RegistryKey<Registry<DotaAttribute>> ATTRIBUTES_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(DotaCraft.MOD_ID, "attributes"));
    public static final Registry<DotaAttribute> ATTRIBUTES = new SimpleRegistry<>(ATTRIBUTES_REGISTRY_KEY, Lifecycle.experimental());
    public static final RegistryKey<Registry<DotaHero>> HERO_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(DotaCraft.MOD_ID, "heroes"));
    public static final Registry<DotaHero> HEROES = new SimpleRegistry<>(HERO_REGISTRY_KEY, Lifecycle.experimental());

    public static void registerModRegistries() {}
}
