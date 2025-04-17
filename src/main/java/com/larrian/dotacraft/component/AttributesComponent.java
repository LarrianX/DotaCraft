package com.larrian.dotacraft.component;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.dota.DotaAttributeInstance;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

public interface AttributesComponent extends ComponentV3, ServerTickingComponent, ClientTickingComponent {
    DotaAttributeInstance getAttribute(DotaAttribute type);
    void sync();

}
