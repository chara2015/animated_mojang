package net.labymod.core.client.gui.background.bootlogo;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/bootlogo/MojangStudiosBootLogoRenderer.class */
public class MojangStudiosBootLogoRenderer extends AbstractBootLogoRenderer {
    private boolean animated;

    @Override // net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer
    public void initialize() {
        super.initialize();
    }

    @Override // net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer
    public void renderBackground(ScreenContext context, float left, float top, float right, float bottom) {
        int backgroundColor = getBackgroundColor();
        context.canvas().submitAbsoluteRect(left, top, right, bottom, backgroundColor);
    }

    @Override // net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer
    public void renderForeground(ScreenContext context, float left, float top, float right, float bottom, boolean uiContext) {
        ResourceLocation resourceLocationSplashTexture;
        int i;
        float width = right - left;
        float height = bottom - top;
        int centerX = (int) (left + (width / 2.0f));
        int centerY = (int) (top + (height / 2.0f));
        double logoHeight = Math.min(((double) width) * 0.75d, height) * 0.25d;
        double logoWidth = logoHeight * 4.0d;
        int offsetY = (int) (logoHeight / 2.0d);
        int partWidth = (int) (logoWidth / 2.0d);
        int maxFrames = Textures.Splash.MOJANG_STUDIOS_FRAMES.length;
        int lastFrame = (4 * maxFrames) - 1;
        float timePassed = this.timePassed + TimeUtil.convertDeltaToMilliseconds(context.getTickDelta());
        this.timePassed = timePassed;
        int frame = (int) Math.min(timePassed / 40.0f, lastFrame);
        int index = this.animated ? frame : 0;
        int sprite = (index / 4) % Textures.Splash.MOJANG_STUDIOS_FRAMES.length;
        int offset = (index % 4) * 120;
        float resolutionHeight = 120 * (this.animated ? 4 : 1);
        if (this.animated) {
            resourceLocationSplashTexture = Textures.Splash.MOJANG_STUDIOS_FRAMES[sprite];
        } else {
            resourceLocationSplashTexture = this.labyAPI.minecraft().textures().splashTexture();
        }
        ResourceLocation location = resourceLocationSplashTexture;
        ScreenCanvas canvas = context.canvas();
        Material logoMaterial = GuiMaterial.textured(RenderStates.GUI_TEXTURED, location);
        canvas.submitGuiBlit(logoMaterial, centerX - partWidth, centerY - offsetY, partWidth, (int) logoHeight, UVCoordinates.of(uiContext ? -0.0625f : 0.0f, offset, 120.0f, 60.0f, 120.0f, resolutionHeight), -1, (RoundedData) null);
        canvas.submitGuiBlit(logoMaterial, centerX, centerY - offsetY, partWidth, (int) logoHeight, UVCoordinates.of(0.0f, 60 + offset, 120.0f, 60.0f, 120.0f, resolutionHeight), -1, (RoundedData) null);
        float progressWidth = (float) (logoHeight * 4.0d);
        if (this.progressVisible) {
            if (this.progress >= 0.95f) {
                i = (int) ((255.0f * (1.0f - this.progress)) / (1.0f - 0.95f));
            } else {
                i = 255;
            }
            int alpha = i;
            int progressColor = ColorFormat.ARGB32.pack(255, 255, 255, alpha);
            int backgroundColor = getBackgroundColor();
            float leftBar = centerX - (progressWidth / 2.0f);
            float topBar = (top + ((bottom - top) * 0.8325f)) - (10.0f / 2.0f);
            canvas.submitAbsoluteRect(leftBar, topBar, leftBar + progressWidth, topBar + 10.0f, progressColor);
            canvas.submitAbsoluteRect(leftBar + 1.0f, topBar + 1.0f, ((leftBar + 1.0f) + progressWidth) - 2.0f, ((topBar + 1.0f) + 10.0f) - 2.0f, backgroundColor);
            canvas.submitAbsoluteRect(leftBar + 2.0f, topBar + 2.0f, leftBar + 2.0f + ((progressWidth - 4.0f) * this.progress), ((topBar + 2.0f) + 10.0f) - 4.0f, progressColor);
        }
        float labyModHeight = (float) (logoHeight / 4.0d);
        float labyModWidth = (labyModHeight * 756.0f) / 164.0f;
        int logoType = this.labyAPI.config().appearance().darkLoadingScreen().get().booleanValue() ? 164 : 0;
        canvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, Textures.Splash.LABYMOD), centerX - (labyModWidth / 2.0f), (float) ((((double) centerY) - logoHeight) - ((double) labyModHeight)), labyModWidth, labyModHeight, UVCoordinates.of(0, logoType, 756, 164, 756, 492), -1, (RoundedData) null);
    }

    private int getBackgroundColor() {
        boolean darkLoadingScreen = this.labyAPI.config().appearance().darkLoadingScreen().get().booleanValue();
        if (darkLoadingScreen) {
            return ColorFormat.ARGB32.pack(0, 0, 0, 255);
        }
        return ColorFormat.ARGB32.pack(239, 50, 61, 255);
    }

    @Override // net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer
    protected void onPreloadResources() {
        if (this.animated) {
            for (ResourceLocation location : Textures.Splash.MOJANG_STUDIOS_FRAMES) {
                preloadTexture(location);
            }
        }
        preloadTexture(Textures.Splash.LABYMOD);
    }
}
