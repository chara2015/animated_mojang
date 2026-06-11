package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.ColorUtil;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiBlitRenderState.class */
public class GuiBlitRenderState extends AbstractGuiRenderState {
    private final float minU;
    private final float minV;
    private final float maxU;
    private final float maxV;
    private final int color;

    @Deprecated(forRemoval = true, since = "4.3.37")
    public GuiBlitRenderState(RenderState renderState, Matrix4f pose, GuiTextureSet guiTextureSet, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int color, @Nullable ScissorArea scissorArea) {
        this(GuiMaterial.fromLegacy(renderState, guiTextureSet), pose, x, y, width, height, minU, minV, maxU, maxV, color, scissorArea);
    }

    public GuiBlitRenderState(Material material, Matrix4f pose, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int color, @Nullable ScissorArea scissorArea) {
        super(material, pose, x, y, width, height, scissorArea);
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
        this.color = ColorUtil.combineAlpha(color);
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void buildVertices(VertexConsumer consumer) {
        Matrix4f pose = pose();
        consumer.addVertex2D(pose, this.left, this.top).setUv(this.minU, this.minV).setColor(this.color);
        consumer.addVertex2D(pose, this.left, this.bottom).setUv(this.minU, this.maxV).setColor(this.color);
        consumer.addVertex2D(pose, this.right, this.bottom).setUv(this.maxU, this.maxV).setColor(this.color);
        consumer.addVertex2D(pose, this.right, this.top).setUv(this.maxU, this.minV).setColor(this.color);
    }
}
