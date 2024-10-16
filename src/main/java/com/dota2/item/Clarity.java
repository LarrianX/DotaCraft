package com.dota2.item;

import com.dota2.component.EffectComponent;
import com.dota2.effect.ModEffects;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

import static com.dota2.component.ModComponents.EFFECT_COMPONENT;

public class Clarity extends Item implements CustomItem {
    private static final String ID = "clarity";

    public Clarity() {
        super(new FabricItemSettings().maxCount(8));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Получаем стак, который игрок держит в руке
        ItemStack stack = user.getStackInHand(hand);

        // Максимальная дистанция, на которую игрок может взаимодействовать с сущностями
        double reachDistance = 5.0D;

        // Выполняем raycast (луч) для поиска объекта, на который смотрит игрок
        Vec3d cameraPos = user.getCameraPosVec(1.0F);
        Vec3d lookVec = user.getRotationVec(1.0F);
        Vec3d reachVec = cameraPos.add(lookVec.multiply(reachDistance));

        // Поиск ближайшей сущности в пределах досягаемости
        Entity targetedEntity = null;
        List<Entity> entities = world.getEntitiesByClass(Entity.class, user.getBoundingBox().stretch(lookVec.multiply(reachDistance)).expand(1.0D), e -> e != user);

        for (Entity entity : entities) {
            Box entityBox = entity.getBoundingBox().expand(entity.getTargetingMargin());
            Optional<Vec3d> optional = entityBox.raycast(cameraPos, reachVec);

            if (entityBox.contains(cameraPos) || optional.isPresent()) {
                targetedEntity = entity;
                break;
            }
        }

        if (targetedEntity instanceof PlayerEntity playerTarget) {
            // Применяем эффекты на игрока, на которого смотрят
            applyEffects(playerTarget);
        } else {
            // Если игрок ни на кого не смотрит, применяем эффекты на самого пользователя
            applyEffects(user);
        }

        // Если игрок не в креативе - уменьшаем стак на один
        if (!user.isCreative()) {
            user.getItemCooldownManager().set(this, 10);
            stack.decrement(1);
        }

        // Успех
        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
        user.setStatusEffect(new StatusEffectInstance(ModEffects.REGENERATION_MANA, 500, 0), null);
        EffectComponent component = user.getComponent(EFFECT_COMPONENT);
        component.getAmplifiers().put(ModEffects.REGENERATION_MANA.getId(), ((double) 150 / 500) + ERROR); // погрешность
        component.sync();
    }

    @Override
    public String getId() {
        return ID;
    }
}
