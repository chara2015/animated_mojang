package net.labymod.api.client.gfx.imgui.control;

import java.util.function.Function;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/control/ControlEntryRegistry.class */
@Referenceable
public interface ControlEntryRegistry extends Registry<ControlEntry> {
    void registerEntry(boolean z, Function<ImGuiBooleanType, ImGuiWindow> function);

    default boolean isVisible(String id) {
        if (!DebugRegistry.DEBUG_WINDOWS.isEnabled()) {
            return false;
        }
        ControlEntry controlEntry = getById(id);
        if (controlEntry == null) {
            return true;
        }
        return controlEntry.getVisible().get();
    }
}
