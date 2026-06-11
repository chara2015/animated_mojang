package net.labymod.api.client.render.batch;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.client.render.GraphicsColor;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/batch/ResourceRenderContext.class */
@Referenceable
public interface ResourceRenderContext extends RenderContext<ResourceRenderContext> {
    public static final AtlasRenderer ATLAS_RENDERER = new AtlasRenderer();

    ResourceRenderContext begin(Stack stack);

    ResourceRenderContext begin(Stack stack, RenderState renderState);

    ResourceRenderContext begin(Stack stack, VertexConsumer vertexConsumer);

    ResourceRenderContext begin(Matrix4f matrix4f, VertexConsumer vertexConsumer);

    ResourceRenderContext blit(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14);

    ResourceRenderContext blitSprite(TextureUV textureUV, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

    ResourceRenderContext directBlit(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9);

    ResourceRenderContext blitSprite(int i, int i2, int i3, int i4, int i5, float f, float f2, float f3, float f4, int i6);

    default ResourceRenderContext blit(float x, float y, float minU, float minV, float maxU, float maxV) {
        return blit(x, y, maxU, maxV, minU, minV, maxU, maxV, 256.0f, 256.0f, -1);
    }

    default ResourceRenderContext blit(float x, float y, float minU, float minV, float maxU, float maxV, int color) {
        return blit(x, y, maxU, maxV, minU, minV, maxU, maxV, 256.0f, 256.0f, color);
    }

    default ResourceRenderContext blit(float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, float resolutionWidth, float resolutionHeight, int color) {
        GraphicsColor graphicsColor = GraphicsColor.DEFAULT_COLOR.update(color);
        return blit(x, y, width, height, minU, minV, maxU, maxV, resolutionWidth, resolutionHeight, graphicsColor.red(), graphicsColor.green(), graphicsColor.blue(), graphicsColor.alpha());
    }

    default ResourceRenderContext directBlit(float x, float y, float width, float height, float offset, int color) {
        GraphicsColor graphicsColor = GraphicsColor.DEFAULT_COLOR.update(color);
        return directBlit(x, y, width, height, offset, graphicsColor.red(), graphicsColor.green(), graphicsColor.blue(), graphicsColor.alpha());
    }

    default ResourceRenderContext uploadToBuffer() {
        uploadToBuffer(RenderStates.GUI_TEXTURED);
        return this;
    }
}
