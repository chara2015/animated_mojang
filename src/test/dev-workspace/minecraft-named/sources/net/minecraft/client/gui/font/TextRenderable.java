package net.minecraft.client.gui.font;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/TextRenderable.class */
public interface TextRenderable {
    void render(Matrix4f matrix4f, VertexConsumer vertexConsumer, int i, boolean z);

    RenderType renderType(Font.DisplayMode displayMode);

    GpuTextureView textureView();

    RenderPipeline guiPipeline();

    float left();

    float top();

    float right();

    float bottom();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/TextRenderable$Styled.class */
    public interface Styled extends ActiveArea, TextRenderable {
        @Override // net.minecraft.client.gui.font.ActiveArea
        default float activeLeft() {
            return left();
        }

        @Override // net.minecraft.client.gui.font.ActiveArea
        default float activeTop() {
            return top();
        }

        @Override // net.minecraft.client.gui.font.ActiveArea
        default float activeRight() {
            return right();
        }

        @Override // net.minecraft.client.gui.font.ActiveArea
        default float activeBottom() {
            return bottom();
        }
    }
}
