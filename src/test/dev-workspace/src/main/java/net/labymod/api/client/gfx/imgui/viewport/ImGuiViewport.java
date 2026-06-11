package net.labymod.api.client.gfx.imgui.viewport;

import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/viewport/ImGuiViewport.class */
public interface ImGuiViewport {
    int getId();

    void setId(int i);

    int getFlags();

    void setFlags(int i);

    void addFlags(int i);

    void removeFlags(int i);

    boolean hasFlags(int i);

    FloatVector2 position();

    FloatVector2 size();

    int getParentViewportId();

    void setParentViewportId(int i);

    long getWindowPointer();

    FloatVector2 center();

    default float getX() {
        return position().getX();
    }

    default float getY() {
        return position().getY();
    }

    default float getWidth() {
        return size().getX();
    }

    default float getHeight() {
        return size().getY();
    }
}
