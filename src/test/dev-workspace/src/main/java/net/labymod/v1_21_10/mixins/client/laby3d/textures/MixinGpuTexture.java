package net.labymod.v1_21_10.mixins.client.laby3d.textures;

import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.TextureFormat;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/laby3d/textures/MixinGpuTexture.class */
@Mixin({GpuTexture.class})
@Implements({@Interface(iface = DeviceTexture.class, prefix = "laby3d$")})
public abstract class MixinGpuTexture implements DeviceTexture {

    @Shadow
    @Final
    private int width;

    @Shadow
    @Final
    private int height;

    @Shadow
    @Final
    private int depthOrLayers;

    @Shadow
    public abstract boolean isClosed();

    @Shadow
    public abstract void close();

    @Shadow
    public abstract TextureFormat getFormat();

    @Shadow
    public abstract int usage();

    @Shadow
    public abstract String getLabel();

    public SamplerDescription samplerDescription() {
        throw new UnsupportedOperationException("The sampler description of a GPU texture is not supported.");
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_10.mixins.client.laby3d.textures.MixinGpuTexture$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/laby3d/textures/MixinGpuTexture$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mojang$blaze3d$textures$TextureFormat = new int[TextureFormat.values().length];

        static {
            try {
                $SwitchMap$com$mojang$blaze3d$textures$TextureFormat[TextureFormat.RGBA8.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$textures$TextureFormat[TextureFormat.RED8.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$textures$TextureFormat[TextureFormat.RED8I.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$textures$TextureFormat[TextureFormat.DEPTH32.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public DeviceTexture.Format format() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$com$mojang$blaze3d$textures$TextureFormat[getFormat().ordinal()]) {
            case 1:
                return DeviceTexture.Format.R8G8B8A8_UNORM;
            case 2:
                return DeviceTexture.Format.R8_UNORM;
            case 3:
                return DeviceTexture.Format.S8_UINT;
            case 4:
                return DeviceTexture.Format.D32_FLOAT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public DeviceTexture.Dimension dimension() {
        return DeviceTexture.Dimension.TEXTURE_2D;
    }

    public int width(int level) {
        return this.width >> level;
    }

    public int height(int level) {
        return this.height >> level;
    }

    public int layers() {
        return this.depthOrLayers;
    }

    @Intrinsic
    public boolean laby3d$isClosed() {
        return isClosed();
    }

    @Intrinsic
    public void laby3d$close() {
        close();
    }

    @NotNull
    public String label() {
        String label = getLabel();
        return label == null ? "unnamed" : label;
    }
}
