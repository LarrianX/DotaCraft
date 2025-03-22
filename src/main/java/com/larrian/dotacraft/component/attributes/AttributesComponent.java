package com.larrian.dotacraft.component.attributes;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;

public interface AttributesComponent extends ComponentV3, CommonTickingComponent {

    // Level methods
    int getLevel();
    void setLevel(int level);
    void addLevel(int level);

    DotaAttribute getAttribute(DotaAttributeType type);
    void sync();

}
