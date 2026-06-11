package net.labymod.core.client.gfx.imgui.window;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.client.gfx.imgui.window.DocumentHandler;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/window/BoundsImGuiWindow.class */
public class BoundsImGuiWindow extends ImGuiWindow {
    public BoundsImGuiWindow(@Nullable ImGuiBooleanType visible) {
        super("Bounds", visible, 0);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void prepareWindow() {
        super.prepareWindow();
        ImGuiViewport windowViewport = LabyImGui.getWindowViewport();
        FloatVector2 windowPosition = windowViewport.position();
        float x = windowPosition.getX();
        float y = windowPosition.getY();
        LabyImGui.setNextWindowPosAndSize(x + windowViewport.getWidth() + 205.0f, y, 200.0f, 300.0f);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void renderContent() {
        DocumentHandler documentHandler = Laby.references().documentHandler();
        Widget selectedWidget = documentHandler.getSelectedWidget();
        if (selectedWidget == null) {
            LabyImGui.text("No widget is selected");
        } else {
            LabyImGui.renderBounds(selectedWidget.bounds());
        }
    }
}
