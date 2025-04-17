package com.larrian.dotacraft;

import com.larrian.dotacraft.entity.MeatHookEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<MeatHookEntity> MEAT_HOOK = register(
            MeatHookEntity.ID,
            MeatHookEntity::new,
            SpawnGroup.CREATURE
    );

    private static <T extends DotaEntity> EntityType<T> register(
            String id,
            EntityType.EntityFactory<T> factory,
            SpawnGroup group
    ) {
        return Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(DotaCraft.MOD_ID, id),
                EntityType.Builder.create(factory, group)
                        .build(id)
        );
    }

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(MEAT_HOOK, MeatHookEntity.createLivingAttributes());
    }
}
