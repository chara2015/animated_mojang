package net.labymod.v1_21_8.laby3d.pipeline.opengl;

import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.TextureFormat;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/laby3d/pipeline/opengl/Laby3DGlTexture.class */
public class Laby3DGlTexture extends fmk {
    private final DeviceTexture texture;

    public Laby3DGlTexture(DeviceTexture texture) {
        super(5, texture.label(), toFormat(texture), texture.width(), texture.height(), texture.layers(), 1, ((GlResource) texture).getId());
        this.texture = texture;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_8.laby3d.pipeline.opengl.Laby3DGlTexture$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/laby3d/pipeline/opengl/Laby3DGlTexture$1.class */
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

    private static TextureFormat toFormat(DeviceTexture texture) {
        DeviceTexture.Format format = texture.format();
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$textures$DeviceTexture$Format[format.ordinal()]) {
            case 1:
                return TextureFormat.RGBA8;
            case 2:
                return TextureFormat.RED8;
            case 3:
                return TextureFormat.RED8I;
            case 4:
                return TextureFormat.DEPTH32;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(format));
        }
    }

    @NotNull
    public String getLabel() {
        return this.texture.label();
    }

    public void setTextureFilter(FilterMode $$0, boolean $$1) {
    }

    public void setAddressMode(AddressMode $$0) {
    }

    public void c() {
    }

    public void b() {
    }

    public void setUseMipmaps(boolean $$0) {
    }

    public void setTextureFilter(FilterMode $$0, FilterMode $$1, boolean $$2) {
    }

    public void setAddressMode(AddressMode $$0, AddressMode $$1) {
    }

    public int a() {
        return super.a();
    }

    public void a(int $$0) {
        this.texture.flushChanges();
    }

    public int a(flz $$0, @Nullable GpuTexture $$1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isClosed() {
        return this.texture.isClosed();
    }

    public void close() {
        this.texture.close();
    }
}
