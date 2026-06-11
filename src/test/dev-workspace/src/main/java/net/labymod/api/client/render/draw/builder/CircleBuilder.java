package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.CircleBuilder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/CircleBuilder.class */
public interface CircleBuilder<T extends CircleBuilder<T>> extends RendererBuilder<T> {
    T donutRadius(float f, float f2);

    T partial(float f, float f2);

    default T radius(float f) {
        return (T) donutRadius(0.0f, f);
    }
}
