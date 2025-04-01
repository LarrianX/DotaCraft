
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import static com.larrian.dotacraft.init.ModEffects.RUNE_REGENERATION_EFFECT;

import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public class RegenerationHealthAttribute extends DotaAttribute {

    public RegenerationHealthAttribute(PlayerEntity provider, AttributesComponent attributes) {
        super(provider, attributes);
    }

    @Override
    public double get() {
        double baseCalc = super.get();
        double regenFromStrength = attributes.getAttribute(DotaAttributes.STRENGTH).get() * 0.09;
        double regenFromHealth = provider.hasStatusEffect(RUNE_REGENERATION_EFFECT)
                ? attributes.getAttribute(DotaAttributes.MAX_HEALTH).get() * 0.06 : 0;
        return Math.max(0, baseCalc + regenFromStrength + regenFromHealth);
    }
}
