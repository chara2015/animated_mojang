package net.labymod.v1_8_9.laby3d;

import javax.inject.Singleton;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.api.models.Implements;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.shaders.ShaderConfiguration;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/laby3d/VersionedLaby3D.class */
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
    public void setupOverlayAndLightingTextures() {
        bfk entityRenderer = ave.A().o;
        ShaderTextures.setShaderTexture(1, UnknownGlDeviceTextureView.ofUnknown(bjo.e.b()));
        ShaderTextures.setShaderTexture(2, TextureViewProvider.cast(entityRenderer).getDeviceTextureView());
    }

    @Override // net.labymod.api.laby3d.Laby3D
    public void setupOverlayAndLightingTextures(@NotNull CommandBuffer cmd) {
        cmd.bindTexture(1, overlayTextureView());
        cmd.bindTexture(2, lightTextureView());
    }

    @Override // net.labymod.api.laby3d.Laby3D
    @NotNull
    public DeviceTextureView overlayTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(bjo.e.b());
    }

    @Override // net.labymod.api.laby3d.Laby3D
    @NotNull
    public DeviceTextureView lightTextureView() {
        return TextureViewProvider.cast(ave.A().o).getDeviceTextureView();
    }
}
