package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import imgui.type.ImLong;
import net.labymod.api.client.gfx.imgui.type.ImGuiLongType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/type/DefaultImGuiLongType.class */
public class DefaultImGuiLongType implements ImGuiLongType {
    private final ImLong value;

    public DefaultImGuiLongType() {
        this(new ImLong());
    }

    public DefaultImGuiLongType(long value) {
        this(new ImLong(value));
    }

    public DefaultImGuiLongType(ImLong value) {
        this.value = value;
    }

    public DefaultImGuiLongType(@NotNull ImGuiLongType other) {
        this(new ImLong((ImLong) other.asImGuiType()));
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiLongType
    public long get() {
        return this.value.get();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiLongType
    public void set(long value) {
        this.value.set(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeAccessor
    public <T> T asImGuiType() {
        return (T) this.value;
    }
}
