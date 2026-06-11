package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.viewport;

import imgui.ImVec2;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/viewport/DefaultImGuiViewport.class */
public class DefaultImGuiViewport implements ImGuiViewport {
    private final imgui.ImGuiViewport viewport;

    public DefaultImGuiViewport(imgui.ImGuiViewport viewport) {
        this.viewport = viewport;
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public int getId() {
        return this.viewport.getID();
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public void setId(int id) {
        this.viewport.setID(id);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public int getFlags() {
        return this.viewport.getFlags();
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public void setFlags(int flags) {
        this.viewport.setFlags(flags);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public void addFlags(int flags) {
        this.viewport.addFlags(flags);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public void removeFlags(int flags) {
        this.viewport.removeFlags(flags);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public boolean hasFlags(int flags) {
        return this.viewport.hasFlags(flags);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public FloatVector2 position() {
        ImVec2 pos = this.viewport.getPos();
        return new FloatVector2(pos.x, pos.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public FloatVector2 size() {
        ImVec2 size = this.viewport.getSize();
        return new FloatVector2(size.x, size.y);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public int getParentViewportId() {
        return this.viewport.getParentViewportId();
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public void setParentViewportId(int id) {
        this.viewport.setParentViewportId(id);
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public long getWindowPointer() {
        return this.viewport.getPlatformHandle();
    }

    @Override // net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport
    public FloatVector2 center() {
        ImVec2 center = this.viewport.getCenter();
        return new FloatVector2(center.x, center.y);
    }
}
