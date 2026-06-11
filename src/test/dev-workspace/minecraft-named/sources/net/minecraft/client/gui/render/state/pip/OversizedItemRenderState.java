package net.minecraft.client.gui.render.state.pip;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.GuiItemRenderState;
import org.joml.Matrix3x2f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/pip/OversizedItemRenderState.class */
public final class OversizedItemRenderState extends Record implements PictureInPictureRenderState {
    private final GuiItemRenderState guiItemRenderState;
    private final int x0;
    private final int y0;
    private final int x1;
    private final int y1;

    public OversizedItemRenderState(GuiItemRenderState $$0, int $$1, int $$2, int $$3, int $$4) {
        this.guiItemRenderState = $$0;
        this.x0 = $$1;
        this.y0 = $$2;
        this.x1 = $$3;
        this.y1 = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, OversizedItemRenderState.class), OversizedItemRenderState.class, "guiItemRenderState;x0;y0;x1;y1", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->guiItemRenderState:Lnet/minecraft/client/gui/render/state/GuiItemRenderState;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->y1:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, OversizedItemRenderState.class), OversizedItemRenderState.class, "guiItemRenderState;x0;y0;x1;y1", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->guiItemRenderState:Lnet/minecraft/client/gui/render/state/GuiItemRenderState;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->y1:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, OversizedItemRenderState.class, Object.class), OversizedItemRenderState.class, "guiItemRenderState;x0;y0;x1;y1", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->guiItemRenderState:Lnet/minecraft/client/gui/render/state/GuiItemRenderState;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/OversizedItemRenderState;->y1:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public GuiItemRenderState guiItemRenderState() {
        return this.guiItemRenderState;
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public int x0() {
        return this.x0;
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public int y0() {
        return this.y0;
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public int x1() {
        return this.x1;
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public int y1() {
        return this.y1;
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public float scale() {
        return 16.0f;
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public Matrix3x2f pose() {
        return this.guiItemRenderState.pose();
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public ScreenRectangle scissorArea() {
        return this.guiItemRenderState.scissorArea();
    }

    @Override // net.minecraft.client.gui.render.state.ScreenArea
    public ScreenRectangle bounds() {
        return this.guiItemRenderState.bounds();
    }
}
