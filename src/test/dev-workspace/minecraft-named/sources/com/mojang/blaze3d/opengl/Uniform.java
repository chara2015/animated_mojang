package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.textures.TextureFormat;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/Uniform.class */
public interface Uniform extends AutoCloseable {
    @Override // java.lang.AutoCloseable
    default void close() {
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/Uniform$Ubo.class */
    public static final class Ubo extends Record implements Uniform {
        private final int blockBinding;

        public Ubo(int $$0) {
            this.blockBinding = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Ubo.class), Ubo.class, "blockBinding", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Ubo;->blockBinding:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Ubo.class), Ubo.class, "blockBinding", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Ubo;->blockBinding:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Ubo.class, Object.class), Ubo.class, "blockBinding", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Ubo;->blockBinding:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int blockBinding() {
            return this.blockBinding;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/Uniform$Utb.class */
    public static final class Utb extends Record implements Uniform {
        private final int location;
        private final int samplerIndex;
        private final TextureFormat format;
        private final int texture;

        public Utb(int $$0, int $$1, TextureFormat $$2, int $$3) {
            this.location = $$0;
            this.samplerIndex = $$1;
            this.format = $$2;
            this.texture = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Utb.class), Utb.class, "location;samplerIndex;format;texture", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->location:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->samplerIndex:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->format:Lcom/mojang/blaze3d/textures/TextureFormat;", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->texture:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Utb.class), Utb.class, "location;samplerIndex;format;texture", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->location:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->samplerIndex:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->format:Lcom/mojang/blaze3d/textures/TextureFormat;", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->texture:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Utb.class, Object.class), Utb.class, "location;samplerIndex;format;texture", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->location:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->samplerIndex:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->format:Lcom/mojang/blaze3d/textures/TextureFormat;", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Utb;->texture:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int location() {
            return this.location;
        }

        public int samplerIndex() {
            return this.samplerIndex;
        }

        public TextureFormat format() {
            return this.format;
        }

        public int texture() {
            return this.texture;
        }

        public Utb(int $$0, int $$1, TextureFormat $$2) {
            this($$0, $$1, $$2, GlStateManager._genTexture());
        }

        @Override // com.mojang.blaze3d.opengl.Uniform, java.lang.AutoCloseable
        public void close() {
            GlStateManager._deleteTexture(this.texture);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/Uniform$Sampler.class */
    public static final class Sampler extends Record implements Uniform {
        private final int location;
        private final int samplerIndex;

        public Sampler(int $$0, int $$1) {
            this.location = $$0;
            this.samplerIndex = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Sampler.class), Sampler.class, "location;samplerIndex", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Sampler;->location:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Sampler;->samplerIndex:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Sampler.class), Sampler.class, "location;samplerIndex", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Sampler;->location:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Sampler;->samplerIndex:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Sampler.class, Object.class), Sampler.class, "location;samplerIndex", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Sampler;->location:I", "FIELD:Lcom/mojang/blaze3d/opengl/Uniform$Sampler;->samplerIndex:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int location() {
            return this.location;
        }

        public int samplerIndex() {
            return this.samplerIndex;
        }
    }
}
