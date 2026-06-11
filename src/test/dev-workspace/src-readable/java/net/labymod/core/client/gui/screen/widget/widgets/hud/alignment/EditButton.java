package net.labymod.core.client.gui.screen.widget.widgets.hud.alignment;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.screen.widget.widgets.hud.ScaledRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/alignment/EditButton.class */
public class EditButton {
    private final SelectionRenderer selection;
    private int previousEditOpacity = 0;
    private int editOpacity = 0;
    private boolean lastHoverEditIcon = false;

    public EditButton(SelectionRenderer selectionRenderer) {
        this.selection = selectionRenderer;
    }

    public void render(ScreenContext context) {
        MutableRectangle entireRectangle;
        HudWidget<?> hudWidget = this.selection.getLastSelectedHudWidget();
        if (hudWidget == null || (entireRectangle = this.selection.getEntireRectangle(1)) == null) {
            return;
        }
        HudWidgetInteractionWidget interactionWidget = this.selection.interactionWidget();
        HudWidgetWidget widget = interactionWidget.getWidget(hudWidget);
        boolean isOnRightSideOfScreen = entireRectangle.getCenterX() > interactionWidget.bounds().getCenterX();
        ScaledRectangle rectangle = widget.scaledBounds();
        float iconX = isOnRightSideOfScreen ? (rectangle.getLeft() - 8) - 3 : rectangle.getRight() + 3;
        float iconY = rectangle.getCenterY() - (8 / 2.0f);
        if (this.selection.isSingleSelection()) {
            this.lastHoverEditIcon = context.mouse().isInside(iconX, iconY, 8, 8);
        } else {
            this.lastHoverEditIcon = false;
        }
        float editOpacity = MathHelper.lerp(this.previousEditOpacity, this.editOpacity);
        int alpha = (int) ((editOpacity / 8.0f) * 255.0f);
        context.canvas().submitIcon(Textures.SpriteCommon.EDIT, iconX, iconY, 8, 8, false, ColorFormat.ARGB32.pack(255, 255, 255, alpha));
    }

    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (this.lastHoverEditIcon && this.selection.isSingleSelection()) {
            this.selection.interactionWidget().openSettings(this.selection.getLastSelectedHudWidget());
            return true;
        }
        return false;
    }

    public void onTick() {
        this.previousEditOpacity = this.editOpacity;
        if (this.lastHoverEditIcon) {
            if (this.editOpacity < 8) {
                this.editOpacity++;
            }
        } else {
            if (this.selection.isSingleSelection()) {
                if (this.editOpacity < 4) {
                    this.editOpacity++;
                }
                if (this.editOpacity > 4) {
                    this.editOpacity--;
                    return;
                }
                return;
            }
            if (this.editOpacity > 0) {
                this.editOpacity--;
            }
        }
    }
}
