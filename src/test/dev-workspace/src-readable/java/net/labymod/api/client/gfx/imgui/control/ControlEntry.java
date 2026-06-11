package net.labymod.api.client.gfx.imgui.control;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.service.Identifiable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/control/ControlEntry.class */
public class ControlEntry implements Identifiable {
    private final ImGuiBooleanType visible;
    private final String name;
    private final ImGuiWindow window;
    private final String id = buildId();

    public ControlEntry(ImGuiWindow window) {
        this.window = window;
        this.visible = window.getVisible();
        this.name = window.getTitle();
    }

    public ImGuiBooleanType getVisible() {
        return this.visible;
    }

    public String getName() {
        return this.name;
    }

    public ImGuiWindow getWindow() {
        return this.window;
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return this.id;
    }

    private String buildId() {
        String id = this.name;
        return Laby.labyAPI().getNamespace(this) + ":" + id.toLowerCase(Locale.ROOT).replace(' ', '_').replace('-', '_');
    }
}
