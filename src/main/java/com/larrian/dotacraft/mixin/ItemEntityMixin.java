package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.item.Bottle;
import com.larrian.dotacraft.item.rune.RuneItem;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    @Shadow
    public abstract ItemStack getStack();

    @Shadow
    private int pickupDelay;

    @Shadow
    private UUID owner;

    private ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        // cancel if collision pickup is disabled
        if (player.getComponent(HERO_COMPONENT).isHero()) {
            ci.cancel();
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            if (DotaCraft.DEBUG)
                player.sendMessage(Text.literal("Использована сущность предмета"));
            String name = this.getName().getString();
            ItemStack itemStack = this.getStack();
            Item item = itemStack.getItem();
            int i = itemStack.getCount();

            if (
//                    this.pickupDelay == 0 &&
                    (this.owner == null || this.owner.equals(player.getUuid()))) {
                if (itemStack.getItem() instanceof RuneItem runeItem) {
                    // Применение руны
                    boolean result = Bottle.checkRune(player, this);
                    if (!result) {
                        // Если в инвентаре нет пустой бутылки - используем руну так
                        Rune rune = runeItem.getRune();
                        player.setStatusEffect(new StatusEffectInstance(rune.getEffect(), rune.getDuration()), null);
                        kill();
                        return ActionResult.SUCCESS;
                    }
                } else {
                    boolean result = player.getInventory().insertStack(itemStack);
                    if (result) {
                        player.sendPickup(this, i);
                        if (itemStack.isEmpty()) {
                            this.discard();
                            itemStack.setCount(i);
                        }

                        player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), i);
                        player.triggerItemPickedUpByEntityCriteria((ItemEntity) (Object) this);
                        player.sendMessage(Text.literal("Подобран предмет: " + name), true);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
    }

//    @Override
//    public boolean isCollidable() {
//        return false;
//    }
//
}