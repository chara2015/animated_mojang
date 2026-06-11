package net.labymod.core.client.gfx.imgui.window;

import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.control.ControlEntry;
import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.util.KeyValue;
import net.labymod.core.client.gfx.imgui.control.DefaultControlEntryRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/window/ControlImGuiWindow.class */
public class ControlImGuiWindow extends ImGuiWindow {
    private final LabyConfig labyConfig;

    public ControlImGuiWindow() {
        super("Control", null, 2097152);
        this.labyConfig = Laby.labyAPI().config();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void prepareWindow() {
        super.prepareWindow();
        LabyImGui.setNextWindowPosAndSize(150.0f, 10.0f, 300.0f, 400.0f);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void renderContent() {
        LabyImGui.text(Constants.Branding.NAME);
        ControlEntryRegistry controlEntryRegistry = Laby.references().controlEntryRegistry();
        for (KeyValue<ControlEntry> element : controlEntryRegistry.getElements()) {
            ControlEntry value = element.getValue();
            ImGuiBooleanType shown = value.getVisible();
            LabyImGui.checkbox("Show " + value.getName(), shown);
            ((DefaultControlEntryRegistry) controlEntryRegistry).setState(value.getWindow());
            if (shown.get()) {
                value.getWindow().render();
            }
        }
        LabyImGui.separator();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected boolean showContentIfCollapsed() {
        return true;
    }
}
