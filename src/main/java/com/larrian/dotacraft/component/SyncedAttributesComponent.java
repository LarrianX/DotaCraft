package com.larrian.dotacraft.component;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.dota.DotaAttributeInstance;
import com.larrian.dotacraft.ModRegistries;
import com.larrian.dotacraft.DotaItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.HashMap;
import java.util.Map;

import static com.larrian.dotacraft.ModComponents.ATTRIBUTES_COMPONENT;

/* Component to synchronize player attributes. */
public class SyncedAttributesComponent implements AttributesComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private Map<Integer, ItemStack> cache;
    private final Map<DotaAttribute, DotaAttributeInstance> attributes = new HashMap<>();

    public SyncedAttributesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new HashMap<>();

        // Create attribute instances using enum registry
        for (DotaAttribute type : ModRegistries.ATTRIBUTES) {
            attributes.put(type, new DotaAttributeInstance(type, provider));
        }
    }

    @Override
    public void sync() {
        provider.syncComponent(ATTRIBUTES_COMPONENT);
    }

    public void tick() {
        Map<Integer, ItemStack> current = mapInventory(provider.getInventory());

        if (!current.equals(this.cache)) {
            updateModifiers(cache, current);
            this.cache = current;
        }
    }

    @Override
    public void clientTick() {
        tick();
    }

    @Override
    public void serverTick() {
        tick();
    }

    private void updateModifiers(Map<Integer, ItemStack> oldItems, Map<Integer, ItemStack> newItems) {
        for (Map.Entry<Integer, ItemStack> entry : oldItems.entrySet()) {
            int slot = entry.getKey();
            ItemStack oldItem = entry.getValue();
            ItemStack newItem = newItems.get(slot);

            if (newItem == null || !ItemStack.areEqual(oldItem, newItem)) {
                removeModifiers((DotaItem) oldItem.getItem(), slot);
            }
        }

        for (Map.Entry<Integer, ItemStack> entry : newItems.entrySet()) {
            int slot = entry.getKey();
            ItemStack newItem = entry.getValue();
            ItemStack oldItem = oldItems.get(slot);

            if (oldItem == null || !ItemStack.areEqual(newItem, oldItem)) {
                if (newItem.getItem() instanceof DotaItem item) {
                    addModifiers(item, slot, newItem.getCount());
                }
            }
        }
    }

    private Map<Integer, ItemStack> mapInventory(PlayerInventory inventory) {
        Map<Integer, ItemStack> map = new HashMap<>();
        for (int slot = 0; slot < inventory.main.size(); slot++) {
            ItemStack stack = inventory.main.get(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof DotaItem) {
                map.put(slot, stack.copy());
            }
        }
        return map;
    }

    private void removeModifiers(DotaItem item, int slot) {
        item.removeModifiers(this.attributes, slot);
    }

    private void addModifiers(DotaItem item, int slot, int count) {
        item.addModifiers(this.attributes, slot, count);
    }


    @Override
    public DotaAttributeInstance getAttribute(DotaAttribute type) {
        return attributes.get(type);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("attributes", NbtElement.COMPOUND_TYPE)) {
            NbtCompound attrsTag = tag.getCompound("attributes");
            for (var entry : attributes.entrySet()) {
                DotaAttribute type = entry.getKey();
                DotaAttributeInstance attribute = entry.getValue();

                if (attrsTag.contains(type.getCustomId(), NbtElement.DOUBLE_TYPE)) {
                    attribute.set(attrsTag.getDouble(type.getCustomId()));
                }
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtCompound attrsTag = new NbtCompound();
        for (Map.Entry<DotaAttribute, DotaAttributeInstance> entry : attributes.entrySet()) {
            attrsTag.putDouble(entry.getKey().getCustomId(), entry.getValue().getBase());
        }
        tag.put("attributes", attrsTag);
    }
}
