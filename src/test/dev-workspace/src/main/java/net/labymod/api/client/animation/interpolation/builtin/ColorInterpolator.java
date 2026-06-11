package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.TypeInterpolator;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/ColorInterpolator.class */
public class ColorInterpolator implements TypeInterpolator<Color> {
    public static final ColorInterpolator INSTANCE = new ColorInterpolator();

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public Color interpolate(Color from, Color to, double t, Color result) {
        int r = (int) Math.round(((double) from.getRed()) + (((double) (to.getRed() - from.getRed())) * t));
        int g = (int) Math.round(((double) from.getGreen()) + (((double) (to.getGreen() - from.getGreen())) * t));
        int b = (int) Math.round(((double) from.getBlue()) + (((double) (to.getBlue() - from.getBlue())) * t));
        int a = (int) Math.round(((double) from.getAlpha()) + (((double) (to.getAlpha() - from.getAlpha())) * t));
        return Color.ofRGB(r, g, b, a);
    }
}
