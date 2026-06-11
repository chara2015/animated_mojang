package com.mojang.blaze3d.resource;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/resource/RenderTargetDescriptor.class */
public final class RenderTargetDescriptor extends Record implements ResourceDescriptor<RenderTarget> {
    private final int width;
    private final int height;
    private final boolean useDepth;
    private final int clearColor;

    public RenderTargetDescriptor(int $$0, int $$1, boolean $$2, int $$3) {
        this.width = $$0;
        this.height = $$1;
        this.useDepth = $$2;
        this.clearColor = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RenderTargetDescriptor.class), RenderTargetDescriptor.class, "width;height;useDepth;clearColor", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->width:I", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->height:I", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->useDepth:Z", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->clearColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RenderTargetDescriptor.class), RenderTargetDescriptor.class, "width;height;useDepth;clearColor", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->width:I", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->height:I", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->useDepth:Z", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->clearColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RenderTargetDescriptor.class, Object.class), RenderTargetDescriptor.class, "width;height;useDepth;clearColor", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->width:I", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->height:I", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->useDepth:Z", "FIELD:Lcom/mojang/blaze3d/resource/RenderTargetDescriptor;->clearColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public boolean useDepth() {
        return this.useDepth;
    }

    public int clearColor() {
        return this.clearColor;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.mojang.blaze3d.resource.ResourceDescriptor
    public RenderTarget allocate() {
        return new TextureTarget(null, this.width, this.height, this.useDepth);
    }

    @Override // com.mojang.blaze3d.resource.ResourceDescriptor
    public void prepare(RenderTarget $$0) {
        if (this.useDepth) {
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures($$0.getColorTexture(), this.clearColor, $$0.getDepthTexture(), 1.0d);
        } else {
            RenderSystem.getDevice().createCommandEncoder().clearColorTexture($$0.getColorTexture(), this.clearColor);
        }
    }

    @Override // com.mojang.blaze3d.resource.ResourceDescriptor
    public void free(RenderTarget $$0) {
        $$0.destroyBuffers();
    }

    @Override // com.mojang.blaze3d.resource.ResourceDescriptor
    public boolean canUsePhysicalResource(ResourceDescriptor<?> $$0) {
        if (!($$0 instanceof RenderTargetDescriptor)) {
            return false;
        }
        RenderTargetDescriptor $$1 = (RenderTargetDescriptor) $$0;
        return this.width == $$1.width && this.height == $$1.height && this.useDepth == $$1.useDepth;
    }
}
