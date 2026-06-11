package net.labymod.api.laby3d.foreign.target.opengl;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.Laby;
import net.labymod.api.laby3d.foreign.target.ForeignRenderTarget;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget.class */
public final class GlForeignRenderTarget extends ForeignRenderTarget implements GlResource {
    private final int id;
    private final Int2ObjectMap<Attachment> colors = new Int2ObjectOpenHashMap();
    private Attachment depth;
    private Attachment stencil;

    public GlForeignRenderTarget(int id) {
        this.id = id;
    }

    @Override // net.labymod.api.laby3d.foreign.target.ForeignRenderTarget
    public int width() {
        return Laby.labyAPI().minecraft().minecraftWindow().getRawWidth();
    }

    @Override // net.labymod.api.laby3d.foreign.target.ForeignRenderTarget
    public int height() {
        return Laby.labyAPI().minecraft().minecraftWindow().getRawHeight();
    }

    public int getId() {
        return this.id;
    }

    public void resize(int newWidth, int newHeight) {
    }

    @Nullable
    public DeviceTextureView findColorTexture(int index) {
        return null;
    }

    @Nullable
    public DeviceTextureView findDepthTexture() {
        return null;
    }

    public Int2ObjectMap<Attachment> colors() {
        return this.colors;
    }

    @Nullable
    public Attachment depth() {
        return this.depth;
    }

    @Nullable
    public Attachment stencil() {
        return this.stencil;
    }

    public void setAttachment(int attachment, int texTarget, int texId, int level, int layerOrFace) {
        Attachment.Texture tex = new Attachment.Texture(texId, texTarget, level, layerOrFace);
        if (attachment == 36096) {
            this.depth = tex;
            return;
        }
        if (attachment == 36128) {
            this.stencil = tex;
        } else if (attachment == 33306) {
            this.depth = tex;
            this.stencil = tex;
        } else {
            this.colors.put(attachment, tex);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment.class */
    public interface Attachment {

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture.class */
        public static final class Texture extends Record implements Attachment {
            private final int id;
            private final int target;
            private final int level;
            private final int layerOrFace;

            public Texture(int id, int target, int level, int layerOrFace) {
                this.id = id;
                this.target = target;
                this.level = level;
                this.layerOrFace = layerOrFace;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Texture.class), Texture.class, "id;target;level;layerOrFace", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->id:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->target:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->level:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->layerOrFace:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Texture.class), Texture.class, "id;target;level;layerOrFace", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->id:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->target:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->level:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->layerOrFace:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object o) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Texture.class, Object.class), Texture.class, "id;target;level;layerOrFace", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->id:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->target:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->level:I", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Texture;->layerOrFace:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
            }

            public int id() {
                return this.id;
            }

            public int target() {
                return this.target;
            }

            public int level() {
                return this.level;
            }

            public int layerOrFace() {
                return this.layerOrFace;
            }
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Rbo.class */
        public static final class Rbo extends Record implements Attachment {
            private final int id;

            public Rbo(int id) {
                this.id = id;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Rbo.class), Rbo.class, "id", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Rbo;->id:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Rbo.class), Rbo.class, "id", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Rbo;->id:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object o) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Rbo.class, Object.class), Rbo.class, "id", "FIELD:Lnet/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTarget$Attachment$Rbo;->id:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
            }

            public int id() {
                return this.id;
            }
        }
    }
}
