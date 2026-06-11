package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyCheckBoxRenderer.class */
public class FancyCheckBoxRenderer extends VanillaBackgroundRenderer {
    public FancyCheckBoxRenderer() {
        super("CheckBox");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> abstractWidget, ScreenContext context) {
        if (!(abstractWidget instanceof CheckBoxWidget)) {
            return;
        }
        CheckBoxWidget checkBoxWidget = (CheckBoxWidget) abstractWidget;
        Bounds bounds = checkBoxWidget.bounds();
        float x = bounds.getX(BoundsType.INNER);
        float y = bounds.getY(BoundsType.INNER);
        float width = bounds.getWidth(BoundsType.INNER);
        float height = bounds.getHeight(BoundsType.INNER);
        ScreenCanvas screenCanvas = context.canvas();
        if (checkBoxWidget.state() == CheckBoxWidget.State.CHECKED) {
            screenCanvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, Textures.SpriteCommon.TEXTURE), x, y, width, height, UVCoordinates.of(32, 0, 32, 32), -1);
        }
        if (checkBoxWidget.state() == CheckBoxWidget.State.PARTLY) {
            float padding = height / 10.0f;
            BorderRadius borderRadius = abstractWidget.getBorderRadius();
            if (borderRadius == null) {
                return;
            }
            screenCanvas.submitAbsoluteRoundedRect(x + padding, y + padding, (x + width) - padding, (y + height) - padding, -16742400, RoundedData.builder().applyBorderRadius(borderRadius).build());
        }
    }
}
