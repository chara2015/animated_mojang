package net.labymod.api.client.gui.screen.state.offscreen;

import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/AbstractOffscreenRenderState.class */
public abstract class AbstractOffscreenRenderState implements OffscreenRenderState {
    private final Matrix4f pose;
    private final Rectangle bounds;
    private final float scale;
    private final RoundedData roundedData;

    protected AbstractOffscreenRenderState(Matrix4f pose, float left, float top, float right, float bottom, float scale, @Nullable RoundedData roundedData) {
        this(pose, Rectangle.absolute(left, top, right, bottom), scale, roundedData);
    }

    protected AbstractOffscreenRenderState(Matrix4f pose, Rectangle bounds, float scale, @Nullable RoundedData roundedData) {
        this.pose = pose;
        this.bounds = bounds;
        this.scale = scale;
        this.roundedData = roundedData;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiTransform
    public Matrix4f pose() {
        return this.pose;
    }

    @Override // net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderState
    public float getScale() {
        return this.scale;
    }

    @Override // net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderState
    @Nullable
    public RoundedData getRoundedData() {
        return this.roundedData;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    public Rectangle bounds() {
        return this.bounds;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    @Nullable
    public final ScissorArea getScissorArea() {
        return null;
    }
}
