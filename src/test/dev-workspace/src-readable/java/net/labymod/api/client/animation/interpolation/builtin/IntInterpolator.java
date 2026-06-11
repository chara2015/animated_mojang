package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.TypeInterpolator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/IntInterpolator.class */
public class IntInterpolator implements TypeInterpolator<Integer> {
    public static final IntInterpolator INSTANCE = new IntInterpolator();

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public Integer interpolate(Integer from, Integer to, double t, Integer result) {
        return Integer.valueOf((int) Math.round(((double) from.intValue()) + (((double) (to.intValue() - from.intValue())) * t)));
    }
}
