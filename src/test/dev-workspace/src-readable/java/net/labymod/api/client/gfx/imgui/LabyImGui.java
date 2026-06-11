package net.labymod.api.client.gfx.imgui;

import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType;
import net.labymod.api.client.gfx.imgui.type.ImGuiFloatType;
import net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType;
import net.labymod.api.client.gfx.imgui.type.ImGuiLongType;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/LabyImGui.class */
public final class LabyImGui {
    private static final ImGuiAccessor ACCESSOR = Laby.references().imGuiAccessor();
    private static final ImGuiTypeProvider TYPE_PROVIDER = Laby.references().imGuiTypeProvider();

    public static void setNextWindowPos(float x, float y, int conditions) {
        ACCESSOR.setNextWindowPos(x, y, conditions);
    }

    public static void setNextWindowSize(float width, float height, int conditions) {
        ACCESSOR.setNextWindowSize(width, height, conditions);
    }

    public static void setNextWindowPosAndSize(float x, float y, float width, float height) {
        ACCESSOR.setNextWindowPosAndSize(x, y, width, height);
    }

    public static ImGuiViewport getMainViewport() {
        return ACCESSOR.getMainViewport();
    }

    public static ImGuiViewport getWindowViewport() {
        return ACCESSOR.getWindowViewport();
    }

    public static boolean beginWindow(String title) {
        return ACCESSOR.beginWindow(title);
    }

    public static boolean beginWindow(String title, ImGuiBooleanType open, int windowFlags) {
        return ACCESSOR.beginWindow(title, open, windowFlags);
    }

    public static void separator() {
        ACCESSOR.separator();
    }

    public static void textWithTooltip(String text, Supplier<String> tooltip) {
        text(text);
        if (isItemHovered()) {
            setTooltip(tooltip.get());
        }
    }

    public static void keyValuePair(String key, int value) {
        keyValuePair(key, Integer.toString(value));
    }

    public static void keyValuePair(String key, String value) {
        text("  " + key);
        sameLine(0.0f, 0.0f);
        text(": ");
        sameLine(0.0f, 0.0f);
        text(value);
    }

    public static void text(String text) {
        ACCESSOR.text(text);
    }

    public static void text(String text, TextColor color) {
        text(text, ColorUtil.adjustedColor(color.getValue()));
    }

    public static void text(String text, int argb) {
        ACCESSOR.text(text, argb);
    }

    public static void text(String text, int red, int green, int blue, int alpha) {
        ACCESSOR.text(text, red, green, blue, alpha);
    }

    public static void text(String text, float red, float green, float blue, float alpha) {
        ACCESSOR.text(text, red, green, blue, alpha);
    }

    public static void text(String text, int normalArgb, int hoverArgb) {
        ACCESSOR.text(text, normalArgb, hoverArgb);
    }

    public static boolean colorPicker4(String label, float[] colors) {
        return ACCESSOR.colorPicker4(label, colors);
    }

    public static void addRectangle(float minX, float minY, float maxX, float maxY, int color, float rounding) {
        ACCESSOR.addRectangle(minX, minY, maxX, maxY, color, rounding);
    }

    public static void pushTree() {
        ACCESSOR.pushTree();
    }

    public static boolean collapsingHeader(String label) {
        return ACCESSOR.collapsingHeader(label);
    }

    public static void endWindow() {
        ACCESSOR.endWindow();
    }

    public static boolean collapsingHeader(String label, int nodeFlags) {
        return ACCESSOR.collapsingHeader(label, nodeFlags);
    }

    public static boolean collapsingHeader(String label, ImGuiBooleanType visible) {
        return ACCESSOR.collapsingHeader(label, visible);
    }

    public static boolean collapsingHeader(String label, ImGuiBooleanType visible, int nodeFlags) {
        return ACCESSOR.collapsingHeader(label, visible, nodeFlags);
    }

    public static void popTree() {
        ACCESSOR.popTree();
    }

    public static boolean button(String label) {
        return ACCESSOR.button(label);
    }

    public static boolean button(String label, float width, float height) {
        return ACCESSOR.button(label, width, height);
    }

