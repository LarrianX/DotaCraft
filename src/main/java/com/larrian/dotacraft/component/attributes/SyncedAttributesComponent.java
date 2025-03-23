package com.larrian.dotacraft.component.attributes;

import com.larrian.dotacraft.item.DotaItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class SyncedAttributesComponent implements AttributesComponent, AutoSyncedComponent {
    private static final int LEVEL_LIMIT = 30;
    public static final double LEVEL_BOOST = 2;

    private final PlayerEntity provider;
    private Map<Integer, ItemStack> cache;
    private int level;
    private final EnumMap<DotaAttributeType, IDotaAttribute> attributes = new EnumMap<>(DotaAttributeType.class);

    public SyncedAttributesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new HashMap<>();

        for (DotaAttributeType type : DotaAttributeType.values()) {
            attributes.put(type, new DotaAttribute(type, provider, this));
        }
    }

    @Override
    public void sync() {
        provider.syncComponent(ATTRIBUTES_COMPONENT);
    }

    @Override
    public void tick() {
        Map<Integer, ItemStack> current = mapInventory(provider.getInventory());

        if (!current.equals(this.cache)) {
            updateModifiers(cache, current);
            this.cache = current;
        }
    }

    private void updateModifiers(Map<Integer, ItemStack> oldItems, Map<Integer, ItemStack> newItems) {
        for (Map.Entry<Integer, ItemStack> entry : oldItems.entrySet()) {
            int slot = entry.getKey();
            ItemStack oldItem = entry.getValue();
            ItemStack newItem = newItems.get(slot);

            if (newItem == null || !ItemStack.areEqual(oldItem, newItem)) {
                removeModifiers((DotaItem) oldItem.getItem(), entry.getKey());
            }
        }

        for (Map.Entry<Integer, ItemStack> entry : newItems.entrySet()) {
            int slot = entry.getKey();
            ItemStack newItem = entry.getValue();
            ItemStack oldItem = oldItems.get(slot);

            if (oldItem == null || !ItemStack.areEqual(newItem, oldItem)) {
                if (newItem.getItem() instanceof DotaItem item) {
                    addModifiers(item, slot);
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

    private void addModifiers(DotaItem item, int slot) {
        item.addModifiers(this.attributes, slot);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(level, LEVEL_LIMIT));
    }

    @Override
    public void addLevel(int add) {
        setLevel(getLevel() + add);
    }

    @Override
    public DotaAttribute getAttribute(DotaAttributeType type) {
        return (DotaAttribute) attributes.get(type);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.level = tag.getInt("level");

        if (tag.contains("attributes", NbtElement.COMPOUND_TYPE)) {
            NbtCompound attrsTag = tag.getCompound("attributes");
            for (Map.Entry<DotaAttributeType, IDotaAttribute> entry : attributes.entrySet()) {
                if (attrsTag.contains(entry.getKey().name(), NbtElement.DOUBLE_TYPE)) {
                    entry.getValue().set(attrsTag.getDouble(entry.getKey().name()));
                }
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("level", this.level);

        NbtCompound attrsTag = new NbtCompound();
        for (Map.Entry<DotaAttributeType, IDotaAttribute> entry : attributes.entrySet()) {
            attrsTag.putDouble(entry.getKey().name(), entry.getValue().getBase());
        }
        tag.put("attributes", attrsTag);
    }
}
