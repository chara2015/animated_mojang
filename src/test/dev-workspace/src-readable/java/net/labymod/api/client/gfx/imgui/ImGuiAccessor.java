package net.labymod.api.client.gfx.imgui;

import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/ImGuiAccessor.class */
@Referenceable
public interface ImGuiAccessor {
    void setNextWindowPos(float f, float f2, int i);

    void setNextWindowSize(float f, float f2, int i);

    void setNextWindowPosAndSize(float f, float f2, float f3, float f4);

    ImGuiViewport getMainViewport();

    ImGuiViewport getWindowViewport();

    boolean beginWindow(String str);

    boolean beginWindow(String str, @Nullable ImGuiBooleanType imGuiBooleanType, int i);

    void text(String str);

    void text(String str, int i);

    void text(String str, int i, int i2, int i3, int i4);

    void text(String str, float f, float f2, float f3, float f4);

    void text(String str, int i, int i2);

    FloatVector2 calculateTextSize(String str);

    boolean colorPicker4(String str, float[] fArr);

    void addRectangle(float f, float f2, float f3, float f4, int i, float f5);

    void separator();

    boolean collapsingHeader(String str);

    boolean collapsingHeader(String str, int i);

    boolean collapsingHeader(String str, ImGuiBooleanType imGuiBooleanType);

    boolean collapsingHeader(String str, ImGuiBooleanType imGuiBooleanType, int i);

    void pushTree();

    void popTree();

    boolean button(String str);

    boolean button(String str, float f, float f2);

    boolean checkbox(String str, ImGuiBooleanType imGuiBooleanType);

    void beginTooltip();

    void setTooltip(String str);

    void endTooltip();

    boolean isItemHovered();

    void sameLine();

    void sameLine(float f);

    void sameLine(float f, float f2);

    boolean isItemClicked();

    void beginGroup();

    void endGroup();

    void endWindow();

    boolean treeNodeEx(String str, int i);

    void setNextWindowSizeConstraints(float f, float f2, float f3, float f4);

    void renderBounds(Bounds bounds);

    void pushId(int i);

    void pushId(String str);

    void popId();

    void pushStyleColor(int i, int i2);

    void pushStyleColor(int i, int i2, int i3, int i4, int i5);

    void pushStyleColor(int i, float f, float f2, float f3, float f4);

    void popStyleColor();

    FloatVector2 getWindowContentRegionMin();

    FloatVector2 getWindowContentRegionMax();

    FloatVector2 getWindowPosition();

    boolean beginCombo(String str, String str2);

    boolean beginCombo(String str, String str2, int i);

    void endCombo();

    void setItemDefaultFocus();

    boolean selectable(String str, boolean z);

    void addRectangleFilled(float f, float f2, float f3, float f4, int i, float f5);

    void image(int i, float f, float f2);

    void image(ResourceLocation resourceLocation, float f, float f2);

    void image(int i, float f, float f2, float f3, float f4);

    void image(int i, float f, float f2, float f3, float f4, float f5, float f6);

    void image(int i, float f, float f2, float f3, float f4, float f5, float f6, int i2);

    FloatVector2 getItemRectMin();

    FloatVector2 getItemRectMax();

    boolean isKeyPressed(Key key);

    boolean isKeyPressed(Key key, boolean z);

    boolean isKeyReleased(Key key);

    boolean isKeyDown(Key key);

    boolean isMouseClicked(Key key);

    boolean isWindowHovered();

    boolean isWindowFocused();

    FloatVector2 getMousePos();

    boolean isMouseDown(int i);

    boolean isMouseClicked(int i);

    FloatVector2 getCursorScreenPos();

    boolean invisibleButton(String str, float f, float f2);

    float getMouseWheel();

    void setCursorScreenPos(float f, float f2);

    void dummy(float f, float f2);
}
