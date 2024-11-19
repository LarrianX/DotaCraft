package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.dota2.effect.ModEffects.BOTTLE_REGENERATION_HEALTH;
import static com.dota2.effect.ModEffects.BOTTLE_REGENERATION_MANA;

public class Bottle extends Item implements CustomItem {
    public enum Rune {
        SPEED(0.4F, StatusEffects.SPEED, 64);

        private final float state;
        private final StatusEffect effect;
        private final int duration;

        Rune(float state, StatusEffect effect, int duration) {
            this.state = state;
            this.effect = effect;
            this.duration = duration;
        }

        public float getState() {
            return this.state;
        }

        public StatusEffect getEffect() {
            return this.effect;
        }

        public int getDuration() {
            return this.duration;
        }
    }

    public static final String FULLNESS_KEY = "fullness";
    public static final int MAX_FULLNESS = 3;
    public static final String RUNE_KEY = "rune";
    private static final String ID = "bottle";

    public Bottle() {
        super(new FabricItemSettings().maxCount(1));
    }

    public static int getFullness(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(FULLNESS_KEY);
    }

    public static void setFullness(ItemStack stack, int fullness) {
        stack.getOrCreateNbt().putInt(FULLNESS_KEY, fullness);
    }

    public static Rune getRune(ItemStack stack) {
        String runeName = stack.getOrCreateNbt().getString(RUNE_KEY);

        try {
            return Rune.valueOf(runeName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static void setRune(ItemStack stack, Rune rune) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (rune != null) {
            nbt.putString(RUNE_KEY, rune.name());
        } else {
            nbt.remove(RUNE_KEY);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        NbtCompound nbt = stack.getOrCreateNbt();

        if (!nbt.contains(FULLNESS_KEY) || nbt.getInt(FULLNESS_KEY) > MAX_FULLNESS) {
            setFullness(stack, MAX_FULLNESS);
        }
        if (getRune(stack) == null) {
            nbt.remove(RUNE_KEY);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Получаем стак
        ItemStack stack = user.getStackInHand(hand);
        // Получаем значения fullness и rune
        int fullness = getFullness(stack);
        Rune rune = getRune(stack);

        if (rune != null) {
            user.setStatusEffect(new StatusEffectInstance(rune.getEffect(), rune.getDuration()), null);

            if (!user.isCreative()) {
                setRune(stack, null);
                setFullness(stack, MAX_FULLNESS);
                user.getItemCooldownManager().set(this, 10);
                return TypedActionResult.success(stack);

            }
        }
        // Проверяем, достаточно ли fullness для выполнения действия
        if (fullness > 0) {
            // Если не в креативе, уменьшаем fullness на 1
            if (!user.isCreative()) {
                setFullness(stack, fullness - 1);
                user.getItemCooldownManager().set(this, 10);
            }
            // Применяем эффекты
            applyEffects(user);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

    private void applyEffects(PlayerEntity user) {
        // Воспроизводим звуки и эффекты
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.0F);
        user.setStatusEffect(new StatusEffectInstance(BOTTLE_REGENERATION_HEALTH, 54, 0), null);
        user.setStatusEffect(new StatusEffectInstance(BOTTLE_REGENERATION_MANA, 54, 0), null);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        ItemStack bottleStack = new ItemStack(this);
        setFullness(bottleStack, MAX_FULLNESS);
        return bottleStack;
    }
}
