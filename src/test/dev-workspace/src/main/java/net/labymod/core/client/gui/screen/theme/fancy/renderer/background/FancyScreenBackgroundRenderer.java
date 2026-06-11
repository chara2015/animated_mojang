package net.labymod.core.client.gui.screen.theme.fancy.renderer.background;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaScreenBackgroundRenderer;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/background/FancyScreenBackgroundRenderer.class */
public class FancyScreenBackgroundRenderer extends VanillaScreenBackgroundRenderer {
    private static final UVCoordinates DEFAULT_COORDINATES = UVCoordinates.of(0, 0, 256, 256);
    private static final int BACKGROUND_COLOR = -1;
    private static final float BACKGROUND_ASPECT_RATIO = 1.7777778f;
    private final ResourceLocation backgroundLocation;
    protected final AbstractBootLogoRenderer loadingRenderer = LabyMod.references().bootLogoController().renderer();

    public FancyScreenBackgroundRenderer(Theme theme) {
        this.backgroundLocation = theme.resource(theme.getNamespace(), "textures/background.png");
        this.api.eventBus().registerListener(this);
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaScreenBackgroundRenderer
    public boolean hasScreenDynamicBackground(Object screenInstance) {
        return true;
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaScreenBackgroundRenderer
    public boolean renderIngameBackground(ScreenContext context, float left, float top, float right, float bottom, Object screenInstance) {
        Window window = this.minecraft.minecraftWindow();
        int windowHeight = MathHelper.ceil(window.getRawHeight() / window.getScale());
        int scaledHeight = window.getScaledHeight();
        int padding = windowHeight - scaledHeight;
        int paddingTop = padding / 2;
        context.canvas().submitAbsoluteGradientRect(GradientDirection.TOP_TO_BOTTOM, left, top - paddingTop, right, bottom + padding, -1072689136, -804253680);
        return true;
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaScreenBackgroundRenderer
    public boolean renderMenuBackground(ScreenContext context, float left, float top, float right, float bottom, Object screenInstance) {
        Window window = this.minecraft.minecraftWindow();
        int windowHeight = MathHelper.ceil(window.getRawHeight() / window.getScale());
        int scaledHeight = window.getScaledHeight();
        float offset = windowHeight - (bottom - top);
        float top2 = (-offset) / 2.0f;
        float bottom2 = windowHeight - (offset / 2.0f);
        int padding = windowHeight - scaledHeight;
        int paddingTop = padding / 2;
        float width = right - left;
        float height = bottom2 - top2;
        float aspectRatio = width / height;
        if (aspectRatio > BACKGROUND_ASPECT_RATIO) {
            float newHeight = width / BACKGROUND_ASPECT_RATIO;
            top2 = ((height - newHeight) / 2.0f) - paddingTop;
            bottom2 = top2 + newHeight;
        } else {
            float newWidth = height * BACKGROUND_ASPECT_RATIO;
            left = (width - newWidth) / 2.0f;
            right = left + newWidth;
        }
        RenderPipeline renderPipeline = this.api.renderPipeline();
        renderPipeline.setModifiedAlpha(false);
        ScreenCanvas canvas = context.canvas();
        canvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, this.backgroundLocation), left, top2, right - left, bottom2 - top2, DEFAULT_COORDINATES, -1);
        renderPipeline.setModifiedAlpha(true);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer
    @Nullable
    public ResourceLocation getBackgroundLocation() {
        return this.backgroundLocation;
    }
}