    public static boolean checkbox(String label, ImGuiBooleanType active) {
        return ACCESSOR.checkbox(label, active);
    }

    public static void beginTooltip() {
        ACCESSOR.beginTooltip();
    }

    public static void setTooltip(String text) {
        ACCESSOR.setTooltip(text);
    }

    public static void endTooltip() {
        ACCESSOR.endTooltip();
    }

    public static boolean isItemHovered() {
        return ACCESSOR.isItemHovered();
    }

    public static boolean isItemClicked() {
        return ACCESSOR.isItemClicked();
    }

    public static void sameLine() {
        ACCESSOR.sameLine();
    }

    public static void sameLine(float offsetFromStartX) {
        ACCESSOR.sameLine(offsetFromStartX);
    }

    public static void sameLine(float offsetFromStartX, float spacing) {
        ACCESSOR.sameLine(offsetFromStartX, spacing);
    }

    public static void beginGroup() {
        ACCESSOR.beginGroup();
    }

    public static void endGroup() {
        ACCESSOR.endGroup();
    }

    public static boolean treeNodeEx(String label, int flags) {
        return ACCESSOR.treeNodeEx(label, flags);
    }

    public static void image(int textureId, float sizeX, float sizeY) {
        ACCESSOR.image(textureId, sizeX, sizeY);
    }

    public static void image(ResourceLocation location, float sizeX, float sizeY) {
        ACCESSOR.image(location, sizeX, sizeY);
    }

    public static void image(int textureId, float sizeX, float sizeY, float minU, float minV) {
        ACCESSOR.image(textureId, sizeX, sizeY, minU, minV);
    }

