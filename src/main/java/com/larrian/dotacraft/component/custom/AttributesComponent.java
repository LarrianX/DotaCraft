package com.larrian.dotacraft.component.custom;

import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.attribute.DotaAttributeInstance;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

public interface AttributesComponent extends ComponentV3, ServerTickingComponent, ClientTickingComponent {
    DotaAttributeInstance getAttribute(DotaAttribute type);
    void sync();

}
