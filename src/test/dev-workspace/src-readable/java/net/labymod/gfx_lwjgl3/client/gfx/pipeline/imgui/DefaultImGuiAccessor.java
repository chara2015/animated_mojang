package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.type.ImBoolean;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiAccessor;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.models.Implements;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.element.BoundsElement;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.viewport.DefaultImGuiViewport;
import net.labymod.laby3d.api.opengl.textures.GlDeviceTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/DefaultImGuiAccessor.class */
@Singleton
@Implements(ImGuiAccessor.class)
public class DefaultImGuiAccessor implements ImGuiAccessor {
    private final TextureRepository textureRepository;

    public DefaultImGuiAccessor(TextureRepository textureRepository) {
        this.textureRepository = textureRepository;
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void setNextWindowPos(float x, float y, int conditions) {
        ImGui.setNextWindowPos(x, y, conditions);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void setNextWindowSize(float width, float height, int conditions) {
        ImGui.setNextWindowSize(width, height, conditions);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void setNextWindowPosAndSize(float x, float y, float width, float height) {
        setNextWindowPos(x, y, 4);
        setNextWindowSize(width, height, 4);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public ImGuiViewport getMainViewport() {
        return new DefaultImGuiViewport(ImGui.getMainViewport());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public ImGuiViewport getWindowViewport() {
        return new DefaultImGuiViewport(ImGui.getWindowViewport());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean beginWindow(String title) {
        return ImGui.begin(title);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean beginWindow(String title, ImGuiBooleanType open, int windowFlags) {
        boolean windowOpened;
        if (Laby.labyAPI().minecraft().isMouseLocked()) {
            windowFlags |= 393732;
        }
        if (open == null) {
            windowOpened = ImGui.begin(title, windowFlags);
        } else {
            windowOpened = ImGui.begin(title, (ImBoolean) open.asImGuiType(), windowFlags);
        }
        imgui.ImGuiViewport viewport = ImGui.getWindowViewport();
        viewport.setFlags(512);
        return windowOpened;
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void text(String text) {
        ImGui.text(text);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void text(String text, int argb) {
        ImGui.textColored(ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, argb), text);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void text(String text, int red, int green, int blue, int alpha) {
        ImGui.textColored(red, green, blue, alpha, text);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void text(String text, float red, float green, float blue, float alpha) {
        ImGui.textColored(red, green, blue, alpha, text);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void text(String text, int normalArgb, int hoverArgb) {
        ImGui.beginGroup();
        ImVec2 textSize = ImGui.calcTextSize(text);
        ImGui.invisibleButton("##" + text, textSize.x, textSize.y);
        ImGui.setCursorPosY((ImGui.getCursorPosY() - textSize.y) - 4.0f);
        if (ImGui.isItemHovered()) {
            text(text, hoverArgb);
        } else {
            text(text, normalArgb);
        }
        ImGui.endGroup();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 calculateTextSize(String text) {
        ImVec2 textSize = ImGui.calcTextSize(text);
        return new FloatVector2(textSize.x, textSize.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean colorPicker4(String label, float[] colors) {
        return ImGui.colorPicker4(label, colors, 16777376);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void addRectangle(float minX, float minY, float maxX, float maxY, int color, float rounding) {
        ImDrawList drawList = ImGui.getForegroundDrawList();
        float itemMinX = ImGui.getCursorScreenPosX();
        float itemMinY = ImGui.getCursorScreenPosY();
        float mx = minX + itemMinX;
        float my = minY + itemMinY;
        drawList.addRectFilled(mx, my, mx + maxX, my + maxY, color, rounding);
        drawList.addRect(mx, my, mx + maxX, my + maxY, ColorFormat.ABGR32.pack(100, 100, 100, 255), rounding);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void separator() {
        ImGui.separator();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean collapsingHeader(String label) {
        return ImGui.collapsingHeader(label);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean collapsingHeader(String label, int nodeFlags) {
        return ImGui.collapsingHeader(label, nodeFlags);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean collapsingHeader(String label, ImGuiBooleanType visible) {
        return ImGui.collapsingHeader(label, (ImBoolean) visible.asImGuiType());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean collapsingHeader(String label, ImGuiBooleanType visible, int nodeFlags) {
        return ImGui.collapsingHeader(label, (ImBoolean) visible.asImGuiType(), nodeFlags);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void pushTree() {
        ImGui.treePush();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void popTree() {
        ImGui.treePop();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean button(String label) {
        return ImGui.button(label);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean button(String label, float width, float height) {
        return ImGui.button(label, width, height);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean checkbox(String label, ImGuiBooleanType active) {
        return ImGui.checkbox(label, (ImBoolean) active.asImGuiType());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void beginTooltip() {
        ImGui.beginTooltip();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void setTooltip(String text) {
        ImGui.setTooltip(text);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void endTooltip() {
        ImGui.endTooltip();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isItemHovered() {
        return ImGui.isItemHovered();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isItemClicked() {
        return ImGui.isItemClicked();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void sameLine() {
        ImGui.sameLine();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void sameLine(float offsetFromStartX) {
        ImGui.sameLine(offsetFromStartX);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void sameLine(float offsetFromStartX, float spacing) {
        ImGui.sameLine(offsetFromStartX, spacing);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void beginGroup() {
        ImGui.beginGroup();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void endGroup() {
        ImGui.endGroup();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void endWindow() {
        ImGui.end();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean treeNodeEx(String label, int flags) {
        return ImGui.treeNodeEx(label, flags);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void setNextWindowSizeConstraints(float minWidth, float minHeight, float maxWidth, float maxHeight) {
        ImGui.setNextWindowSizeConstraints(minWidth, minHeight, maxWidth, maxHeight);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void renderBounds(Bounds bounds) {
        BoundsElement.renderBounds(bounds);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void pushId(int id) {
        ImGui.pushID(id);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void pushId(String id) {
        ImGui.pushID(id);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void popId() {
        ImGui.popID();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void pushStyleColor(int imGuiColor, int color) {
        ImGui.pushStyleColor(imGuiColor, color);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void pushStyleColor(int imGuiColor, int red, int green, int blue, int alpha) {
        ImGui.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void pushStyleColor(int imGuiColor, float red, float green, float blue, float alpha) {
        ImGui.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void popStyleColor() {
        ImGui.popStyleColor();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 getWindowContentRegionMin() {
        ImVec2 windowContentRegionMin = ImGui.getWindowContentRegionMin();
        return new FloatVector2(windowContentRegionMin.x, windowContentRegionMin.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 getWindowContentRegionMax() {
        ImVec2 windowContentRegionMax = ImGui.getWindowContentRegionMax();
        return new FloatVector2(windowContentRegionMax.x, windowContentRegionMax.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 getWindowPosition() {
        ImVec2 pos = ImGui.getWindowPos();
        return new FloatVector2(pos.x, pos.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean beginCombo(String label, String previewValue) {
        return beginCombo(label, previewValue, 0);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean beginCombo(String label, String previewValue, int flags) {
        return ImGui.beginCombo(label, previewValue, flags);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void endCombo() {
        ImGui.endCombo();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void setItemDefaultFocus() {
        ImGui.setItemDefaultFocus();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean selectable(String label, boolean selected) {
        return ImGui.selectable(label, selected);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void addRectangleFilled(float minX, float minY, float maxX, float maxY, int color, float rounding) {
        ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addRectFilled(minX, minY, maxX, maxY, color, rounding);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void image(int textureId, float sizeX, float sizeY) {
        ImGui.image(textureId, sizeX, sizeY);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void image(ResourceLocation location, float sizeX, float sizeY) {
        Texture texture = this.textureRepository.getTexture(location);
        if (texture == null) {
            return;
        }
        GlDeviceTexture glDeviceTextureDeviceTexture = texture.deviceTexture();
        if (glDeviceTextureDeviceTexture instanceof GlDeviceTexture) {
            GlDeviceTexture glTextureHandle = glDeviceTextureDeviceTexture;
            image(glTextureHandle.getId(), sizeX, sizeY);
        }
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void image(int textureId, float sizeX, float sizeY, float minU, float minV) {
        ImGui.image(textureId, sizeX, sizeY, minU, minV);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void image(int textureId, float sizeX, float sizeY, float minU, float minV, float maxU, float maxV) {
        ImGui.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void image(int textureId, float sizeX, float sizeY, float minU, float minV, float maxU, float maxV, int color) {
        ColorFormat format = ColorFormat.ARGB32;
        float red = format.red(color);
        float green = format.green(color);
        float blue = format.blue(color);
        float alpha = format.alpha(color);
        ImGui.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV, red, green, blue, alpha);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 getItemRectMin() {
        ImVec2 min = ImGui.getItemRectMin();
        return new FloatVector2(min.x, min.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 getItemRectMax() {
        ImVec2 max = ImGui.getItemRectMax();
        return new FloatVector2(max.x, max.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isKeyPressed(Key key) {
        return ImGui.isKeyPressed(key.getId());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isKeyPressed(Key key, boolean repeat) {
        return ImGui.isKeyPressed(key.getId(), repeat);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isKeyReleased(Key key) {
        return ImGui.isKeyReleased(key.getId());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isKeyDown(Key key) {
        return ImGui.isKeyDown(key.getId());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isMouseClicked(Key mouseButton) {
        return ImGui.isMouseClicked(mouseButton.getId());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isWindowHovered() {
        return ImGui.isWindowHovered();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isWindowFocused() {
        return ImGui.isWindowFocused();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 getMousePos() {
        ImVec2 mousePos = ImGui.getMousePos();
        return new FloatVector2(mousePos.x, mousePos.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isMouseDown(int button) {
        return ImGui.isMouseDown(button);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean isMouseClicked(int button) {
        return ImGui.isMouseClicked(button);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public FloatVector2 getCursorScreenPos() {
        return new FloatVector2(ImGui.getCursorScreenPosX(), ImGui.getCursorScreenPosY());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public boolean invisibleButton(String id, float width, float height) {
        return ImGui.invisibleButton(id, width, height);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public float getMouseWheel() {
        return ImGui.getIO().getMouseWheel();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void setCursorScreenPos(float x, float y) {
        ImGui.setCursorScreenPos(x, y);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiAccessor
    public void dummy(float width, float height) {
        ImGui.dummy(width, height);
    }
}
