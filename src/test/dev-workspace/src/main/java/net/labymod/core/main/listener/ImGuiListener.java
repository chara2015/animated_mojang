package net.labymod.core.main.listener;

import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.debug.ImGuiInitializeEvent;
import net.labymod.core.client.gfx.imgui.window.BoundsImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.CanvasDebugImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.DocumentImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.FrameProfilerImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.InstructionImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.OtherImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.RenderPauseImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.RenderStatisticsImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.VariablesImGuiWindow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/ImGuiListener.class */
public class ImGuiListener {
    @Subscribe
    public void onImGuiInitialize(ImGuiInitializeEvent event) {
        ControlEntryRegistry registry = event.registry();
        registry.registerEntry(true, BoundsImGuiWindow::new);
        registry.registerEntry(true, DocumentImGuiWindow::new);
        registry.registerEntry(true, VariablesImGuiWindow::new);
        registry.registerEntry(true, InstructionImGuiWindow::new);
        registry.registerEntry(false, RenderPauseImGuiWindow::new);
        registry.registerEntry(false, RenderStatisticsImGuiWindow::new);
        registry.registerEntry(false, FrameProfilerImGuiWindow::new);
        registry.registerEntry(false, CanvasDebugImGuiWindow::new);
        registry.registerEntry(false, OtherImGuiWindow::new);
    }
}
