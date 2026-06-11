package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.ResourceBuilder;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.debug.Preconditions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/builder/DefaultResourceBuilder.class */
public class DefaultResourceBuilder<T extends ResourceBuilder<T>> extends DefaultRectangleBuilder<T> implements ResourceBuilder<T> {
    protected ResourceLocation resourceLocation;
    protected float resolutionWidth;
    protected boolean resolutionWidthChanged;
    protected float resolutionHeight;
    protected boolean resolutionHeightChanged;
    protected float spriteX;
    protected boolean spriteXChanged;
    protected float spriteY;
    protected float spriteWidth;
    protected float spriteHeight;
    protected float offset;
    protected RoundedGeometryBuilder.RoundedData data;
    protected boolean blur;

    protected DefaultResourceBuilder() {
        resetBuilder();
    }

    public T texture(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        return this;
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.api.client.render.draw.builder.RectangleBuilder
    public T rounded(float leftTopRadius, float rightTopRadius, float leftBottomRadius, float rightBottomRadius) {
        throw new UnsupportedOperationException("Rounded textures are not implemented yet");
    }

    @Override // net.labymod.api.client.render.draw.builder.ResourceBuilder
    public T sprite(float spriteX, float spriteY, float spriteWidth, float spriteHeight) {
        this.spriteX = spriteX;
        this.spriteXChanged = true;
        this.spriteY = spriteY;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.ResourceBuilder
    public T resolution(float resolutionWidth, float resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        if (resolutionWidth > 0.0f) {
            this.resolutionWidthChanged = true;
        }
        this.resolutionHeight = resolutionHeight;
        if (resolutionHeight > 0.0f) {
            this.resolutionHeightChanged = true;
        }
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.ResourceBuilder
    public T offset(float offset) {
        this.offset = offset;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.ResourceBuilder
    public T roundedData(RoundedGeometryBuilder.RoundedData data) {
        this.data = data;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.ResourceBuilder
    public T blur(boolean blur) {
        this.blur = blur;
        return this;
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.core.client.render.draw.builder.DefaultRendererBuilder, net.labymod.api.client.render.draw.builder.RendererBuilder
    public void validateBuilder() {
        super.validateBuilder();
        Preconditions.notNull(this.resourceLocation, "Missing resourceLocation (call texture())");
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.core.client.render.draw.builder.DefaultRendererBuilder, net.labymod.api.client.render.draw.builder.RendererBuilder
    public void resetBuilder() {
        super.resetBuilder();
        this.resolutionWidth = 0.0f;
        this.resolutionWidthChanged = false;
        this.resolutionHeight = 0.0f;
        this.resolutionHeightChanged = false;
        this.spriteX = 0.0f;
        this.spriteXChanged = false;
        this.spriteY = 0.0f;
        this.spriteWidth = 0.0f;
        this.spriteHeight = 0.0f;
        this.offset = 0.0f;
        this.data = null;
        this.blur = false;
    }
}
