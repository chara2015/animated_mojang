package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.util.ColorUtil;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiTrapezoidRenderState.class */
public class GuiTrapezoidRenderState extends AbstractGuiRenderState {
    private static final Vector3f DEFAULT_NORMAL = new Vector3f(1.0f, 0.0f, 0.0f);
    private final float x;
    private final float y;
    private final float x1;
    private final float y1;
    private final float x2;
    private final float y2;
    private final float x3;
    private final float y3;
    private final int argb;

    public GuiTrapezoidRenderState(Matrix4f pose, float x, float y, float x1, float y1, float x2, float y2, float x3, float y3, int argb, @Nullable ScissorArea scissorArea) {
        super(GuiMaterial.untextured(RenderStates.TRIANGLE), pose, x, y, 1.0f, 1.0f, scissorArea);
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.argb = ColorUtil.combineAlpha(argb);
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void buildVertices(VertexConsumer consumer) {
        Matrix4f pose = pose();
        addVertex(consumer, pose, this.x, this.y, this.argb);
        addVertex(consumer, pose, this.x1, this.y1, this.argb);
        addVertex(consumer, pose, this.x2, this.y2, this.argb);
        addVertex(consumer, pose, this.x2, this.y2, this.argb);
        addVertex(consumer, pose, this.x3, this.y3, this.argb);
        addVertex(consumer, pose, this.x, this.y, this.argb);
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public boolean shouldDirectRecord() {
        return true;
    }

    private void addVertex(VertexConsumer consumer, Matrix4f pose, float x, float y, int argb) {
        consumer.addVertex2D(pose, x, y).setColor(argb).setNormal(DEFAULT_NORMAL);
    }
}
