package com.dota2.item;

import com.dota2.DotaCraft;
import com.dota2.item.rune.RuneItem;
import com.dota2.rune.Rune;
import com.dota2.rune.Runes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
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
    private static final String ID = "bottle";
    public static final String FULLNESS_KEY = "fullness";
    public static final String RUNE_KEY = "rune";
    public static final int MAX_FULLNESS = 3;

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
            return Runes.valueOf(runeName).getRune();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static void setRune(ItemStack stack, Rune rune) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (rune != null) {
            nbt.putString(RUNE_KEY, rune.getId());
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
        if (nbt.contains(RUNE_KEY) && getRune(stack) == null) {
            setRune(stack, null);
        }
    }

    private boolean checkRune(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Entity targetedEntity = DotaCraft.getTargetedEntity(world, player, 5.0D);

        if (targetedEntity instanceof ItemEntity itemEntity &&
                itemEntity.getStack().getItem() instanceof RuneItem rune) {
            setRune(stack, rune.getRune());
            itemEntity.kill();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Получаем стак
        ItemStack stack = user.getStackInHand(hand);
        // Получаем значения fullness и rune
        int fullness = getFullness(stack);
        Rune rune = getRune(stack);
        // Проверяем, можно ли захватить руну
        if (rune == null && checkRune(world, user, hand)) {
            return TypedActionResult.success(stack);
        }

        if (rune != null) {
            user.setStatusEffect(new StatusEffectInstance(rune.getEffect(), rune.getDuration()), null);

            if (!user.isCreative()) {
                setRune(stack, null);
                setFullness(stack, MAX_FULLNESS);
                user.getItemCooldownManager().set(this, 10);

            }
            return TypedActionResult.success(stack);

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
