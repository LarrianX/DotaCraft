package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.init.ModItems;
import com.larrian.dotacraft.item.rune.RuneItem;
import com.larrian.dotacraft.rune.Rune;
import com.larrian.dotacraft.rune.Runes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.larrian.dotacraft.init.ModEffects.BOTTLE_REGENERATION_HEALTH;
import static com.larrian.dotacraft.init.ModEffects.BOTTLE_REGENERATION_MANA;

public class BottleItem extends Item implements Custom {
    private static final String ID = "bottle";
    public static final String FULLNESS_KEY = "fullness";
    public static final String RUNE_KEY = "rune";
    public static final int MAX_FULLNESS = 3;

    public BottleItem() {
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

    public static ItemStack emptyBottleInInventory(PlayerInventory inventory) {
        for (ItemStack stack : inventory.main) {
            if (stack.getItem() == ModItems.BOTTLE && stack.getCount() > 0 && getRune(stack) == null) {
                return stack;
            }
        }
        return null;
    }

    public static boolean checkRune(PlayerEntity player, Entity entity) {
        PlayerInventory inventory = player.getInventory();
        ItemStack stack = emptyBottleInInventory(inventory);
        if (stack != null) {
            return checkRune(player, entity, stack);
        } else {
            return false;
        }
    }

    private static boolean checkRune(PlayerEntity player, Entity entity, ItemStack stack) {
        if (entity instanceof ItemEntity itemEntity &&
                itemEntity.getStack().getItem() instanceof RuneItem rune) {
            setRune(stack, rune.getRune());
            player.getItemCooldownManager().set(stack.getItem(), 10);
            itemEntity.kill();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && DotaCraft.DEBUG)
            user.sendMessage(Text.literal("Использован бутыль"));
        // Получаем стак
        ItemStack stack = user.getStackInHand(hand);
        // Получаем значения fullness и rune
        int fullness = getFullness(stack);
        Rune rune = getRune(stack);
        // Если наводимся на руну - использовать нельзя, чтобы исключить вероятность двойного срабатывания
        if (DotaCraft.getTargetedEntity(world, user, 4.5D) instanceof ItemEntity itemEntity &&
                itemEntity.getStack().getItem() instanceof RuneItem) {
            return TypedActionResult.pass(stack);
        }
        // Использование руны
        if (rune != null) {
            rune.use(user);
            user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.0F);

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

    private static void applyEffects(PlayerEntity user) {
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
    public ItemStack getDefaultStack() {
        ItemStack bottleStack = new ItemStack(this);
        setFullness(bottleStack, MAX_FULLNESS);
        return bottleStack;
    }
}
