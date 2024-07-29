package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
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

public class Bottle extends Item implements CustomItem, HasPredicate {
    private static final String ID = "bottle";
    private static final String FULLNESS_KEY = "fullness";
    private static final int MAX_FULLNESS = 3;
    private static final Predicate PREDICATE = new Predicate(FULLNESS_KEY, (stack, world, entity, seed) -> {
        float value = (float) Bottle.getFullness(stack) / Bottle.MAX_FULLNESS;
        return Math.round(value * 100.0f) / 100.0f;
    });

    public Bottle() {
        super(new FabricItemSettings().maxCount(1));
    }

    // Отключено в целях оптимизации
    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.World world, Entity entity, int slot, boolean selected) {
        // inventoryTick - функция вызывается каждый тик в инвентаре
        super.inventoryTick(stack, world, entity, slot, selected);
        // Получаем nbt из стака
        NbtCompound nbt = stack.getOrCreateNbt();
        // Если у предмета нет в nbt нашего fullness - создаём fullness и ставим максимум
        if (!nbt.contains(FULLNESS_KEY) || nbt.getInt(FULLNESS_KEY) > MAX_FULLNESS) {
            nbt.putInt(FULLNESS_KEY, MAX_FULLNESS);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Получаем стак
        ItemStack stack = user.getStackInHand(hand);
        // Получаем NBT и ищем значение fullness
        NbtCompound nbt = stack.getOrCreateNbt();
        int fullness = nbt.getInt(FULLNESS_KEY);

        if (fullness >= 1) {
            // Если человек не в креативе - уменьшаем fullness на 1
            if (!user.isCreative()) {
                nbt.putInt(FULLNESS_KEY, fullness - 1);
            }
            // Воспроизводим нужные действия
            user.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_PLACE, 1.0F, 1.0F);
            user.setStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 65, 2), null);
            user.setStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 7, 0), null);
            // Возращаем удачный результат
            return TypedActionResult.success(stack);
        } else {
            // Не удалось выпить пустую бутылку
            return TypedActionResult.fail(stack);
        }
    }

    public static int getFullness(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        return nbt.getInt(FULLNESS_KEY);
    }

    public static void setFullness(ItemStack stack, int fullness) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(FULLNESS_KEY, fullness);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getForTabItemGroup() {
        ItemStack fullBottleStack = new ItemStack(this);
        Bottle.setFullness(fullBottleStack, Bottle.MAX_FULLNESS);
        return fullBottleStack;
    }

    @Override
    public Predicate getPredicate() {
        return PREDICATE;
    }
}
