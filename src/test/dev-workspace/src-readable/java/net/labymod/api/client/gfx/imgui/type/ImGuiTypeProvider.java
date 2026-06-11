package net.labymod.api.client.gfx.imgui.type;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/type/ImGuiTypeProvider.class */
@Referenceable
public interface ImGuiTypeProvider {
    ImGuiBooleanType booleanType();

    ImGuiBooleanType booleanType(boolean z);

    ImGuiBooleanType booleanType(ImGuiBooleanType imGuiBooleanType);

    ImGuiDoubleType doubleType();

    ImGuiDoubleType doubleType(double d);

    ImGuiDoubleType doubleType(ImGuiDoubleType imGuiDoubleType);

    ImGuiFloatType floatType();

    ImGuiFloatType floatType(float f);

    ImGuiFloatType floatType(ImGuiFloatType imGuiFloatType);

    ImGuiIntegerType integerType();

    ImGuiIntegerType integerType(int i);

    ImGuiIntegerType integerType(ImGuiIntegerType imGuiIntegerType);

    ImGuiLongType longType();

    ImGuiLongType longType(long j);

    ImGuiLongType longType(ImGuiLongType imGuiLongType);
}
