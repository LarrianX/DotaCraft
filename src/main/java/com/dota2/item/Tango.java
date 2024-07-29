package com.dota2.item;

import com.dota2.DotaCraft;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public class Tango extends Item implements CustomItem, HasPredicate {
    private static final String ID = "tango";
    private static final String FULLNESS_KEY = "fullness";
    private static final int MAX_FULLNESS = 3;
    private static final Predicate PREDICATE = new Predicate(FULLNESS_KEY, (stack, world, entity, seed) -> {
        float value = (float) Tango.getFullness(stack) / Tango.MAX_FULLNESS;
        // Делим fullness на макс. fullness и получаем float
        return Math.round(value * 100.0f) / 100.0f; // Округляем до двух чисел после запятой
    });

    public Tango() {
        super(new FabricItemSettings().maxCount(8));
    }

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
        // Выполняем ожидаемые действия
        user.playSound(SoundEvents.BLOCK_GRASS_BREAK, 1.0F, 1.0F);
        user.setStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 320, 0),null);
        // Получаем стак
        ItemStack stack = user.getStackInHand(hand);
        // Если человек в креативе - пропускаем уменьшение fullness
        if (user.isCreative()) {
            return TypedActionResult.success(stack);
        }
        // Получаем NBT и ищем значение fullness
        NbtCompound nbt = stack.getOrCreateNbt();
        int fullness = nbt.getInt(FULLNESS_KEY);

        if (fullness > 1) {
            // Уменьшаем значение fullness на 1
            nbt.putInt(FULLNESS_KEY, fullness - 1);
        } else {
            // Уменьшаем значение стака на 1 и ставим макс. fullness
            nbt.putInt(FULLNESS_KEY, MAX_FULLNESS);
            stack.decrement(1);
        }
        return TypedActionResult.success(stack);
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
        ItemStack fullTangoStack = new ItemStack(this);
        Tango.setFullness(fullTangoStack, Tango.MAX_FULLNESS);
        return fullTangoStack;
    }

    @Override
    public Predicate getPredicate() {
        return PREDICATE;
    }
}
