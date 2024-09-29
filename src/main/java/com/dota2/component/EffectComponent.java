package com.dota2.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

import java.util.Map;

public interface EffectComponent extends ComponentV3 {
    Map<String, Double> getAmplifiers();

    void sync();
}
