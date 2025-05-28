package com.larrian.dotacraft.hero.tick;

public interface CommonTickingHero extends ServerTickingHero, ClientTickingHero{
    @Override
    default void serverTick() {
        this.tick();
    }

    @Override
    default void clientTick() {
        this.tick();
    }

    void tick();
}
