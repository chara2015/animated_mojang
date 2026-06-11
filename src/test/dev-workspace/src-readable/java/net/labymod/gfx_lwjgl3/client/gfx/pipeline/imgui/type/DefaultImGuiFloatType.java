package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import imgui.type.ImFloat;
import net.labymod.api.client.gfx.imgui.type.ImGuiFloatType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/type/DefaultImGuiFloatType.class */
public class DefaultImGuiFloatType implements ImGuiFloatType {
    private final ImFloat value;

    public DefaultImGuiFloatType() {
        this(new ImFloat());
    }

    public DefaultImGuiFloatType(float value) {
        this(new ImFloat(value));
    }

    public DefaultImGuiFloatType(ImFloat container) {
        this.value = container;
    }

    public DefaultImGuiFloatType(@NotNull ImGuiFloatType other) {
        this(new ImFloat((ImFloat) other.asImGuiType()));
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiFloatType
    public float get() {
        return this.value.get();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiFloatType
    public void set(float value) {
        this.value.set(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeAccessor
    public <T> T asImGuiType() {
        return (T) this.value;
    }
}
