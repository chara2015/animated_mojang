package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyDropdownRenderer.class */
public class FancyDropdownRenderer extends FancyBackgroundRenderer {
    public FancyDropdownRenderer() {
        this.name = "Dropdown";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        if (!(widget instanceof DropdownWidget)) {
            super.renderPre(widget, context);
            return;
        }
        DropdownWidget<?> dropdown = (DropdownWidget) widget;
        float progress = dropdown.getAnimationProgress();
        WidgetReference reference = dropdown.getReference();
        if (progress == 0.0f || reference == null) {
            super.renderPre(widget, context);
            return;
        }
        Bounds bounds = dropdown.bounds();
        float popupHeight = reference.widget().getEffectiveHeight();
        Rectangle rectangle = Rectangle.relative(bounds.getX(), bounds.getY() - (dropdown.isDropUp() ? popupHeight * progress : 0.0f), bounds.getWidth(), bounds.getHeight() + (popupHeight * progress));
        int backgroundColor = widget.backgroundColor().get().intValue();
        int alpha = (int) Math.max((backgroundColor >> 24) & 255, progress * 255.0f);
        super.renderBackground(context, widget, rectangle, rectangle, (backgroundColor & 16777215) | (alpha << 24));
    }

    @Override // net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPost(widget, context);
        if (!(widget instanceof DropdownWidget)) {
            return;
        }
        DropdownWidget<?> dropdown = (DropdownWidget) widget;
        Bounds bounds = widget.bounds();
        float rotation = (dropdown.isOpen() ? 1 : -1) * dropdown.getAnimationProgress() * 180.0f;
        Stack stack = context.stack();
        stack.push();
        stack.translate((bounds.getX() + bounds.getWidth()) - 8.0f, bounds.getY() + (bounds.getHeight() / 2.0f), 0.0f);
        stack.rotate(rotation, 0.0f, 0.0f, 1.0f);
        if (dropdown.hasEntries()) {
            context.canvas().submitIcon(Textures.SpriteCommon.SMALL_DOWN_SHADOW, -4.0f, -4.0f, 8.0f, 8.0f);
        }
        stack.pop();
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer
    public void playInteractionSound(AbstractWidget<?> widget) {
        Laby.references().soundService().play(SoundType.BUTTON_CLICK);
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer
    public boolean hasInteractionSound() {
        return true;
    }
}
