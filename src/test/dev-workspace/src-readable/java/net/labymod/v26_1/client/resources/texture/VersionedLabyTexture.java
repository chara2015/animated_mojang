package net.labymod.v26_1.client.resources.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.textures.SamplerDescription;
import net.labymod.v26_1.laby3d.pipeline.opengl.Laby3DGlTexture;
import net.labymod.v26_1.laby3d.pipeline.opengl.Laby3DGlTextureView;
import net.minecraft.client.renderer.texture.AbstractTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/resources/texture/VersionedLabyTexture.class */
public class VersionedLabyTexture extends AbstractTexture implements Texture {
    private final LabyTexture delegate;
    private Laby3DGlTexture gameGlTexture;
    private Laby3DGlTextureView gameGlTextureView;
    private boolean samplerCreated;

    public VersionedLabyTexture(LabyTexture delegate) {
        this.delegate = delegate;
    }

    public void close() {
        this.delegate.release();
    }

    public GpuTexture getTexture() {
        return (GpuTexture) CastUtil.requireInstanceOf(getGameGlTexture(), GpuTexture.class);
    }

    public GpuTextureView getTextureView() {
        return (GpuTextureView) CastUtil.requireInstanceOf(getGameGlTextureView(), GpuTextureView.class);
    }

    public LabyTexture getDelegate() {
        return this.delegate;
    }

    public GpuSampler getSampler() {
        if (!this.samplerCreated) {
            this.samplerCreated = true;
            SamplerDescription description = this.delegate.deviceTexture().samplerDescription();
            this.sampler = RenderSystem.getSamplerCache().getSampler(toAddressMode(description.addressU()), toAddressMode(description.addressV()), toFilterMode(description.minFilter()), toFilterMode(description.magFilter()), description.mipLevels() > 1);
        }
        return super.getSampler();
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        return (DeviceTexture) CastUtil.requireInstanceOf(getTexture(), DeviceTexture.class);
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return (DeviceTextureView) CastUtil.requireInstanceOf(getTextureView(), DeviceTextureView.class);
    }

    private Laby3DGlTexture getGameGlTexture() {
        if (this.gameGlTexture == null) {
            this.gameGlTexture = new Laby3DGlTexture(this.delegate.deviceTexture());
        }
        return this.gameGlTexture;
    }

    private Laby3DGlTextureView getGameGlTextureView() {
        if (this.gameGlTextureView == null) {
            this.gameGlTextureView = new Laby3DGlTextureView(getGameGlTexture());
        }
        return this.gameGlTextureView;
    }

    private AddressMode toAddressMode(SamplerDescription.AddressMode mode) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode[mode.ordinal()]) {
            case 1:
                return AddressMode.CLAMP_TO_EDGE;
            case 2:
                return AddressMode.REPEAT;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(mode));
        }
    }

    /* JADX INFO: renamed from: net.labymod.v26_1.client.resources.texture.VersionedLabyTexture$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/resources/texture/VersionedLabyTexture$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter = new int[SamplerDescription.Filter.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter[SamplerDescription.Filter.NEAREST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter[SamplerDescription.Filter.LINEAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode = new int[SamplerDescription.AddressMode.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode[SamplerDescription.AddressMode.CLAMP.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode[SamplerDescription.AddressMode.WRAP.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private FilterMode toFilterMode(SamplerDescription.Filter filter) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter[filter.ordinal()]) {
            case 1:
                return FilterMode.NEAREST;
            case 2:
                return FilterMode.LINEAR;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
