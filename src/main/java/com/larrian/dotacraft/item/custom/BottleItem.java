package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.item.ModItems;

import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.item.RuneItem;
import com.larrian.dotacraft.rune.DotaRune;
import com.larrian.dotacraft.rune.Runes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.larrian.dotacraft.effect.ModEffects.BOTTLE_REGENERATION_HEALTH;
import static com.larrian.dotacraft.effect.ModEffects.BOTTLE_REGENERATION_MANA;

public class BottleItem extends DotaItem {
    private static final String ID = "bottle";
    public static final String FULLNESS_KEY = "fullness";
    public static final String RUNE_KEY = "rune";
    public static final int MAX_FULLNESS = 3;

    public BottleItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    // Get the current fullness level from the NBT data
    public static int getFullness(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(FULLNESS_KEY);
    }

    // Set the fullness level in the NBT data
    public static void setFullness(ItemStack stack, int fullness) {
        stack.getOrCreateNbt().putInt(FULLNESS_KEY, fullness);
    }

    // Retrieve the Rune from the NBT data
    public static DotaRune getRune(ItemStack stack) {
        String runeName = stack.getOrCreateNbt().getString(RUNE_KEY);
        try {
            return Runes.valueOf(runeName).getRune();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // Store the Rune in the NBT data; remove the key if the rune is null
    public static void setRune(ItemStack stack, DotaRune rune) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (rune != null) {
            nbt.putString(RUNE_KEY, rune.getCustomId());
        } else {
            nbt.remove(RUNE_KEY);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        NbtCompound nbt = stack.getOrCreateNbt();

        // Ensure the fullness value is initialized and does not exceed the maximum
        if (!nbt.contains(FULLNESS_KEY) || nbt.getInt(FULLNESS_KEY) > MAX_FULLNESS) {
            setFullness(stack, MAX_FULLNESS);
        }

        // Remove the rune key if it does not correspond to a valid rune
        if (nbt.contains(RUNE_KEY) && getRune(stack) == null) {
            setRune(stack, null);
        }
    }

    // Find an empty bottle in the player's inventory
    public static ItemStack emptyBottleInInventory(PlayerInventory inventory) {
        ItemStack mainHandStack = inventory.getMainHandStack();
        if (checkStack(mainHandStack)) {
            return mainHandStack;
        }
        // Iterate through the main inventory slots
        for (ItemStack stack : inventory.main) {
            if (checkStack(stack)) {
                return stack;
            }
        }
        return null;
    }

    // Check if the given ItemStack is a valid empty bottle
    private static boolean checkStack(ItemStack stack) {
        return stack.getItem() == ModItems.BOTTLE && stack.getCount() > 0 && getRune(stack) == null;
    }

    // Check and process rune pickup from an entity
    public static boolean checkRune(PlayerEntity player, Entity entity) {
        PlayerInventory inventory = player.getInventory();
        ItemStack stack = emptyBottleInInventory(inventory);
        if (stack != null) {
            return checkRune(player, entity, stack);
        }
        return false;
    }

    // Process the rune if the entity is a valid rune item
    private static boolean checkRune(PlayerEntity player, Entity entity, ItemStack stack) {
        if (entity instanceof ItemEntity itemEntity &&
                itemEntity.getStack().getItem() instanceof RuneItem rune) {
            setRune(stack, rune.getRune());
            // Set cooldown of 10 ticks for the bottle item
            player.getItemCooldownManager().set(stack.getItem(), 10);
            // Remove the rune entity from the world
            itemEntity.kill();
            return true;
        }
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Get the item stack from the hand
        ItemStack stack = user.getStackInHand(hand);
        int fullness = getFullness(stack);
        DotaRune rune = getRune(stack);

        // Prevent double activation if targeting a rune item in the world
        if (DotaCraft.getTargetedEntity(world, user, 4.5D) instanceof ItemEntity itemEntity &&
                itemEntity.getStack().getItem() instanceof RuneItem) {
            return TypedActionResult.pass(stack);
        }

        // Use the rune if present
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

        // Apply effects if there is enough fullness
        if (fullness > 0) {
            if (!user.isCreative()) {
                setFullness(stack, fullness - 1);
                user.getItemCooldownManager().set(this, 10);
            }
            applyEffects(user);
            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

    // Apply health and mana regeneration effects and play sound
    private static void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.0F);
        user.setStatusEffect(new StatusEffectInstance(BOTTLE_REGENERATION_HEALTH, 54, 0), null);
        user.setStatusEffect(new StatusEffectInstance(BOTTLE_REGENERATION_MANA, 54, 0), null);
    }

    // Create the default item stack with maximum fullness
    @Override
    public ItemStack getDefaultStack() {
        ItemStack bottleStack = new ItemStack(this);
        setFullness(bottleStack, MAX_FULLNESS);
        return bottleStack;
    }
}
