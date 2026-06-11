package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup.DropdownPopupWidget;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyDropdownPopupRenderer.class */
public class FancyDropdownPopupRenderer extends FancyBackgroundRenderer {
    public FancyDropdownPopupRenderer() {
        this.name = "DropdownPopup";
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderWidget(AbstractWidget<?> widget, ScreenContext context) {
        if (!(widget instanceof DropdownPopupWidget)) {
            super.renderWidget(widget, context);
            return;
        }
        DropdownPopupWidget<?> popupWidget = (DropdownPopupWidget) widget;
        DropdownWidget<?> dropdown = popupWidget.getDropdown();
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.OUTER);
        float progress = dropdown.getAnimationProgress();
        float height = rectangle.getHeight();
        Scissor scissor = context.canvas().scissor();
        try {
            scissor.push(rectangle.getX(), rectangle.getY() + (dropdown.isDropUp() ? height * (1.0f - progress) : 0.0f), rectangle.getWidth(), height * progress);
            super.renderWidget(widget, context);
            scissor.pop();
        } catch (Throwable th) {
            scissor.pop();
            throw th;
        }
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        if (!(widget instanceof DropdownPopupWidget)) {
            super.renderPre(widget, context);
            return;
        }
        DropdownPopupWidget<?> popupWidget = (DropdownPopupWidget) widget;
        DropdownWidget<?> dropdown = popupWidget.getDropdown();
        float progress = dropdown.getAnimationProgress();
        WidgetReference reference = dropdown.getReference();
        if (progress == 0.0f || reference == null) {
            return;
        }
        Rectangle popupRect = popupWidget.bounds().rectangle(BoundsType.OUTER);
        float popupHeight = reference.widget().getEffectiveHeight() + 2.0f;
        Rectangle rectangle = Rectangle.relative(popupRect.getX(), popupRect.getY() + (dropdown.isDropUp() ? popupHeight * (1.0f - progress) : 0.0f), popupRect.getWidth(), popupHeight * progress);
        int backgroundColor = widget.backgroundColor().get().intValue();
        int alpha = (int) Math.max((backgroundColor >> 24) & 255, progress * 255.0f);
        super.renderBackground(context, widget, rectangle, rectangle, (backgroundColor & 16777215) | (alpha << 24));
    }
}
