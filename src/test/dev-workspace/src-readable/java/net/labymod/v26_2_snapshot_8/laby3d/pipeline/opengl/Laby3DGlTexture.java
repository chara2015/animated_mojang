package net.labymod.v26_2_snapshot_8.laby3d.pipeline.opengl;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.opengl.FrameBufferCache;
import com.mojang.blaze3d.opengl.GlTexture;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTexture;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/pipeline/opengl/Laby3DGlTexture.class */
public class Laby3DGlTexture extends GlTexture {
    private final DeviceTexture texture;

    public Laby3DGlTexture(DeviceTexture texture, FrameBufferCache fboCache) {
        super(5, texture.label(), toFormat(texture), texture.width(), texture.height(), texture.layers(), 1, ((GlResource) texture).getId(), fboCache);
        this.texture = texture;
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.laby3d.pipeline.opengl.Laby3DGlTexture$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/pipeline/opengl/Laby3DGlTexture$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$textures$DeviceTexture$Format = new int[DeviceTexture.Format.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$DeviceTexture$Format[DeviceTexture.Format.R8G8B8A8_UNORM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$DeviceTexture$Format[DeviceTexture.Format.R8_UNORM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$DeviceTexture$Format[DeviceTexture.Format.S8_UINT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$DeviceTexture$Format[DeviceTexture.Format.D32_FLOAT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static GpuFormat toFormat(DeviceTexture texture) {
        DeviceTexture.Format format = texture.format();
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$textures$DeviceTexture$Format[format.ordinal()]) {
            case 1:
                return GpuFormat.RGBA8_UNORM;
            case 2:
                return GpuFormat.R8_UNORM;
            case 3:
                return GpuFormat.S8_UINT;
            case 4:
                return GpuFormat.D32_FLOAT;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(format));
        }
    }

    @NotNull
    public String getLabel() {
        return this.texture.label();
    }

    public void removeViews() {
    }

    public void addViews() {
    }

    public int glId() {
        return super.glId();
    }

    public boolean isClosed() {
        return this.texture.isClosed();
    }

    public void close() {
        this.texture.close();
    }
}
