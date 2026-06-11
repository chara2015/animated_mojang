package net.labymod.core.client.render.batch;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.models.Implements;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/batch/DefaultResourceRenderContext.class */
@Singleton
@Implements(ResourceRenderContext.class)
public class DefaultResourceRenderContext extends DefaultRenderContext<ResourceRenderContext> implements ResourceRenderContext {
    private static final float TEXTURE_PADDING = 0.025f;
    private final Laby3D laby3D = Laby.references().laby3D();
    private Supplier<Matrix4f> poseSupplier;
    private VertexConsumer consumer;
    private boolean batched;
    private boolean simple;

    @Inject
    public DefaultResourceRenderContext() {
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext begin(Stack stack) {
        this.poseSupplier = () -> {
            return stack.getProvider().getPose();
        };
        ResourceRenderContext consumer = begin(stack, (VertexConsumer) this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.POSITION_UV_COLOR));
        this.simple = true;
        return consumer;
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext begin(Stack stack, RenderState renderState) {
        this.poseSupplier = () -> {
            return stack.getProvider().getPose();
        };
        return begin(stack, (VertexConsumer) this.laby3D.begin(renderState.drawingMode(), renderState.vertexDescription()));
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext begin(Stack stack, VertexConsumer consumer) {
        this.poseSupplier = () -> {
            return stack.getProvider().getPose();
        };
        this.consumer = consumer;
        this.batched = false;
        return this;
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext begin(Matrix4f pose, VertexConsumer consumer) {
        this.poseSupplier = () -> {
            return pose;
        };
        this.consumer = consumer;
        this.batched = true;
        this.simple = false;
        return this;
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext blit(float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, float resolutionWidth, float resolutionHeight, float red, float green, float blue, float alpha) {
        return innerBlit(x, x + width, y, y + height, this.zOffset, maxU, maxV, minU, minV, resolutionWidth, resolutionHeight, red, green, blue, alpha);
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext blitSprite(TextureUV uv, int spriteWidth, int spriteHeight, int spriteX, int spriteY, int x, int y, int zOffset, int width, int height, int color) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        float red = colorFormat.normalizedRed(color);
        float green = colorFormat.normalizedGreen(color);
        float blue = colorFormat.normalizedBlue(color);
        float alpha = colorFormat.normalizedAlpha(color);
        return innerBlit(x, x + width, y, y + height, zOffset, uv.getU(spriteX / spriteWidth), uv.getU((spriteX + width) / spriteWidth), uv.getV(spriteY / spriteHeight), uv.getV((spriteY + height) / spriteHeight), red, green, blue, alpha);
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext directBlit(float x, float y, float width, float height, float offset, float red, float green, float blue, float alpha) {
        int lightCoords = Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
        Matrix4f pose = this.poseSupplier.get();
        addVertex(pose, x, y + height, offset, red, green, blue, alpha, 0.0f, height, lightCoords);
        addVertex(pose, x + width, y + height, offset, red, green, blue, alpha, width, height, lightCoords);
        addVertex(pose, x + width, y, offset, red, green, blue, alpha, width, 0.0f, lightCoords);
        addVertex(pose, x, y, offset, red, green, blue, alpha, 0.0f, 0.0f, lightCoords);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.ResourceRenderContext
    public ResourceRenderContext blitSprite(int x, int y, int width, int height, int offset, float minU, float minV, float maxU, float maxV, int color) {
        Matrix4f pose = this.poseSupplier.get();
        ColorFormat colorFormat = ColorFormat.ARGB32;
        float red = colorFormat.normalizedRed(color);
        float green = colorFormat.normalizedGreen(color);
        float blue = colorFormat.normalizedBlue(color);
        float alpha = colorFormat.normalizedAlpha(color);
        int lightCoords = Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
        addVertex(pose, x, y, offset, red, green, blue, alpha, minU, minV, lightCoords);
        addVertex(pose, x, y + height, offset, red, green, blue, alpha, minU, maxV, lightCoords);
        addVertex(pose, x + width, y + height, offset, red, green, blue, alpha, maxU, maxV, lightCoords);
        addVertex(pose, x + width, y, offset, red, green, blue, alpha, maxU, minV, lightCoords);
        return this;
    }

    private ResourceRenderContext innerBlit(float left, float right, float top, float bottom, float offset, float minU, float minV, float maxU, float maxV, float resolutionWidth, float resolutionHeight, float red, float green, float blue, float alpha) {
        float spriteX = maxU / resolutionWidth;
        float spriteWidth = (maxU + minU) / resolutionWidth;
        float spriteY = maxV / resolutionHeight;
        float spriteHeight = (maxV + minV) / resolutionHeight;
        return innerBlit(left, right, top, bottom, offset, spriteX, spriteWidth, spriteY, spriteHeight, red, green, blue, alpha);
    }

    private ResourceRenderContext innerBlit(float left, float right, float top, float bottom, float offset, float minU, float maxU, float minV, float maxV, float red, float green, float blue, float alpha) {
        Matrix4f pose = this.poseSupplier.get();
        RenderEnvironmentContext context = Laby.references().renderEnvironmentContext();
        int lightCoords = context.getPackedLightWithCondition();
        if (context.isScreenContext()) {
            left += TEXTURE_PADDING;
            top += TEXTURE_PADDING;
            right += TEXTURE_PADDING;
            bottom += TEXTURE_PADDING;
        }
        addVertex(pose, left, bottom, offset, red, green, blue, alpha, minU, maxV, lightCoords);
        addVertex(pose, right, bottom, offset, red, green, blue, alpha, maxU, maxV, lightCoords);
        addVertex(pose, right, top, offset, red, green, blue, alpha, maxU, minV, lightCoords);
        addVertex(pose, left, top, offset, red, green, blue, alpha, minU, minV, lightCoords);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RenderContext
    public RenderContext<ResourceRenderContext> uploadToBuffer(RenderState renderState) {
        verifyUploadState();
        BufferBuilder bufferBuilder = (BufferBuilder) this.consumer;
        drawImmediate(bufferBuilder, renderState);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RenderContext
    public RenderContext<ResourceRenderContext> uploadToBuffer(RenderState renderState, Consumer<CommandBuffer> commandConsumer) {
        verifyUploadState();
        BufferBuilder bufferBuilder = (BufferBuilder) this.consumer;
        drawImmediate(bufferBuilder, renderState, commandConsumer);
        return this;
    }

    private void verifyUploadState() {
        if (this.batched) {
            throw new IllegalStateException("The resource context is batched and cannot be uploaded to the buffer");
        }
    }

    private void addVertex(Matrix4f pose, float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int lightCoords) {
        if (this.simple) {
            this.consumer.addVertex(pose, x, y, z).setUv(u, v).setColor(red, green, blue, alpha);
        } else {
            this.consumer.addVertex(pose, x, y, z).setColor(red, green, blue, alpha).setUv(u, v).setPackedLight(lightCoords);
        }
    }
}
