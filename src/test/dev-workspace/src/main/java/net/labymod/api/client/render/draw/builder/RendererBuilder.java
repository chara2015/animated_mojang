package net.labymod.api.client.render.draw.builder;

import java.awt.Color;
import net.labymod.api.client.render.draw.builder.RendererBuilder;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/RendererBuilder.class */
public interface RendererBuilder<T extends RendererBuilder<T>> {
    T pos(float f, float f2);

    T color(int i);

    void validateBuilder();

    void resetBuilder();

    default T color(float f, float f2, float f3, float f4) {
        return (T) color((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f));
    }

    default T color(int i, int i2, int i3, int i4) {
        return (T) color(ColorFormat.ARGB32.pack(i, i2, i3, i4));
    }

    default T color(Color color) {
        return (T) color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
}
