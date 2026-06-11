package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.state.states.GuiRectangleRenderState;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.shaders.block.CircleDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.CircleDataUniformBlockData;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiCircleRenderState.class */
public class GuiCircleRenderState extends GuiRectangleRenderState {
    private static final float CIRCLE_SEGMENT_STEP = 0.0027777778f;
    private static final float DEFAULT_STARTING_ANGLE = 0.5f;
    private final float centerX;
    private final float centerY;
    private final float innerRadius;
    private final float outerRadius;
    private final float startingAngle;
    private final float endingAngle;

    public GuiCircleRenderState(Material material, Matrix4f pose, float x, float y, GuiRectangleRenderState.RectConfig config, float innerRadius, float outerRadius, float startingAngle, float endingAngle, @Nullable ScissorArea scissorArea) {
        super(material, pose, x - outerRadius, y - outerRadius, outerRadius * 2.0f, outerRadius * 2.0f, config, scissorArea);
        if (config.getRoundedData() != null) {
            throw new IllegalArgumentException("Cannot use rounded data for circle render states.");
        }
        this.centerX = x;
        this.centerY = y;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.startingAngle = startingAngle;
        this.endingAngle = endingAngle;
    }

    @Override // net.labymod.api.client.gui.screen.state.states.GuiRectangleRenderState, net.labymod.api.client.gui.screen.state.GuiRenderState
    public boolean shouldDirectRecord() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.state.states.GuiRectangleRenderState, net.labymod.api.client.gui.screen.state.GuiRenderState
    public void consumeCommand(@NotNull DrawCommandContext context) {
        super.consumeCommand(context);
        Matrix4f pose = pose();
        Vector3f pos = pose.transformPosition(this.centerX, this.centerY, 0.0f, new Vector3f());
        float outerRadius = this.outerRadius * pose.m00();
        float innerRadius = this.innerRadius * pose.m00();
        CircleDataUniformBlockData data = new CircleDataUniformBlockData();
        data.circleCenter().set(pos.x(), pos.y());
        data.setCircleRadius(outerRadius);
        data.setCircleInnerRadius(outerRadius - innerRadius);
        data.setCircleStartAngle(0.5f + (CIRCLE_SEGMENT_STEP * this.startingAngle));
        data.setCircleEndAngle(CIRCLE_SEGMENT_STEP * this.endingAngle);
        CommandBuffer cmd = context.commandBuffer();
        cmd.bindUniformBlock(CircleDataUniformBlock.NAME, Laby.references().laby3D().circleData());
        cmd.bindUniformBlockData(CircleDataUniformBlock.NAME, data);
    }
}
