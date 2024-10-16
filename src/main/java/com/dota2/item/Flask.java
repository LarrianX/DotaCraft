package com.dota2.item;

import com.dota2.component.EffectComponent;
import com.dota2.effect.ModEffects;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.dota2.component.ModComponents.EFFECT_COMPONENT;

public class Flask extends Item implements CustomItem {
    private static final String ID = "flask";

    public Flask() {
        super(new FabricItemSettings().maxCount(8));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Воспроизводим нужные действия
        applyEffects(user);
        // Получаем стак
        ItemStack stack = user.getStackInHand(hand);
        // Если человек не в креативе - уменьшаем стак на один
        if (!user.isCreative()) {
            stack.decrement(1);
            user.getItemCooldownManager().set(this, 10);
        }
        // Успех
        return TypedActionResult.success(stack);
    }

    private void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
        user.setStatusEffect(new StatusEffectInstance(ModEffects.REGENERATION_HEALTH, 260, 0), null);
        EffectComponent component = user.getComponent(EFFECT_COMPONENT);
        component.getAmplifiers().put(ModEffects.REGENERATION_HEALTH.getId(), ((double) 390 / 260) + ERROR); // погрешность
        component.sync();
    }





    @Override
    public String getId() {
        return ID;
    }
}
