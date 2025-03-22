package com.larrian.dotacraft.component.attributes;

import com.larrian.dotacraft.item.DotaItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class SyncedAttributesComponent implements AttributesComponent, AutoSyncedComponent {
    private static final int LEVEL_LIMIT = 30;
    public static final double LEVEL_BOOST = 2;

    private final PlayerEntity provider;
    private Map<String, ItemStack> cache;
    private int level;
    private final EnumMap<DotaAttributeType, DotaAttribute> attributes = new EnumMap<>(DotaAttributeType.class);

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
        Map<String, ItemStack> current = mapInventory(provider.getInventory());

        if (!current.equals(this.cache)) {
            updateModifiers(cache, current);
            this.cache = current;
        }
    }

    private void updateModifiers(Map<String, ItemStack> oldItems, Map<String, ItemStack> newItems) {
        for (String id : oldItems.keySet()) {
            if (!newItems.containsKey(id)) {
                removeModifier(id);
            }
        }

        for (Map.Entry<String, ItemStack> entry : newItems.entrySet()) {
            String id = entry.getKey();
            ItemStack newItem = entry.getValue();
            ItemStack oldItem = oldItems.get(id);

            if (oldItem == null || !ItemStack.areEqual(oldItem, newItem)) {
                if (newItem.getItem() instanceof DotaItem item) {
                    addModifier(item);
                }
            }
        }
    }

    private Map<String, ItemStack> mapInventory(PlayerInventory inventory) {
        Map<String, ItemStack> map = new HashMap<>();
        for (ItemStack stack : inventory.main) {
            if (stack.getItem() instanceof DotaItem item) {
                map.put(item.getId(), stack);
            }
        }
        return map;
    }

    private void removeModifier(String id) {
        for (DotaAttribute attribute : this.attributes.values()) {
            attribute.removeModifier(id);
        }
    }

    private void addModifier(DotaItem item) {
        item.addModifiers(this.attributes);
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
        return attributes.get(type);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.level = tag.getInt("level");

        if (tag.contains("attributes", NbtElement.COMPOUND_TYPE)) {
            NbtCompound attrsTag = tag.getCompound("attributes");
            for (Map.Entry<DotaAttributeType, DotaAttribute> entry : attributes.entrySet()) {
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
        for (Map.Entry<DotaAttributeType, DotaAttribute> entry : attributes.entrySet()) {
            attrsTag.putDouble(entry.getKey().name(), entry.getValue().getBase());
        }
        tag.put("attributes", attrsTag);
    }
}
