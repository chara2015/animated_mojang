package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaCheckBoxRenderer.class */
public class VanillaCheckBoxRenderer extends VanillaButtonRenderer {
    public VanillaCheckBoxRenderer() {
        super("CheckBox");
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        renderTexture(context, bounds.rectangle(BoundsType.MIDDLE), false, widget.isHovered(), widget.backgroundColor().get().intValue());
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        float x = Math.round(bounds.getX(BoundsType.MIDDLE));
        float y = Math.round(bounds.getY(BoundsType.MIDDLE));
        float width = Math.round(bounds.getWidth(BoundsType.MIDDLE));
        float height = Math.round(bounds.getHeight(BoundsType.MIDDLE));
        CheckBoxWidget checkBoxWidget = (CheckBoxWidget) widget;
        ScreenCanvas screenCanvas = context.canvas();
        if (checkBoxWidget.state() == CheckBoxWidget.State.CHECKED) {
            screenCanvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, Textures.SpriteCommon.TEXTURE), x, y, width, height, UVCoordinates.of(32, 0, 32, 32), -1);
        }
        if (checkBoxWidget.state() == CheckBoxWidget.State.PARTLY) {
            float padding = height / 5.0f;
            screenCanvas.submitAbsoluteRect(x + padding, y + padding, (x + width) - padding, (y + height) - padding, -16730112);
        }
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer
    public boolean hasInteractionSound() {
        return false;
    }
}
