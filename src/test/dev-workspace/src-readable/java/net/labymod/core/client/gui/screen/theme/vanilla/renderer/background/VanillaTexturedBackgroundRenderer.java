package net.labymod.core.client.gui.screen.theme.vanilla.renderer.background;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureSprite;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/background/VanillaTexturedBackgroundRenderer.class */
public class VanillaTexturedBackgroundRenderer extends ThemeRenderer<AbstractWidget<?>> {
    private final ResourceLocation resource;
    private final TextureSprite sprite;

    public VanillaTexturedBackgroundRenderer(VanillaTheme theme) {
        super("TexturedBackground");
        this.resource = theme.resource("labymod", "textures/widgets/textured_background.png");
        this.sprite = new DefaultTextureSprite(0.0f, 0.0f, 1.0f, 1.0f, 200.0f, 20.0f, (width, height) -> {
            return new NineSpliceScaling((int) width, (int) height, new NineSpliceScaling.Border(1));
        });
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.MIDDLE);
        float x = rectangle.getX();
        float y = rectangle.getY();
        float width = rectangle.getWidth();
        float height = rectangle.getHeight();
        int backgroundColor = widget.backgroundColor().get().intValue();
        if (backgroundColor == 0) {
            backgroundColor = -1;
        }
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitGuiSprite(this.resource, this.sprite, MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(width), MathHelper.ceil(height), backgroundColor);
    }
}
