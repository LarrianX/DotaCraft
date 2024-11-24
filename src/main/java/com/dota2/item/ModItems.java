package com.dota2.item;

import com.dota2.DotaCraft;
import com.dota2.item.rune.RuneSpeed;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Flask FLASK = new Flask();
    public static final Clarity CLARITY = new Clarity();
    public static final Crystalys CRYSTALYS = new Crystalys();
    public static final Mango MANGO = new Mango();
    public static final Daedalus DAEDALUS = new Daedalus();
    public static final Scepter SCEPTER = new Scepter();
    public static final BattleFury BATTLE_FURY = new BattleFury();
    public static final TpScroll TPSCROLL = new TpScroll();
    public static final ShadowBlade SHADOWBLADE = new ShadowBlade();
    public static final Radiance RADIANCE = new Radiance();
    public static final Bottle BOTTLE = new Bottle();
    public static final Tango TANGO = new Tango();
    public static final BootsOfSpeed BOOTS_OF_SPEED = new BootsOfSpeed();
    public static final DemonEdge DEMON_EDGE = new DemonEdge();
    public static final RecDaedalus REC_DAEDALUS = new RecDaedalus();
    public static final TangoTF TANGO_TF = new TangoTF();
    public static final RuneSpeed RUNE_SPEED = new RuneSpeed();

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
            TPSCROLL,
            SHADOWBLADE,
            RADIANCE,
            DEMON_EDGE,
            REC_DAEDALUS,
            TANGO_TF,
            RUNE_SPEED
    };

    private static void registerItems() {
        for (Item item : ITEMS) {
            if (item instanceof CustomItem) {
                registerItem(((CustomItem) item).getId(), item);
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