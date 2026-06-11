package net.labymod.api.util.debug;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.DefaultLabyImGuiWindows;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/debug/DebugWidgetRenderer.class */
public final class DebugWidgetRenderer {
    private static final int WHITE = 16777215;
    private static final int SELECTED_WHITE = 268435455;

    public static void renderDebug(ScreenContext context, Widget widget) {
        if (Laby.references().controlEntryRegistry().isVisible(DefaultLabyImGuiWindows.DOCUMENT)) {
            renderImGuiDebug(context, widget);
        }
        Laby.references().screenWindowManagement().renderWidgetOverlay(context, widget);
    }

    private static void renderImGuiDebug(ScreenContext context, Widget widget) {
        Widget targetWidget = Laby.references().documentHandler().getTargetWidget();
        boolean isSelected = targetWidget != null && targetWidget.equals(widget);
        int alpha = isSelected ? 255 : 42;
        Bounds bounds = widget.bounds();
        boolean useFloatingPoints = Laby.labyAPI().minecraft().isKeyPressed(Key.L_SHIFT);
        float x = bounds.getX(BoundsType.MIDDLE);
        float y = bounds.getY(BoundsType.MIDDLE);
        float width = bounds.getWidth(BoundsType.MIDDLE);
        float height = bounds.getHeight(BoundsType.MIDDLE);
        if (!useFloatingPoints) {
            x = Math.round(x);
            y = Math.round(y);
            width = Math.round(width);
            height = Math.round(height);
        }
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitRelativeOutlineRect(x, y, width, height, 1.0f, 1, WHITE | (alpha << 24));
        if (isSelected) {
            screenCanvas.submitRelativeRect(x, y, width, height, SELECTED_WHITE);
        }
    }
}
