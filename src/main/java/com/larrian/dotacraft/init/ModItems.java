package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.item.custom.BattleFuryItem;
import com.larrian.dotacraft.item.custom.BootsOfSpeedItem;
import com.larrian.dotacraft.item.custom.ClarityItem;
import com.larrian.dotacraft.item.custom.CrystalysItem;
import com.larrian.dotacraft.item.custom.DaedalusItem;
import com.larrian.dotacraft.item.custom.DemonEdgeItem;
import com.larrian.dotacraft.item.custom.FlaskItem;
import com.larrian.dotacraft.item.custom.MangoItem;
import com.larrian.dotacraft.item.custom.RecDaedalusItem;
import com.larrian.dotacraft.item.custom.ShadowBladeItem;
import com.larrian.dotacraft.item.custom.TpScrollItem;
import com.larrian.dotacraft.item.custom.TangoItem;
import com.larrian.dotacraft.item.custom.TangoTFItem;
import com.larrian.dotacraft.item.custom.PhaseBootsItem;
import com.larrian.dotacraft.item.custom.BottleItem;
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

    // Список всех зарегистрированных предметов
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final FlaskItem FLASK = register(new FlaskItem());
    public static final ClarityItem CLARITY = register(new ClarityItem());
    public static final CrystalysItem CRYSTALYS = register(new CrystalysItem());
    public static final MangoItem MANGO = register(new MangoItem());
    public static final DaedalusItem DAEDALUS = register(new DaedalusItem());
    public static final com.larrian.dotacraft.item.custom.ScepterItem SCEPTER = register(new com.larrian.dotacraft.item.custom.ScepterItem());
    public static final BattleFuryItem BATTLE_FURY = register(new BattleFuryItem());
    public static final TpScrollItem TP_SCROLL = register(new TpScrollItem());
    public static final ShadowBladeItem SHADOW_BLADE = register(new ShadowBladeItem());
    public static final com.larrian.dotacraft.item.custom.RadianceItem RADIANCE = register(new com.larrian.dotacraft.item.custom.RadianceItem());
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

    private static <T extends Item> T register(T item) {
        if (item instanceof Custom) {
            String id = ((Custom) item).getId();
            Registry.register(Registries.ITEM, new Identifier(DotaCraft.MOD_ID, id), item);
        }
        ITEMS.add(item);
        return item;
    }

    public static void registerModItems() {
        DotaCraft.LOGGER.info("Registering Mod Items for " + DotaCraft.MOD_ID);
        // Регистрация выполнена при инициализации статических полей
    }
}
