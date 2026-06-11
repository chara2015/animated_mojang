package net.labymod.v26_1.client.gui;

import net.minecraft.network.chat.Style;
import org.joml.Matrix3x2fStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/GuiGraphicsAccessor.class */
public interface GuiGraphicsAccessor {
    void setPose(Matrix3x2fStack matrix3x2fStack);

    void labyMod$renderHoverEffect(Style style, int i, int i2);
}
