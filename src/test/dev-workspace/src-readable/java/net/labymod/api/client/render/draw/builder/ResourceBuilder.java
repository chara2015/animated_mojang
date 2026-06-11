package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.client.render.draw.builder.ResourceBuilder;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/ResourceBuilder.class */
public interface ResourceBuilder<T extends ResourceBuilder<T>> extends RectangleBuilder<T> {
    T texture(ResourceLocation resourceLocation);

    T sprite(float f, float f2, float f3, float f4);

    T resolution(float f, float f2);

    T offset(float f);

    T roundedData(RoundedGeometryBuilder.RoundedData roundedData);

    T blur(boolean z);

    default T sprite16(float f, float f2) {
        return (T) sprite(f, f2, 16.0f);
    }

    default T sprite32(float f, float f2) {
        return (T) sprite(f, f2, 32.0f);
    }

    default T sprite(float f, float f2, float f3) {
        return (T) sprite(f, f2, f3, f3);
    }

    default T sprite(Sprite sprite) {
        return (T) sprite(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }
}
