package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.screen.state.ClipShape;
import net.labymod.api.client.gui.screen.state.GuiRenderState;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/AbstractGuiRenderState.class */
public abstract class AbstractGuiRenderState implements GuiRenderState {
    private static final RenderEnvironmentContext ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
    private final Material material;
    private final Matrix4f pose;
    private final Rectangle bounds;

    @Nullable
    private final ScissorArea scissorArea;

    @Nullable
    private final ClipShape clipShape;
    protected float left;
    protected float top;
    protected float right;
    protected float bottom;

    protected AbstractGuiRenderState(Material material, Matrix4f pose, float x, float y, float width, float height, @Nullable ScissorArea scissorArea) {
        this.material = material;
        this.pose = pose;
        this.bounds = Rectangle.relative(x, y, width, height).transformedAabb(pose);
        this.left = x;
        this.top = y;
        this.right = x + width;
        this.bottom = y + height;
        this.scissorArea = scissorArea;
        RenderAttributes attributes = ENVIRONMENT_CONTEXT.renderAttributesStack().last();
        this.clipShape = attributes.getClipShape();
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    @NotNull
    public Material material() {
        return this.material;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    public Rectangle bounds() {
        return this.bounds;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    @Nullable
    public ScissorArea getScissorArea() {
        return this.scissorArea;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    @Nullable
    public ClipShape clipShape() {
        return this.clipShape;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiTransform
    public Matrix4f pose() {
        return this.pose;
    }
}
