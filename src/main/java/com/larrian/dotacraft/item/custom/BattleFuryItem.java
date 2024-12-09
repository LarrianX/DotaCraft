package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.item.Weapon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BattleFuryItem extends Weapon implements Custom {
    private static final String ID = "battle_fury";
    private static final int DAMAGE = 65;
    private static final float SPLASH_DAMAGE = 10.0f; // Урон сплэша
    private static final double SPLASH_RADIUS = 5.0;  // Радиус сплэша

    public BattleFuryItem() {
        super(DAMAGE);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        // Проверяем, что кликнули на блок дерева
        if (isLog(block)) {
            Set<BlockPos> treeBlocks = new HashSet<>();
            gatherTreeAndLeavesBlocks(world, pos, block, treeBlocks);

            // Удаляем все собранные блоки дерева и листвы
            for (BlockPos blockPos : treeBlocks) {
                world.breakBlock(blockPos, true, player);  // true - чтобы блоки выпадали
            }

            // Применяем сплэш урон
            if (!world.isClient && player != null) {
                applySplashDamage(world, player);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    // Метод для применения сплэш урона
    private void applySplashDamage(World world, PlayerEntity player) {
        // Получаем позицию игрока
        Vec3d playerPos = player.getPos();

        // Создаем куб для поиска всех сущностей в радиусе
        Box splashBox = new Box(playerPos.add(-SPLASH_RADIUS, -SPLASH_RADIUS, -SPLASH_RADIUS),
                playerPos.add(SPLASH_RADIUS, SPLASH_RADIUS, SPLASH_RADIUS));

        // Получаем всех сущностей в радиусе
        List<Entity> entities = world.getOtherEntities(player, splashBox, entity -> entity instanceof LivingEntity);

        // Наносим урон каждому существу, кроме игрока
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && entity != player) {
                livingEntity.damage(player.getDamageSources().playerAttack(player), SPLASH_DAMAGE);
            }
        }
    }

    // Проверка, является ли блок стволом дерева
    private boolean isLog(Block block) {
        return block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG || block == Blocks.BIRCH_LOG ||
                block == Blocks.JUNGLE_LOG || block == Blocks.ACACIA_LOG || block == Blocks.DARK_OAK_LOG;
    }

    // Проверка, является ли блок листвой
    private boolean isLeaves(Block block) {
        return block == Blocks.OAK_LEAVES || block == Blocks.SPRUCE_LEAVES || block == Blocks.BIRCH_LEAVES ||
                block == Blocks.JUNGLE_LEAVES || block == Blocks.ACACIA_LEAVES || block == Blocks.DARK_OAK_LEAVES;
    }

    // Рекурсивная функция для сбора всех связанных блоков дерева и листвы
    private void gatherTreeAndLeavesBlocks(World world, BlockPos pos, Block block, Set<BlockPos> treeBlocks) {
        if (!treeBlocks.contains(pos)) {
            Block currentBlock = world.getBlockState(pos).getBlock();

            // Если блок — дерево или листва, добавляем его
            if (isLog(currentBlock) || isLeaves(currentBlock)) {
                treeBlocks.add(pos);
                // Проверяем соседние блоки во всех направлениях
                gatherTreeAndLeavesBlocks(world, pos.up(), block, treeBlocks);
                gatherTreeAndLeavesBlocks(world, pos.down(), block, treeBlocks);
                gatherTreeAndLeavesBlocks(world, pos.north(), block, treeBlocks);
                gatherTreeAndLeavesBlocks(world, pos.south(), block, treeBlocks);
                gatherTreeAndLeavesBlocks(world, pos.east(), block, treeBlocks);
                gatherTreeAndLeavesBlocks(world, pos.west(), block, treeBlocks);
            }
        }
    }
}
