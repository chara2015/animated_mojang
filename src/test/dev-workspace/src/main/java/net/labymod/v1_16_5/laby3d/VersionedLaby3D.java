package net.labymod.v1_16_5.laby3d;

import javax.inject.Singleton;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.accessor.resource.texture.NativeImageAccessor;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.shaders.ShaderConfiguration;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_16_5.client.resources.texture.NativeGameImage;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/laby3d/VersionedLaby3D.class */
@Singleton
@Implements(Laby3D.class)
public class VersionedLaby3D extends Laby3D {
    public VersionedLaby3D(@NotNull GameTransformations gameTransformations) {
        super(gameTransformations);
    }

    @Override // net.labymod.api.laby3d.Laby3D
    @NotNull
    public RenderDevice createRenderDevice(@NotNull ShaderConfiguration shaderConfiguration) {
        return new VersionedGlRenderDevice(shaderConfiguration, DEFAULT_FLAGS);
    }

    @Override // net.labymod.api.laby3d.Laby3D
    public void writeToTexture(@NotNull DeviceTexture texture, @NotNull GameImage image) {
        if (image instanceof NativeGameImage) {
            NativeGameImage nativeGameImage = (NativeGameImage) image;
            NativeImageAccessor accessor = (NativeImageAccessor) CastUtil.requireInstanceOf(nativeGameImage.getNativeImage(), NativeImageAccessor.class);
            renderDevice().writeToTexture(texture, image.getWidth(), image.getHeight(), DeviceTexture.Format.R8G8B8A8_UNORM, accessor.getPointer());
            return;
        }
        super.writeToTexture(texture, image);
    }

    @Override // net.labymod.api.laby3d.Laby3D
    public void setupOverlayAndLightingTextures() {
        dzz gameRenderer = djz.C().h;
        gameRenderer.m().a();
        gameRenderer.l().c();
    }

    @Override // net.labymod.api.laby3d.Laby3D
    public void setupOverlayAndLightingTextures(@NotNull CommandBuffer cmd) {
        cmd.bindTexture(1, overlayTextureView());
        cmd.bindTexture(2, lightTextureView());
    }

    @Override // net.labymod.api.laby3d.Laby3D
    @NotNull
    public DeviceTextureView overlayTextureView() {
        dzz renderer = djz.C().h;
        return TextureViewProvider.cast(renderer.m()).getDeviceTextureView();
    }

    @Override // net.labymod.api.laby3d.Laby3D
    @NotNull
    public DeviceTextureView lightTextureView() {
        dzz renderer = djz.C().h;
        return TextureViewProvider.cast(renderer.l()).getDeviceTextureView();
    }
}
