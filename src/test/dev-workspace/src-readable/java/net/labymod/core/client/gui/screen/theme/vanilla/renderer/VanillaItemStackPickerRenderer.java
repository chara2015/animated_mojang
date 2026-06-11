package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaItemStackPickerRenderer.class */
public class VanillaItemStackPickerRenderer extends VanillaButtonRenderer {
    public VanillaItemStackPickerRenderer() {
        super("ItemStackPicker");
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        renderTexture(context, bounds.rectangle(BoundsType.MIDDLE), widget.isAttributeStateEnabled(AttributeState.ENABLED), widget.isHovered(), widget.backgroundColor().get().intValue());
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPost(widget, context);
        ItemStackPickerWidget pickerWidget = (ItemStackPickerWidget) widget;
        Bounds bounds = widget.bounds();
        Laby.references().itemStackVisualizer().submitItem(context, pickerWidget.itemStack(), (int) (bounds.getCenterX() - 8.0f), (int) (bounds.getCenterY() - 8.0f));
    }
}
