package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import imgui.type.ImBoolean;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/type/DefaultImGuiBooleanType.class */
public class DefaultImGuiBooleanType implements ImGuiBooleanType {
    private final ImBoolean value;

    public DefaultImGuiBooleanType() {
        this(new ImBoolean());
    }

    public DefaultImGuiBooleanType(boolean value) {
        this(new ImBoolean(value));
    }

    public DefaultImGuiBooleanType(ImBoolean value) {
        this.value = value;
    }

    public DefaultImGuiBooleanType(@NotNull ImGuiBooleanType other) {
        this(new ImBoolean((ImBoolean) other.asImGuiType()));
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType
    public boolean get() {
        return this.value.get();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType
    public void set(boolean value) {
        this.value.set(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeAccessor
    public <T> T asImGuiType() {
        return (T) this.value;
    }
}
