package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyItemStackPickerRenderer.class */
public class FancyItemStackPickerRenderer extends VanillaBackgroundRenderer {
    public FancyItemStackPickerRenderer() {
        super("ItemStackPicker");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        ItemStackPickerWidget pickerWidget = (ItemStackPickerWidget) widget;
        Bounds bounds = widget.bounds();
        super.renderPost(widget, context);
        Laby.references().itemStackVisualizer().submitItem(context, pickerWidget.itemStack(), (int) (bounds.getCenterX() - 8.0f), (int) (bounds.getCenterY() - 8.0f));
    }
}
