package com.larrian.dotacraft.component;

import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.attributes.IDotaAttribute;
import com.larrian.dotacraft.item.DotaItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

/* Component to synchronize player attributes. */
public class SyncedAttributesComponent implements AttributesComponent, AutoSyncedComponent {
    private static final int LEVEL_LIMIT = 30;
    public static final double LEVEL_BOOST = 2;

    private final PlayerEntity provider;
    private Map<Integer, ItemStack> cache;
    private int level;
    private final EnumMap<DotaAttributes, IDotaAttribute> attributes = new EnumMap<>(DotaAttributes.class);

    public SyncedAttributesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new HashMap<>();

        // Create attribute instances using enum registry
        for (DotaAttributes type : DotaAttributes.values()) {
            attributes.put(type, type.createAttribute(provider, this));
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
    public DotaAttribute getAttribute(DotaAttributes type) {
        return (DotaAttribute) attributes.get(type);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.level = tag.getInt("level");

        if (tag.contains("attributes", NbtElement.COMPOUND_TYPE)) {
            NbtCompound attrsTag = tag.getCompound("attributes");
            for (Map.Entry<DotaAttributes, IDotaAttribute> entry : attributes.entrySet()) {
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
        for (Map.Entry<DotaAttributes, IDotaAttribute> entry : attributes.entrySet()) {
            attrsTag.putDouble(entry.getKey().name(), entry.getValue().getBase());
        }
        tag.put("attributes", attrsTag);
    }
}
