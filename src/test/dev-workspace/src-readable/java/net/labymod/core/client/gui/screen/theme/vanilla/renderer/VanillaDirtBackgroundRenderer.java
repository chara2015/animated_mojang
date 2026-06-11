package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.DirtBackgroundType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaDirtBackgroundRenderer.class */
public class VanillaDirtBackgroundRenderer extends VanillaBackgroundRenderer {
    public VanillaDirtBackgroundRenderer() {
        this.name = "DirtBackground";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        ResourceLocation resourceLocationScreenListBackgroundTexture;
        float brightness;
        float shift = widget.backgroundDirtShift().get().intValue();
        DirtBackgroundType dirtBackgroundType = widget.backgroundDirtType().get();
        float legacyBrightness = widget.backgroundDirtBrightness().get().intValue();
        if (legacyBrightness != 0.0f) {
            dirtBackgroundType = legacyBrightness >= 64.0f ? DirtBackgroundType.MENU : DirtBackgroundType.LIST;
        }
        MinecraftTextures textures = this.labyAPI.minecraft().textures();
        if (dirtBackgroundType == DirtBackgroundType.MENU) {
            resourceLocationScreenListBackgroundTexture = textures.screenMenuBackgroundTexture();
        } else {
            resourceLocationScreenListBackgroundTexture = textures.screenListBackgroundTexture();
        }
        ResourceLocation backgroundTexture = resourceLocationScreenListBackgroundTexture;
        if (PlatformEnvironment.isFreshUI()) {
            brightness = 255.0f;
        } else {
            brightness = dirtBackgroundType.getBrightness();
        }
        float brightness2 = brightness;
        if (!widget.backgroundAlwaysDirt().get().booleanValue() && this.labyAPI.minecraft().isIngame()) {
            super.renderPre(widget, context);
        } else {
            Rectangle bounds = Bounds.absoluteBounds(widget, BoundsType.MIDDLE);
            context.canvas().submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, backgroundTexture), widget.bounds().rectangle(BoundsType.MIDDLE), UVCoordinates.of(bounds.getX() * 8.0f, (bounds.getY() + shift) * 8.0f, bounds.getWidth() * 8.0f, bounds.getHeight() * 8.0f), ColorFormat.ARGB32.pack(brightness2 / 255.0f, brightness2 / 255.0f, brightness2 / 255.0f, 1.0f));
        }
        super.renderPre(widget, context);
    }
}
