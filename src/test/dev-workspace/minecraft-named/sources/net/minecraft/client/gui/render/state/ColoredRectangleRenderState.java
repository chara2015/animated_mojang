package net.minecraft.client.gui.render.state;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import org.joml.Matrix3x2fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/ColoredRectangleRenderState.class */
public final class ColoredRectangleRenderState extends Record implements GuiElementRenderState {
    private final RenderPipeline pipeline;
    private final TextureSetup textureSetup;
    private final Matrix3x2fc pose;
    private final int x0;
    private final int y0;
    private final int x1;
    private final int y1;
    private final int col1;
    private final int col2;
    private final ScreenRectangle scissorArea;
    private final ScreenRectangle bounds;

    public ColoredRectangleRenderState(RenderPipeline $$0, TextureSetup $$1, Matrix3x2fc $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, ScreenRectangle $$9, ScreenRectangle $$10) {
        this.pipeline = $$0;
        this.textureSetup = $$1;
        this.pose = $$2;
        this.x0 = $$3;
        this.y0 = $$4;
        this.x1 = $$5;
        this.y1 = $$6;
        this.col1 = $$7;
        this.col2 = $$8;
        this.scissorArea = $$9;
        this.bounds = $$10;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ColoredRectangleRenderState.class), ColoredRectangleRenderState.class, "pipeline;textureSetup;pose;x0;y0;x1;y1;col1;col2;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->col1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->col2:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ColoredRectangleRenderState.class), ColoredRectangleRenderState.class, "pipeline;textureSetup;pose;x0;y0;x1;y1;col1;col2;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->col1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->col2:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ColoredRectangleRenderState.class, Object.class), ColoredRectangleRenderState.class, "pipeline;textureSetup;pose;x0;y0;x1;y1;col1;col2;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->col1:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->col2:I", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/ColoredRectangleRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public RenderPipeline pipeline() {
        return this.pipeline;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public TextureSetup textureSetup() {
        return this.textureSetup;
    }

    public Matrix3x2fc pose() {
        return this.pose;
    }

    public int x0() {
        return this.x0;
    }

    public int y0() {
        return this.y0;
    }

    public int x1() {
        return this.x1;
    }

    public int y1() {
        return this.y1;
    }

    public int col1() {
        return this.col1;
    }

    public int col2() {
        return this.col2;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public ScreenRectangle scissorArea() {
        return this.scissorArea;
    }

    @Override // net.minecraft.client.gui.render.state.ScreenArea
    public ScreenRectangle bounds() {
        return this.bounds;
    }

    public ColoredRectangleRenderState(RenderPipeline $$0, TextureSetup $$1, Matrix3x2fc $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, ScreenRectangle $$9) {
        this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, getBounds($$3, $$4, $$5, $$6, $$2, $$9));
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public void buildVertices(VertexConsumer $$0) {
        $$0.addVertexWith2DPose(pose(), x0(), y0()).setColor(col1());
        $$0.addVertexWith2DPose(pose(), x0(), y1()).setColor(col2());
        $$0.addVertexWith2DPose(pose(), x1(), y1()).setColor(col2());
        $$0.addVertexWith2DPose(pose(), x1(), y0()).setColor(col1());
    }

    private static ScreenRectangle getBounds(int $$0, int $$1, int $$2, int $$3, Matrix3x2fc $$4, ScreenRectangle $$5) {
        ScreenRectangle $$6 = new ScreenRectangle($$0, $$1, $$2 - $$0, $$3 - $$1).transformMaxBounds($$4);
        return $$5 != null ? $$5.intersection($$6) : $$6;
    }
}
