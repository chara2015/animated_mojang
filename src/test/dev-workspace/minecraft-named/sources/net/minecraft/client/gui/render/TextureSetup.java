package net.minecraft.client.gui.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/TextureSetup.class */
public final class TextureSetup extends Record {
    private final GpuTextureView texure0;
    private final GpuTextureView texure1;
    private final GpuTextureView texure2;
    private final GpuSampler sampler0;
    private final GpuSampler sampler1;
    private final GpuSampler sampler2;
    private static final TextureSetup NO_TEXTURE_SETUP = new TextureSetup(null, null, null, null, null, null);
    private static int sortKeySeed;

    public TextureSetup(GpuTextureView $$0, GpuTextureView $$1, GpuTextureView $$2, GpuSampler $$3, GpuSampler $$4, GpuSampler $$5) {
        this.texure0 = $$0;
        this.texure1 = $$1;
        this.texure2 = $$2;
        this.sampler0 = $$3;
        this.sampler1 = $$4;
        this.sampler2 = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextureSetup.class), TextureSetup.class, "texure0;texure1;texure2;sampler0;sampler1;sampler2", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure0:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure1:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure2:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler0:Lcom/mojang/blaze3d/textures/GpuSampler;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler1:Lcom/mojang/blaze3d/textures/GpuSampler;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler2:Lcom/mojang/blaze3d/textures/GpuSampler;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextureSetup.class), TextureSetup.class, "texure0;texure1;texure2;sampler0;sampler1;sampler2", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure0:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure1:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure2:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler0:Lcom/mojang/blaze3d/textures/GpuSampler;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler1:Lcom/mojang/blaze3d/textures/GpuSampler;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler2:Lcom/mojang/blaze3d/textures/GpuSampler;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextureSetup.class, Object.class), TextureSetup.class, "texure0;texure1;texure2;sampler0;sampler1;sampler2", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure0:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure1:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->texure2:Lcom/mojang/blaze3d/textures/GpuTextureView;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler0:Lcom/mojang/blaze3d/textures/GpuSampler;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler1:Lcom/mojang/blaze3d/textures/GpuSampler;", "FIELD:Lnet/minecraft/client/gui/render/TextureSetup;->sampler2:Lcom/mojang/blaze3d/textures/GpuSampler;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public GpuTextureView texure0() {
        return this.texure0;
    }

    public GpuTextureView texure1() {
        return this.texure1;
    }

    public GpuTextureView texure2() {
        return this.texure2;
    }

    public GpuSampler sampler0() {
        return this.sampler0;
    }

    public GpuSampler sampler1() {
        return this.sampler1;
    }

    public GpuSampler sampler2() {
        return this.sampler2;
    }

    public static TextureSetup singleTexture(GpuTextureView $$0, GpuSampler $$1) {
        return new TextureSetup($$0, null, null, $$1, null, null);
    }

    public static TextureSetup singleTextureWithLightmap(GpuTextureView $$0, GpuSampler $$1) {
        return new TextureSetup($$0, null, Minecraft.getInstance().gameRenderer.lightTexture().getTextureView(), $$1, null, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
    }

    public static TextureSetup doubleTexture(GpuTextureView $$0, GpuSampler $$1, GpuTextureView $$2, GpuSampler $$3) {
        return new TextureSetup($$0, $$2, null, $$1, $$3, null);
    }

    public static TextureSetup noTexture() {
        return NO_TEXTURE_SETUP;
    }

    public int getSortKey() {
        return SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER ? hashCode() * (sortKeySeed + 1) : hashCode();
    }

    public static void updateSortKeySeed() {
        sortKeySeed = Math.round(100000.0f * ((float) Math.random()));
    }
}
