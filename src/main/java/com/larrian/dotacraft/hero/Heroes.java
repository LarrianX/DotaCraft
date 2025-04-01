package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.hero.custom.Pudge;

import java.util.function.Function;

public enum Heroes {
    pudge(Pudge::new);

    private final DotaHero hero;

    Heroes(Function<String, DotaHero> constructor) {
        this.hero = constructor.apply(this.name());
    }

    public DotaHero getHero() {
        return hero;
    }
}
