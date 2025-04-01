
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import static com.larrian.dotacraft.init.ModEffects.RUNE_DOUBLE_DAMAGE_EFFECT;

import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public class DamageAttribute extends DotaAttribute {

    public DamageAttribute(PlayerEntity provider, AttributesComponent attributes) {
        super(provider, attributes);
    }

    @Override
    public double get() {
        double baseCalc = super.get();
        if (provider.hasStatusEffect(RUNE_DOUBLE_DAMAGE_EFFECT)) {
            baseCalc *= 0.8;
        }
        return Math.max(0, baseCalc);
    }
}
