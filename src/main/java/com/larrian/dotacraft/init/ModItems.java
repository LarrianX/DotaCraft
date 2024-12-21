package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.item.custom.*;
import com.larrian.dotacraft.item.rune.RuneDoubleDamageItem;
import com.larrian.dotacraft.item.rune.RuneInvisibilityItem;
import com.larrian.dotacraft.item.rune.RuneSpeedItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final FlaskItem FLASK = new FlaskItem();
    public static final ClarityItem CLARITY = new ClarityItem();
    public static final CrystalysItem CRYSTALYS = new CrystalysItem();
    public static final MangoItem MANGO = new MangoItem();
    public static final DaedalusItem DAEDALUS = new DaedalusItem();
    public static final ScepterItem SCEPTER = new ScepterItem();
    public static final BattleFuryItem BATTLE_FURY = new BattleFuryItem();
    public static final TpScrollItem TP_SCROLL = new TpScrollItem();
    public static final ShadowBladeItem SHADOW_BLADE = new ShadowBladeItem();
    public static final RadianceItem RADIANCE = new RadianceItem();
    public static final BottleItem BOTTLE = new BottleItem();
    public static final TangoItem TANGO = new TangoItem();
    public static final BootsOfSpeedItem BOOTS_OF_SPEED = new BootsOfSpeedItem();
    public static final DemonEdgeItem DEMON_EDGE = new DemonEdgeItem();
    public static final RecDaedalusItem REC_DAEDALUS = new RecDaedalusItem();
    public static final TangoTFItem TANGO_TF = new TangoTFItem();
    public static final RuneSpeedItem RUNE_SPEED = new RuneSpeedItem();
    public static final RuneDoubleDamageItem RUNE_DOUBLE_DAMAGE = new RuneDoubleDamageItem();
    public static final RuneInvisibilityItem RUNE_INVISIBILITY = new RuneInvisibilityItem();
    public static final PhaseBootsItem PHASE_BOOTS = new PhaseBootsItem();

    public static final Item[] ITEMS = {
            FLASK,
            CLARITY,
            CRYSTALYS,
            MANGO,
            DAEDALUS,
            SCEPTER,
            BATTLE_FURY,
            BOOTS_OF_SPEED,
            BOTTLE,
            TANGO,
            TP_SCROLL,
            SHADOW_BLADE,
            RADIANCE,
            DEMON_EDGE,
            REC_DAEDALUS,
            TANGO_TF,
            RUNE_SPEED,
            RUNE_DOUBLE_DAMAGE,
            RUNE_INVISIBILITY,
            PHASE_BOOTS
    };

    private static void registerItems() {
        for (Item item : ITEMS) {
            if (item instanceof Custom) {
                registerItem(((Custom) item).getId(), item);
            }
        }
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, name), item);
    }

    public static void registerModItems() {
        DotaCraft.LOGGER.info("Registering Mod Items for " + DotaCraft.MOD_ID);
        registerItems();
    }
}