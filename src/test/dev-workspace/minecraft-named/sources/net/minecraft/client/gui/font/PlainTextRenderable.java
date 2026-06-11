package net.minecraft.client.gui.font;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.font.TextRenderable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/PlainTextRenderable.class */
public interface PlainTextRenderable extends TextRenderable.Styled {
    public static final float DEFAULT_WIDTH = 8.0f;
    public static final float DEFAULT_HEIGHT = 8.0f;
    public static final float DEFUAULT_ASCENT = 8.0f;

    void renderSprite(Matrix4f matrix4f, VertexConsumer vertexConsumer, int i, float f, float f2, float f3, int i2);

    float x();

    float y();

    int color();

    int shadowColor();

    float shadowOffset();

    @Override // net.minecraft.client.gui.font.TextRenderable
    default void render(Matrix4f $$0, VertexConsumer $$1, int $$2, boolean $$3) {
        float $$4 = 0.0f;
        if (shadowColor() != 0) {
            renderSprite($$0, $$1, $$2, shadowOffset(), shadowOffset(), 0.0f, shadowColor());
            if (!$$3) {
                $$4 = 0.0f + 0.03f;
            }
        }
        renderSprite($$0, $$1, $$2, 0.0f, 0.0f, $$4, color());
    }

    default float width() {
        return 8.0f;
    }

    default float height() {
        return 8.0f;
    }

    default float ascent() {
        return 8.0f;
    }

    @Override // net.minecraft.client.gui.font.TextRenderable
    default float left() {
        return x();
    }

    @Override // net.minecraft.client.gui.font.TextRenderable
    default float right() {
        return left() + width();
    }

    @Override // net.minecraft.client.gui.font.TextRenderable
    default float top() {
        return (y() + 7.0f) - ascent();
    }

    @Override // net.minecraft.client.gui.font.TextRenderable
    default float bottom() {
        return activeTop() + height();
    }
}
