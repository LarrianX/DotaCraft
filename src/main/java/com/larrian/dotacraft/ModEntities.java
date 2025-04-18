package com.larrian.dotacraft;

import com.larrian.dotacraft.entity.MeatHookEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<MeatHookEntity> MEAT_HOOK = register(
            MeatHookEntity.ID,
            MeatHookEntity::new,
            SpawnGroup.CREATURE,
            0.5f,
            0.5f
    );

    private static <T extends Entity & Custom> EntityType<T> register(
            String id,
            EntityType.EntityFactory<T> factory,
            SpawnGroup group,
            float width,
            float height
    ) {
        return Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(DotaCraft.MOD_ID, id),
                EntityType.Builder.create(factory, group).setDimensions(width, height)
                        .build(id)
        );
    }

    public static void registerModEntities() {
//        FabricDefaultAttributeRegistry.register(MEAT_HOOK, MeatHookEntity.());
    }
}
