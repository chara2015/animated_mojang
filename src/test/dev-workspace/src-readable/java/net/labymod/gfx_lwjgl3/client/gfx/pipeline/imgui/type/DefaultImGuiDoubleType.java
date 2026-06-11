package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import imgui.type.ImDouble;
import net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/type/DefaultImGuiDoubleType.class */
public class DefaultImGuiDoubleType implements ImGuiDoubleType {
    private final ImDouble value;

    public DefaultImGuiDoubleType() {
        this(new ImDouble());
    }

    public DefaultImGuiDoubleType(double value) {
        this(new ImDouble(value));
    }

    public DefaultImGuiDoubleType(ImDouble value) {
        this.value = value;
    }

    public DefaultImGuiDoubleType(@NotNull ImGuiDoubleType other) {
        this(new ImDouble((ImDouble) other.asImGuiType()));
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType
    public double get() {
        return this.value.get();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType
    public void set(double value) {
        this.value.set(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeAccessor
    public <T> T asImGuiType() {
        return (T) this.value;
    }
}