    public static void image(int textureId, float sizeX, float sizeY, float minU, float minV, float maxU, float maxV) {
        ACCESSOR.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV);
    }

    public static void image(DeviceTexture handle, float sizeX, float sizeY, float minU, float minV, float maxU, float maxV) {
        ACCESSOR.image(((GlResource) handle).getId(), sizeX, sizeY, minU, minV, maxU, maxV);
    }

    public static void image(int textureId, float sizeX, float sizeY, float minU, float minV, float maxU, float maxV, int color) {
        ACCESSOR.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV, color);
    }

    public static void renderBounds(Bounds bounds) {
        ACCESSOR.renderBounds(bounds);
    }

    public static void setNextWindowSizeConstraints(float minWidth, float minHeight, float maxWidth, float maxHeight) {
        ACCESSOR.setNextWindowSizeConstraints(minWidth, minHeight, maxWidth, maxHeight);
    }

    public static FloatVector2 calculateTextSize(String text) {
        return ACCESSOR.calculateTextSize(text);
    }

    public static void pushId(int id) {
        ACCESSOR.pushId(id);
    }

    public static void pushId(String id) {
        ACCESSOR.pushId(id);
    }

    public static void popId() {
        ACCESSOR.popId();
    }

    public static void pushStyleColor(int imGuiColor, int color) {
        ACCESSOR.pushStyleColor(imGuiColor, color);
    }

    public static void pushStyleColor(int imGuiColor, int red, int green, int blue, int alpha) {
        ACCESSOR.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }

    public static void pushStyleColor(int imGuiColor, float red, float green, float blue, float alpha) {
        ACCESSOR.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }

    public static void popStyleColor() {
        ACCESSOR.popStyleColor();
    }

    public static FloatVector2 getWindowContentRegionMin() {
        return ACCESSOR.getWindowContentRegionMin();
    }

    public static FloatVector2 getWindowContentRegionMax() {
        return ACCESSOR.getWindowContentRegionMax();
    }

    public static FloatVector2 getWindowPosition() {
        return ACCESSOR.getWindowPosition();
    }

    public static boolean beginCombo(String label, String previewValue) {
        return ACCESSOR.beginCombo(label, previewValue);
    }

    public static boolean beginCombo(String label, String previewValue, int flags) {
        return ACCESSOR.beginCombo(label, previewValue, flags);
    }

    public static void endCombo() {
        ACCESSOR.endCombo();
    }

    public static void setItemDefaultFocus() {
        ACCESSOR.setItemDefaultFocus();
    }

    public static boolean selectable(String label, boolean selected) {
        return ACCESSOR.selectable(label, selected);
    }

    public static void addRectangleFilled(float minX, float minY, float maxX, float maxY, int color, float rounding) {
        ACCESSOR.addRectangleFilled(minX, minY, maxX, maxY, color, rounding);
    }

    public static FloatVector2 getItemRectMin() {
        return ACCESSOR.getItemRectMin();
    }

    public static FloatVector2 getItemRectMax() {
        return ACCESSOR.getItemRectMax();
    }

    public static boolean isKeyPressed(Key key) {
        return ACCESSOR.isKeyPressed(key);
    }

    public static boolean isKeyPressed(Key key, boolean repeat) {
        return ACCESSOR.isKeyPressed(key, repeat);
    }

    public static boolean isKeyReleased(Key key) {
        return ACCESSOR.isKeyReleased(key);
    }

    public static boolean isKeyDown(Key key) {
        return ACCESSOR.isKeyDown(key);
    }

    public static boolean isMouseClicked(Key mouseButton) {
        return ACCESSOR.isMouseClicked(mouseButton);
    }

    public static boolean isWindowHovered() {
        return ACCESSOR.isWindowHovered();
    }

    public static boolean isWindowFocused() {
        return ACCESSOR.isWindowFocused();
    }

    public static FloatVector2 getMousePos() {
        return ACCESSOR.getMousePos();
    }

    public static boolean isMouseDown(int button) {
        return ACCESSOR.isMouseDown(button);
    }

    public static boolean isMouseClicked(int button) {
        return ACCESSOR.isMouseClicked(button);
    }

    public static FloatVector2 getCursorScreenPos() {
        return ACCESSOR.getCursorScreenPos();
    }

    public static ImGuiBooleanType booleanType() {
        return TYPE_PROVIDER.booleanType();
    }

    public static ImGuiDoubleType doubleType(double value) {
        return TYPE_PROVIDER.doubleType(value);
    }

    public static ImGuiBooleanType booleanType(boolean value) {
        return TYPE_PROVIDER.booleanType(value);
    }

    public static ImGuiBooleanType booleanType(ImGuiBooleanType other) {
        return TYPE_PROVIDER.booleanType(other);
    }

    public static ImGuiDoubleType doubleType() {
        return TYPE_PROVIDER.doubleType();
    }

    public static ImGuiDoubleType doubleType(ImGuiDoubleType other) {
        return TYPE_PROVIDER.doubleType(other);
    }

    public static ImGuiFloatType floatType() {
        return TYPE_PROVIDER.floatType();
    }

    public static ImGuiLongType longType() {
        return TYPE_PROVIDER.longType();
    }

    public static ImGuiFloatType floatType(float value) {
        return TYPE_PROVIDER.floatType(value);
    }

    public static ImGuiIntegerType integerType(ImGuiIntegerType other) {
        return TYPE_PROVIDER.integerType(other);
    }

    public static ImGuiLongType longType(long value) {
        return TYPE_PROVIDER.longType(value);
    }

    public static ImGuiFloatType floatType(ImGuiFloatType other) {
        return TYPE_PROVIDER.floatType(other);
    }

    public static ImGuiIntegerType integerType() {
        return TYPE_PROVIDER.integerType();
    }

    public static ImGuiIntegerType integerType(int value) {
        return TYPE_PROVIDER.integerType(value);
    }

    public static ImGuiLongType longType(ImGuiLongType other) {
        return TYPE_PROVIDER.longType(other);
    }

    public static boolean invisibleButton(String id, float width, float height) {
        return ACCESSOR.invisibleButton(id, width, height);
    }

    public static float getMouseWheel() {
        return ACCESSOR.getMouseWheel();
    }

    public static void setCursorScreenPos(float x, float y) {
        ACCESSOR.setCursorScreenPos(x, y);
    }

    public static void dummy(float width, float height) {
        ACCESSOR.dummy(width, height);
    }
}
