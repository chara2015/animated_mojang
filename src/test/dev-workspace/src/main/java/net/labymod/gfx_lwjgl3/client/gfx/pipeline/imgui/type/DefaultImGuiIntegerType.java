package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import imgui.type.ImInt;
import net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/type/DefaultImGuiIntegerType.class */
public class DefaultImGuiIntegerType implements ImGuiIntegerType {
    private final ImInt value;

    public DefaultImGuiIntegerType() {
        this(new ImInt());
    }

    public DefaultImGuiIntegerType(int value) {
        this(new ImInt(value));
    }

    public DefaultImGuiIntegerType(ImInt value) {
        this.value = value;
    }

    public DefaultImGuiIntegerType(@NotNull ImGuiIntegerType other) {
        this(new ImInt((ImInt) other.asImGuiType()));
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType
    public int get() {
        return this.value.get();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType
    public void set(int value) {
        this.value.set(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeAccessor
    public <T> T asImGuiType() {
        return (T) this.value;
    }
}
