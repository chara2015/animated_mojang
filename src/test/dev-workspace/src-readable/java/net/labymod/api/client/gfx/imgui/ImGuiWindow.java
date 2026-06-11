package net.labymod.api.client.gfx.imgui;

import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/ImGuiWindow.class */
public abstract class ImGuiWindow {
    private final String title;
    private final ImGuiBooleanType visible;
    private final int flags;

    protected abstract void renderContent();

    public ImGuiWindow(String title, @Nullable ImGuiBooleanType visible, int flags) {
        this.title = title;
        this.visible = visible;
        int newFlags = flags | 8192;
        this.flags = newFlags;
    }

    public final void render() {
        prepareWindow();
        boolean opened = LabyImGui.beginWindow(this.title, this.visible, this.flags);
        if (showContentIfCollapsed() || opened) {
            renderContent();
        }
        LabyImGui.endWindow();
    }

    public ImGuiBooleanType getVisible() {
        return this.visible;
    }

    public String getTitle() {
        return this.title;
    }

    protected void prepareWindow() {
        ImGuiViewport windowViewport = LabyImGui.getWindowViewport();
        FloatVector2 windowPosition = windowViewport.position();
        float x = windowPosition.getX();
        float y = windowPosition.getY();
        LabyImGui.setNextWindowPosAndSize(x + windowViewport.getWidth() + 5.0f, y, 200.0f, 300.0f);
    }

    protected boolean showContentIfCollapsed() {
        return false;
    }
}
