package net.labymod.api.client.animation.interpolation;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Interpolatable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/InterpolatableTypeInterpolator.class */
public class InterpolatableTypeInterpolator<I extends Interpolatable<I>> implements TypeInterpolator<I> {
    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public I interpolate(I i, I i2, double d, I i3) {
        return (I) i2.interpolate(CubicBezier.LINEAR, i, 0L, 1000L, (long) (d * 1000.0d));
    }
}
