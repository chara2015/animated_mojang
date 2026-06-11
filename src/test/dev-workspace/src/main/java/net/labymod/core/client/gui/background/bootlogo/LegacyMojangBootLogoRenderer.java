package net.labymod.core.client.gui.background.bootlogo;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/bootlogo/LegacyMojangBootLogoRenderer.class */
public class LegacyMojangBootLogoRenderer extends AbstractBootLogoRenderer {
    private static final UVCoordinates SCALED_BACKGROUND_UV = UVCoordinates.of(0, 0, 1, 1, 256, 256);
    private static final UVCoordinates MOJANG_LOGO_UV = UVCoordinates.of(0, 0, 256, 256, 256, 256);
    private static final boolean RENDER_LABYMOD_BRAND = false;

    @Override // net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer
    public void renderBackground(ScreenContext context, float left, float top, float right, float bottom) {
        ResourceLocation resourceLocation = this.labyAPI.minecraft().textures().splashTexture();
        context.canvas().submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, resourceLocation), left, top, left + right, top + bottom, SCALED_BACKGROUND_UV, -1, (RoundedData) null);
    }

    @Override // net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer
    public void renderForeground(ScreenContext context, float left, float top, float right, float bottom, boolean uiContext) {
        ResourceLocation location = this.labyAPI.minecraft().textures().splashTexture();
        float centerX = left + ((right - left) / 2.0f);
        float f = top + ((bottom - top) / 2.0f);
        float size = bottom - top;
        ScreenCanvas canvas = context.canvas();
        canvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, location), centerX - (size / 2.0f), top, size, size, MOJANG_LOGO_UV, -1);
    }

    @Override // net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer
    protected void onPreloadResources() {
        preloadTexture(Textures.Splash.LABYMOD);
    }
}
