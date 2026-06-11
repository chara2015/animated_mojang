package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/RectangleBuilder.class */
public interface RectangleBuilder<T extends RectangleBuilder<T>> extends RendererBuilder<T> {
    T size(float f, float f2);

    T rounded(float f, float f2, float f3, float f4);

    T lowerEdgeSoftness(float f);

    T upperEdgeSoftness(float f);

    T borderThickness(float f);

    T borderSoftness(float f);

    T borderColor(int i);

    T gradientHorizontal(int i, int i2);

    T gradientVertical(int i, int i2);

    default T pos(Rectangle rectangle) {
        return (T) pos(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom());
    }

    default T pos(float f, float f2, float f3, float f4) {
        return (T) pos(f, f2).size(f3 - f, f4 - f2);
    }

    default T size(float f) {
        return (T) size(f, f);
    }

    default T rounded(float f) {
        return (T) rounded(f, f, f, f);
    }

    default T gradientHorizontal(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return (T) gradientHorizontal((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f), (int) (f5 * 255.0f), (int) (f6 * 255.0f), (int) (f7 * 255.0f), (int) (f8 * 255.0f));
    }

    default T gradientHorizontal(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return (T) gradientHorizontal(ColorFormat.ARGB32.pack(i, i2, i3, i4), ColorFormat.ARGB32.pack(i5, i6, i7, i8));
    }

    default T gradientVertical(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return (T) gradientVertical((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f), (int) (f5 * 255.0f), (int) (f6 * 255.0f), (int) (f7 * 255.0f), (int) (f8 * 255.0f));
    }

    default T gradientVertical(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return (T) gradientVertical(ColorFormat.ARGB32.pack(i, i2, i3, i4), ColorFormat.ARGB32.pack(i5, i6, i7, i8));
    }
}
