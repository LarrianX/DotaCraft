package com.larrian.dotacraft.item;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.item.custom.*;
import com.larrian.dotacraft.item.rune.RuneDoubleDamageItem;
import com.larrian.dotacraft.item.rune.RuneInvisibilityItem;
import com.larrian.dotacraft.item.rune.RuneRegenerationItem;
import com.larrian.dotacraft.item.rune.RuneSpeedItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class ModItems {

    // List of all items
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final FlaskItem FLASK = register(new FlaskItem());
    public static final ClarityItem CLARITY = register(new ClarityItem());
    public static final CrystalysItem CRYSTALYS = register(new CrystalysItem());
    public static final MangoItem MANGO = register(new MangoItem());
    public static final DaedalusItem DAEDALUS = register(new DaedalusItem());
    public static final ScepterItem SCEPTER = register(new ScepterItem());
    public static final BattleFuryItem BATTLE_FURY = register(new BattleFuryItem());
    public static final TpScrollItem TP_SCROLL = register(new TpScrollItem());
    public static final ShadowBladeItem SHADOW_BLADE = register(new ShadowBladeItem());
    public static final RadianceItem RADIANCE = register(new RadianceItem());
    public static final DesolatorItem DESOLATOR = register(new DesolatorItem());
    public static final SkullBasherItem SKULL_BASHER = register(new SkullBasherItem());
    public static final BottleItem BOTTLE = register(new BottleItem());
    public static final TangoItem TANGO = register(new TangoItem());
    public static final BootsOfSpeedItem BOOTS_OF_SPEED = register(new BootsOfSpeedItem());
    public static final DemonEdgeItem DEMON_EDGE = register(new DemonEdgeItem());
    public static final RecDaedalusItem REC_DAEDALUS = register(new RecDaedalusItem());
    public static final TangoTFItem TANGO_TF = register(new TangoTFItem());
    public static final RuneSpeedItem RUNE_SPEED = register(new RuneSpeedItem());
    public static final RuneDoubleDamageItem RUNE_DOUBLE_DAMAGE = register(new RuneDoubleDamageItem());
    public static final RuneInvisibilityItem RUNE_INVISIBILITY = register(new RuneInvisibilityItem());
    public static final RuneRegenerationItem RUNE_REGENERATION = register(new RuneRegenerationItem());
    public static final PhaseBootsItem PHASE_BOOTS = register(new PhaseBootsItem());
    public static final HeavenHalberdItem HEAVEN_HALBERD = register(new HeavenHalberdItem());

    private static <T extends DotaItem> T register(T item) {
        String id = item.getCustomId();
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, id), item);
    }

    public static void registerModItems() {}
}
