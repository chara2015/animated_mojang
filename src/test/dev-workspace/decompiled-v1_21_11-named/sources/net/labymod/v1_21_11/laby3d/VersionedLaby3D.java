package net.labymod.v1_21_11.laby3d;

import com.mojang.blaze3d.platform.NativeImage;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.shader.ShaderTextures;
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
import net.labymod.v1_21_11.client.resources.texture.NativeGameImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/VersionedLaby3D.class */
@Singleton
@Implements(Laby3D.class)
public class VersionedLaby3D extends Laby3D {
    public VersionedLaby3D(@NotNull GameTransformations gameTransformations) {
        super(gameTransformations);
    }

    @NotNull
    public RenderDevice createRenderDevice(@NotNull ShaderConfiguration shaderConfiguration) {
        return new VersionedGlRenderDevice(shaderConfiguration, DEFAULT_FLAGS);
    }

    public void setupOverlayAndLightingTextures() {
        ShaderTextures.setShaderTexture(1, overlayTextureView());
        ShaderTextures.setShaderTexture(2, lightTextureView());
    }

    public void setupOverlayAndLightingTextures(@NotNull CommandBuffer cmd) {
        GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
        cmd.bindTexture(1, TextureViewProvider.cast(gameRenderer.overlayTexture()).getDeviceTextureView());
        cmd.bindTexture(2, TextureViewProvider.cast(gameRenderer.lightTexture()).getDeviceTextureView());
    }

    @NotNull
    public DeviceTextureView overlayTextureView() {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        return TextureViewProvider.cast(renderer.overlayTexture()).getDeviceTextureView();
    }

    @NotNull
    public DeviceTextureView lightTextureView() {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        return TextureViewProvider.cast(renderer.lightTexture()).getDeviceTextureView();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void writeToTexture(@NotNull DeviceTexture texture, @NotNull GameImage image) throws MatchException {
        if (image instanceof NativeGameImage) {
            try {
                NativeImage nativeImage = ((NativeGameImage) image).image();
                NativeImageAccessor accessor = (NativeImageAccessor) CastUtil.requireInstanceOf(nativeImage, NativeImageAccessor.class);
                renderDevice().writeToTexture(texture, image.getWidth(), image.getHeight(), DeviceTexture.Format.R8G8B8A8_UNORM, accessor.getPointer());
                return;
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        super.writeToTexture(texture, image);
    }
}
