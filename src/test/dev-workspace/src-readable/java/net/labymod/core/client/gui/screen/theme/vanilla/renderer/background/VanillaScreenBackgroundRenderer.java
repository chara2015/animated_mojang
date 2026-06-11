package net.labymod.core.client.gui.screen.theme.vanilla.renderer.background;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaThemeConfig;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/background/VanillaScreenBackgroundRenderer.class */
public class VanillaScreenBackgroundRenderer implements BackgroundRenderer {
    protected static final int VANILLA_BACKGROUND_TOP_COLOR = -1072689136;
    protected static final int VANILLA_BACKGROUND_BOTTOM_COLOR = -804253680;
    protected boolean canTick = false;
    protected final LabyAPI api = Laby.labyAPI();
    protected final Minecraft minecraft = this.api.minecraft();
    protected final DynamicBackgroundController dynamicBackground = LabyMod.references().dynamicBackgroundController();

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (!this.canTick) {
            return;
        }
        this.canTick = false;
        this.dynamicBackground.tick();
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer
    public boolean renderBackground(ScreenContext context, Object screenInstance) {
        boolean isIngame = this.minecraft.isIngame();
        Window window = this.minecraft.minecraftWindow();
        int scaledWidth = window.getScaledWidth();
        int scaledHeight = window.getScaledHeight();
        if (isIngame) {
            if (this.api.config().appearance().hideMenuBackground().get().booleanValue()) {
                return true;
            }
            return renderIngameBackground(context, 0.0f, 0.0f, scaledWidth, scaledHeight, screenInstance);
        }
        if (DynamicBackgroundController.isEnabled() && hasScreenDynamicBackground(screenInstance)) {
            return renderDynamicBackground(context, 0.0f, 0.0f, window.getRawWidth(), window.getRawHeight(), screenInstance);
        }
        return renderMenuBackground(context, 0.0f, 0.0f, scaledWidth, scaledHeight, screenInstance);
    }

    public boolean hasScreenDynamicBackground(Object screenInstance) {
        return isFreshUIEnabled();
    }

    public boolean renderDynamicBackground(ScreenContext context, float left, float top, float right, float bottom, Object screenInstance) {
        Stack stack = context.stack();
        stack.push();
        stack.translate(left, top, 0.0f);
        this.dynamicBackground.renderTick(stack, left, top, right, bottom, context.getTickDelta());
        this.dynamicBackground.render(context, left, top, right, bottom);
        stack.pop();
        this.canTick = true;
        return true;
    }

    public boolean renderIngameBackground(ScreenContext context, float left, float top, float right, float bottom, Object screenInstance) {
        if (isFreshUIEnabled()) {
            return false;
        }
        context.canvas().submitAbsoluteGradientRect(GradientDirection.TOP_TO_BOTTOM, left, top, right, bottom, VANILLA_BACKGROUND_TOP_COLOR, VANILLA_BACKGROUND_BOTTOM_COLOR);
        return true;
    }

    public boolean renderMenuBackground(ScreenContext context, float left, float top, float right, float bottom, Object screenInstance) {
        if (isFreshUIEnabled()) {
            return false;
        }
        ResourceLocation backgroundTexture = this.minecraft.textures().screenMenuBackgroundTexture();
        float brightness = PlatformEnvironment.isFreshUI() ? 255.0f : 64.0f;
        ScreenCanvas canvas = context.canvas();
        canvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, backgroundTexture), left, top, left + right, top + bottom, UVCoordinates.of(0.0f, 0.0f, right * 8.0f, bottom * 8.0f), ColorFormat.ARGB32.pack(brightness / 255.0f, brightness / 255.0f, brightness / 255.0f, 1.0f));
        return true;
    }

    public boolean isFreshUIEnabled() {
        VanillaThemeConfig config = (VanillaThemeConfig) this.api.themeService().getThemeConfig(VanillaThemeConfig.class);
        return PlatformEnvironment.isFreshUI() && config != null && config.freshUI().get().booleanValue();
    }
}
