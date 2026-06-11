package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.element;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/element/BoundsElement.class */
public final class BoundsElement {
    private static final float BOUNDS_SPACE = 25.0f;
    private static final float BOUNDS_INNER_HEIGHT = 30.0f;

    private BoundsElement() {
    }

    public static void renderBounds(Bounds bounds) {
        ImVec2 pos = ImGui.getWindowPos();
        ImVec2 windowContentRegionMin = ImGui.getWindowContentRegionMin();
        ImVec2 windowContentRegionMax = ImGui.getWindowContentRegionMax();
        float windowContentWidth = windowContentRegionMax.x - windowContentRegionMin.x;
        float windowContentHeight = windowContentRegionMax.y - windowContentRegionMin.y;
        float x = pos.x + windowContentRegionMin.x;
        float y = pos.y + windowContentRegionMin.y;
        ImVec2 center = new ImVec2(x + (windowContentWidth * 0.5f), y + (windowContentHeight * 0.5f));
        String positionText = toPrettyValue(bounds.getX(BoundsType.OUTER), true) + ", " + toPrettyValue(bounds.getY(BoundsType.OUTER), true);
        float boundsInnerWidth = windowContentWidth / 2.0f;
        ImDrawList drawList = ImGui.getForegroundDrawList();
        renderText(drawList, center, positionText, x, y, boundsInnerWidth, 6.0f, -8912897);
        renderText(drawList, center, "margin", x, y, boundsInnerWidth, 4.0f, getColorByType(BoundsType.OUTER));
        renderText(drawList, center, "border", x, y, boundsInnerWidth, 2.0f, getColorByType(BoundsType.BORDER));
        renderText(drawList, center, "padding", x, y, boundsInnerWidth, 1.0f, getColorByType(BoundsType.MIDDLE));
        renderBounds(drawList, center, boundsInnerWidth + 150.0f, 180.0f, bounds, BoundsType.OUTER, BOUNDS_SPACE);
        renderBounds(drawList, center, boundsInnerWidth + 100.0f, 130.0f, bounds, BoundsType.BORDER, BOUNDS_SPACE);
        renderBounds(drawList, center, boundsInnerWidth + 50.0f, 80.0f, bounds, BoundsType.MIDDLE, BOUNDS_SPACE);
        renderBounds(drawList, center, boundsInnerWidth, BOUNDS_INNER_HEIGHT, bounds, BoundsType.INNER, BOUNDS_SPACE);
    }

    private static void renderText(ImDrawList drawList, ImVec2 center, String text, float x, float y, float boundsInnerWidth, float space, int color) {
        if (space == 1.0f) {
            FloatVector2 windowContentRegionMin = LabyImGui.getWindowContentRegionMin();
            float textY = center.y - BOUNDS_INNER_HEIGHT;
            if (textY >= y - (ImGui.getFontSize() / 2.0f)) {
                float textX = center.x - (boundsInnerWidth / 2.0f);
                if (textX >= x - (windowContentRegionMin.getX() / 2.0f)) {
                    drawList.addText(textX, textY, color, text);
                    return;
                }
                return;
            }
            return;
        }
        FloatVector2 windowContentRegionMin2 = LabyImGui.getWindowContentRegionMin();
        float textY2 = (center.y - ((BOUNDS_INNER_HEIGHT + (BOUNDS_SPACE * space)) / 2.0f)) - 14.0f;
        if (textY2 >= y - (ImGui.getFontSize() / 2.0f)) {
            float textX2 = center.x - ((boundsInnerWidth + (BOUNDS_SPACE * space)) / 2.0f);
            if (textX2 >= x - (windowContentRegionMin2.getX() / 2.0f)) {
                drawList.addText(textX2, textY2, color, text);
            }
        }
    }

    private static void renderBounds(ImDrawList drawList, ImVec2 center, float width, float height, Bounds bounds, BoundsType type, float space) {
        float offsetX = center.x - (width / 2.0f);
        float offsetY = center.y - (height / 2.0f);
        ImVec2 pos = ImGui.getWindowPos();
        ImVec2 windowContentRegionMin = ImGui.getWindowContentRegionMin();
        float x = pos.x + windowContentRegionMin.x;
        float y = pos.y + windowContentRegionMin.y;
        if (offsetX <= x - (windowContentRegionMin.x / 2.0f) || offsetY <= y - (ImGui.getFontSize() / 2.0f)) {
            return;
        }
        drawList.addRect(offsetX, offsetY, offsetX + width, offsetY + height, getColorByType(type), 0.0f);
        if (type == BoundsType.INNER) {
            renderLabel(drawList, center, bounds, BoundsType.INNER, null, 1.0f, 0.0f);
            return;
        }
        renderLabel(drawList, center, bounds, type, OffsetSide.LEFT, ((-width) / 2.0f) + (space / 2.0f), 0.0f);
        renderLabel(drawList, center, bounds, type, OffsetSide.BOTTOM, 1.0f, (height / 2.0f) - (space / 2.0f));
        renderLabel(drawList, center, bounds, type, OffsetSide.RIGHT, (width / 2.0f) - (space / 2.0f), 0.0f);
        renderLabel(drawList, center, bounds, type, OffsetSide.TOP, 1.0f, ((-height) / 2.0f) + (space / 2.0f));
    }

    private static void renderLabel(ImDrawList drawList, ImVec2 center, Bounds bounds, BoundsType type, @Nullable OffsetSide side, float offsetX, float offsetY) {
        float offset;
        String labelContent;
        if (side == null) {
            labelContent = toPrettyValue(bounds.getWidth(type), true) + " x " + toPrettyValue(bounds.getHeight(type), true);
        } else {
            WidgetStyleSheetUpdater updater = bounds.getUpdater();
            if (type == BoundsType.BORDER) {
                offset = updater.getBorder(side).floatValue();
            } else if (type == BoundsType.MIDDLE) {
                offset = updater.getPadding(side).floatValue();
            } else {
                offset = updater.getMargin(side).floatValue();
            }
            labelContent = toPrettyValue(offset, false);
        }
        ImVec2 textSize = ImGui.calcTextSize(labelContent);
        float textX = center.x + offsetX;
        float textY = center.y + offsetY;
        drawList.addText(textX - (textSize.x / 2.0f), textY - (textSize.y / 2.0f), getColorByType(type), labelContent);
    }

    private static int getColorByType(BoundsType type) {
        int color;
        if (type == BoundsType.INNER) {
            color = -11043425;
        } else if (type == BoundsType.MIDDLE) {
            color = -10256809;
        } else if (type == BoundsType.BORDER) {
            color = -4807309;
        } else {
            color = -5209260;
        }
        return ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color);
    }

    private static String toPrettyValue(float value, boolean showZero) {
        if (Float.isNaN(value)) {
            return "NaN";
        }
        if (value == 0.0f && !showZero) {
            return "-";
        }
        if (((int) value) == value) {
            return String.valueOf((int) value);
        }
        return String.valueOf(((int) (value * 10.0f)) / 10.0f);
    }
}
