package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.StateUtil;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.ColorUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiRoundedBlitRenderState.class */
public class GuiRoundedBlitRenderState extends GuiBlitRenderState {
    private final RoundedData roundedData;

    public GuiRoundedBlitRenderState(Material material, Matrix4f pose, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int color, RoundedData roundedData, @Nullable ScissorArea scissorArea) {
        super(material, pose, x, y, width, height, minU, minV, maxU, maxV, ColorUtil.combineAlpha(color), scissorArea);
        this.roundedData = roundedData;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public boolean shouldDirectRecord() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void consumeCommand(@NotNull DrawCommandContext context) {
        super.consumeCommand(context);
        StateUtil.applyRoundData(context.commandBuffer(), this.roundedData, pose(), this.left, this.top, this.right, this.bottom);
    }
}
