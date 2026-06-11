package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/button/VanillaButtonDropdownRenderer.class */
public class VanillaButtonDropdownRenderer extends VanillaButtonRenderer {
    public VanillaButtonDropdownRenderer() {
        this.name = "ButtonDropdown";
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        if (!(widget instanceof DropdownWidget)) {
            return;
        }
        DropdownWidget<?> dropdownWidget = (DropdownWidget) widget;
        Bounds bounds = widget.bounds();
        float x = bounds.getX(BoundsType.MIDDLE);
        float y = bounds.getY(BoundsType.MIDDLE);
        float width = bounds.getWidth(BoundsType.MIDDLE);
        float height = bounds.getHeight(BoundsType.MIDDLE);
        boolean open = dropdownWidget.isOpen();
        boolean hasEntries = dropdownWidget.hasEntries();
        ScreenCanvas screenCanvas = context.canvas();
        if (hasEntries) {
            Icon icon = open ? Textures.SpriteCommon.SMALL_UP_SHADOW : Textures.SpriteCommon.SMALL_DOWN_SHADOW;
            screenCanvas.submitIcon(icon, (x + width) - 12.0f, (y + (height / 2.0f)) - 4.0f, 8.0f, 8.0f);
        }
        screenCanvas.submitIcon(Textures.SpriteCommon.DELIMITER, ((x + width) - 8.0f) - 12.0f, y + 2.0f, 8.0f, height - 4.0f);
    }
}
