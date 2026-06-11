package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/button/VanillaButtonRenderer.class */
public class VanillaButtonRenderer extends ThemeRenderer<AbstractWidget<?>> {
    protected static final ResourceLocation BUTTON = ResourceLocation.create(Namespaces.MINECRAFT, "widget/button");
    protected static final ResourceLocation BUTTON_DISABLED = ResourceLocation.create(Namespaces.MINECRAFT, "widget/button_disabled");
    protected static final ResourceLocation BUTTON_HIGHLIGHTED = ResourceLocation.create(Namespaces.MINECRAFT, "widget/button_highlighted");

    public VanillaButtonRenderer() {
        super("Button");
    }

    public VanillaButtonRenderer(String name) {
        super(name);
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        renderTexture(context, widget.bounds().rectangle(BoundsType.MIDDLE), widget.isAttributeStateEnabled(AttributeState.ENABLED), widget.isHovered() || widget.isFocused(), widget.backgroundColor().get().intValue());
    }

    public void renderTexture(ScreenContext context, Rectangle rectangle, boolean enabled, boolean hovered, int backgroundColor) {
        TextureAtlas widgetAtlas = Laby.references().atlasRegistry().getAtlas(Atlases.WIDGET_ATLAS);
        ResourceLocation buttonTexture = enabled ? hovered ? BUTTON_HIGHLIGHTED : BUTTON : BUTTON_DISABLED;
        if (backgroundColor == 0) {
            backgroundColor = -1;
        }
        renderSingleVanillaButton(context, rectangle, backgroundColor, widgetAtlas, buttonTexture);
    }

    protected void renderSingleVanillaButton(ScreenContext context, Rectangle rectangle, int backgroundColor, TextureAtlas textureAtlas, ResourceLocation buttonTexture) {
        TextureSprite sprite = textureAtlas.findSprite(buttonTexture);
        if (sprite == null) {
            throw new IllegalStateException("Could not find sprite for button texture: " + String.valueOf(buttonTexture));
        }
        float x = rectangle.getX();
        float y = rectangle.getY();
        float width = rectangle.getWidth();
        float height = rectangle.getHeight();
        context.canvas().submitGuiSprite(textureAtlas.resource(), sprite, x, y, width, height, backgroundColor);
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer
    public void playInteractionSound(AbstractWidget<?> widget) {
        this.labyAPI.minecraft().sounds().playButtonPress();
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer
    public boolean hasInteractionSound() {
        return true;
    }
}
