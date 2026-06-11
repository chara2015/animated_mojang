package net.labymod.v26_2_snapshot_8.client.gui;

import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.joml.Matrix3x2fStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gui/GuiGraphicsAccessor.class */
public interface GuiGraphicsAccessor {
    void setPose(Matrix3x2fStack matrix3x2fStack);

    void setGuiRenderState(GuiRenderState guiRenderState);
}
