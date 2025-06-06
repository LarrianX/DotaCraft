package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.DotaCraft;

import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static com.larrian.dotacraft.effect.ModEffects.TANGO_REGENERATION_HEALTH;
import static com.larrian.dotacraft.item.ModItems.TANGO_TF;

public class TangoItem extends DotaItem {
    public static final String FULLNESS_KEY = "fullness";
    public static final int MAX_FULLNESS = 3;
    private static final String ID = "tango";

    public TangoItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    public static int getFullness(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(FULLNESS_KEY);
    }

    public static void setFullness(ItemStack stack, int fullness) {
        stack.getOrCreateNbt().putInt(FULLNESS_KEY, fullness);
    }

    public static void removeTree(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == Blocks.OAK_LOG || world.getBlockState(pos).getBlock() == Blocks.OAK_LEAVES) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.offset(direction);
                removeTree(world, neighborPos);
            }
        }
    }

    public static void applyEffects(PlayerEntity user) {
        user.playSound(SoundEvents.BLOCK_GRASS_BREAK, 1.0F, 1.0F);
        user.setStatusEffect(new StatusEffectInstance(TANGO_REGENERATION_HEALTH, 320, 0), null);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        NbtCompound nbt = stack.getOrCreateNbt();

        if (!nbt.contains(FULLNESS_KEY) || nbt.getInt(FULLNESS_KEY) > MAX_FULLNESS) {
            setFullness(stack, MAX_FULLNESS);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound nbt = stack.getOrCreateNbt();
        int fullness = nbt.getInt(FULLNESS_KEY);

        if (fullness == 0) {
            return TypedActionResult.fail(stack);
        }

        Entity targetedEntity = DotaCraft.getTargetedEntity(world, user, 5.0D);
        if (targetedEntity instanceof PlayerEntity playerTarget && user.isTeammate(playerTarget)) {
            playerTarget.giveItemStack(new ItemStack(TANGO_TF, 1));
            updateStack(stack);
        } else {
            BlockHitResult hitResult = (BlockHitResult) user.raycast(5.0D, 0.0F, false);
            if (hitResult.getType() == BlockHitResult.Type.BLOCK && world.getBlockState(hitResult.getBlockPos()).getBlock() == Blocks.OAK_LOG) {
                removeTree(world, hitResult.getBlockPos());
                applyEffects(user);

                if (!user.isCreative()) {
                    updateStack(stack);
                }

                return TypedActionResult.success(stack);
            }
        }

        return TypedActionResult.fail(stack);
    }

    private void updateStack(ItemStack stack) {
        int fullness = getFullness(stack);
        if (fullness > 1) {
            setFullness(stack, fullness - 1);
        } else {
            setFullness(stack, MAX_FULLNESS);
            stack.decrement(1);
        }
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack fullTangoStack = new ItemStack(this);
        TangoItem.setFullness(fullTangoStack, TangoItem.MAX_FULLNESS);
        return fullTangoStack;
    }
}
