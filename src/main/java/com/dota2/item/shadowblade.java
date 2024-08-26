package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class shadowblade extends Item implements CustomItem {
    private static final String ID = "shadowblade";

    public shadowblade() {
        super(new FabricItemSettings().maxCount(1));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        applyEffects(user);

        return null;
                // Линар, я хуй знает что я тут сделал, но я пытался. Тут мои полномочия всё. Заливаю коммит.
    }

    private void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.5F);
        user.setStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 340, 0),null);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        return new ItemStack(this);
    }
}
