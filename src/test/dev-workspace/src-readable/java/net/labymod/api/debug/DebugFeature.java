package net.labymod.api.debug;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gui.screen.key.Key;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/debug/DebugFeature.class */
public class DebugFeature {
    private final String name;
    private final Key key;
    private final BooleanSupplier permissionCheck;
    private final ImGuiBooleanType state;
    private final List<BooleanConsumer> listeners;

    public DebugFeature(String name, Key key) {
        this(name, key, () -> {
            return true;
        });
    }

    public DebugFeature(String name, Key key, BooleanSupplier permissionCheck) {
        this.listeners = new ArrayList();
        this.name = name;
        this.key = key;
        this.permissionCheck = permissionCheck;
        this.state = LabyImGui.booleanType();
    }

    public void addListener(BooleanConsumer listener) {
        this.listeners.add(listener);
    }

    public void toggleState(Key key) {
        if (this.permissionCheck.getAsBoolean() && key == this.key) {
            _toggleState();
        }
    }

    public void toggleState() {
        if (!this.permissionCheck.getAsBoolean()) {
            return;
        }
        _toggleState();
    }

    private void _toggleState() {
        this.state.set(!this.state.get());
        for (BooleanConsumer listener : this.listeners) {
            listener.accept(this.state.get());
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.permissionCheck.getAsBoolean() && this.state.get();
    }

    public ImGuiBooleanType getState() {
        return this.state;
    }
}
