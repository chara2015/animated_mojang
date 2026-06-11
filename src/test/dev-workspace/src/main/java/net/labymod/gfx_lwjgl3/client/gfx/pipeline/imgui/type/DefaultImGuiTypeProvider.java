package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import javax.inject.Singleton;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType;
import net.labymod.api.client.gfx.imgui.type.ImGuiFloatType;
import net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType;
import net.labymod.api.client.gfx.imgui.type.ImGuiLongType;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/type/DefaultImGuiTypeProvider.class */
@Singleton
@Implements(ImGuiTypeProvider.class)
public class DefaultImGuiTypeProvider implements ImGuiTypeProvider {
    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiBooleanType booleanType() {
        return new DefaultImGuiBooleanType();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiBooleanType booleanType(boolean value) {
        return new DefaultImGuiBooleanType(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiBooleanType booleanType(ImGuiBooleanType other) {
        return new DefaultImGuiBooleanType(other);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiDoubleType doubleType() {
        return new DefaultImGuiDoubleType();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiDoubleType doubleType(double value) {
        return new DefaultImGuiDoubleType(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiDoubleType doubleType(ImGuiDoubleType other) {
        return new DefaultImGuiDoubleType(other);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiFloatType floatType() {
        return new DefaultImGuiFloatType();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiFloatType floatType(float value) {
        return new DefaultImGuiFloatType(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiFloatType floatType(ImGuiFloatType other) {
        return new DefaultImGuiFloatType(other);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiIntegerType integerType() {
        return new DefaultImGuiIntegerType();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiIntegerType integerType(int value) {
        return new DefaultImGuiIntegerType(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiIntegerType integerType(ImGuiIntegerType other) {
        return new DefaultImGuiIntegerType(other);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiLongType longType() {
        return new DefaultImGuiLongType();
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiLongType longType(long value) {
        return new DefaultImGuiLongType(value);
    }

    @Override // net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider
    public ImGuiLongType longType(ImGuiLongType other) {
        return new DefaultImGuiLongType(other);
    }
}
