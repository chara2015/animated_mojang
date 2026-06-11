package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.ColorUtil;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiRectangleOutlineRenderState.class */
public class GuiRectangleOutlineRenderState extends AbstractGuiRenderState {
    private final int innerColor;
    private final int outerColor;
    private final float thickness;

    public GuiRectangleOutlineRenderState(Material material, Matrix4f pose, float x, float y, float width, float height, int innerColor, int outerColor, float thickness, @Nullable ScissorArea scissorArea) {
        super(material, pose, x, y, width, height, scissorArea);
        this.innerColor = ColorUtil.combineAlpha(innerColor);
        this.outerColor = ColorUtil.combineAlpha(outerColor);
        this.thickness = thickness;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void buildVertices(VertexConsumer consumer) {
        Matrix4f pose = pose();
        consumer.addVertex2D(pose, this.left - this.thickness, this.bottom + this.thickness).setBlankUv().setColor(this.outerColor).addVertex2D(pose, this.left, this.bottom).setBlankUv().setColor(this.innerColor).addVertex2D(pose, this.left, this.top).setBlankUv().setColor(this.innerColor).addVertex2D(pose, this.left - this.thickness, this.top - this.thickness).setBlankUv().setColor(this.outerColor);
        consumer.addVertex2D(pose, this.right, this.bottom).setBlankUv().setColor(this.innerColor).addVertex2D(pose, this.right + this.thickness, this.bottom + this.thickness).setBlankUv().setColor(this.outerColor).addVertex2D(pose, this.right + this.thickness, this.top - this.thickness).setBlankUv().setColor(this.outerColor).addVertex2D(pose, this.right, this.top).setBlankUv().setColor(this.innerColor);
        consumer.addVertex2D(pose, this.left, this.top).setBlankUv().setColor(this.innerColor).addVertex2D(pose, this.right, this.top).setBlankUv().setColor(this.innerColor).addVertex2D(pose, this.right + this.thickness, this.top - this.thickness).setBlankUv().setColor(this.outerColor).addVertex2D(pose, this.left - this.thickness, this.top - this.thickness).setBlankUv().setColor(this.outerColor);
        consumer.addVertex2D(pose, this.left - this.thickness, this.bottom + this.thickness).setBlankUv().setColor(this.outerColor).addVertex2D(pose, this.right + this.thickness, this.bottom + this.thickness).setBlankUv().setColor(this.outerColor).addVertex2D(pose, this.right, this.bottom).setBlankUv().setColor(this.innerColor).addVertex2D(pose, this.left, this.bottom).setBlankUv().setColor(this.innerColor);
    }
}
