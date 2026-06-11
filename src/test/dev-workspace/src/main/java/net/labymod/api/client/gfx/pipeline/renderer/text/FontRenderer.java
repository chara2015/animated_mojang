package net.labymod.api.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FontRenderer.class */
public interface FontRenderer {
    void render(Matrix4f matrix4f, String str, float f, float f2, int i, int i2, int i3, int i4);

    void render(Matrix4f matrix4f, Component component, float f, float f2, int i, int i2, int i3, int i4);

    void render(Matrix4f matrix4f, FormattedTextLayout formattedTextLayout, float f, float f2, int i, int i2, int i3, int i4);

    TextState prepareText(FormattedTextLayout formattedTextLayout, float f, float f2, int i, int i2, int i3, int i4);

    float getWidth(String str);

    float getWidth(Component component);

    float getWidth(FormattedTextLayout formattedTextLayout);

    float getLineHeight();

    String trimTextToWidth(String str, float f, StringStart stringStart);

    float getWidth(int i, Style style);

    default String trimTextToWidth(String text, float maxWidth) {
        return trimTextToWidth(text, maxWidth, StringStart.LEFT);
    }

    @Nullable
    default <V> V getValue(@NotNull ResourceLocation key) {
        return null;
    }

    default <V> V getValue(@NotNull ResourceLocation key, V defaultValue) {
        return defaultValue;
    }

    default <V> void setValue(@NotNull ResourceLocation key, @Nullable V value) {
    }
}
