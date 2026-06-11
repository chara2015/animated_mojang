package net.labymod.api.property;

import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/property/ClampFloatPropertyConvention.class */
public class ClampFloatPropertyConvention implements PropertyConvention<Float> {
    private final float minimum;
    private final float maximum;

    public ClampFloatPropertyConvention(float minimum, float maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override // net.labymod.api.property.PropertyConvention
    public Float convention(Float value) {
        return Float.valueOf(MathHelper.clamp(value.floatValue(), this.minimum, this.maximum));
    }
}
