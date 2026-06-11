package net.minecraft.client.gui.render.state;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/TiledBlitRenderState.class */
public final class TiledBlitRenderState extends Record implements GuiElementRenderState {
    private final RenderPipeline pipeline;
    private final TextureSetup textureSetup;
    private final Matrix3x2f pose;
    private final int tileWidth;
    private final int tileHeight;
    private final int x0;
    private final int y0;
    private final int x1;
    private final int y1;
    private final float u0;
    private final float u1;
    private final float v0;
    private final float v1;
    private final int color;
    private final ScreenRectangle scissorArea;
    private final ScreenRectangle bounds;

    public TiledBlitRenderState(RenderPipeline $$0, TextureSetup $$1, Matrix3x2f $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, float $$9, float $$10, float $$11, float $$12, int $$13, ScreenRectangle $$14, ScreenRectangle $$15) {
        this.pipeline = $$0;
        this.textureSetup = $$1;
        this.pose = $$2;
        this.tileWidth = $$3;
        this.tileHeight = $$4;
        this.x0 = $$5;
        this.y0 = $$6;
        this.x1 = $$7;
        this.y1 = $$8;
        this.u0 = $$9;
        this.u1 = $$10;
        this.v0 = $$11;
        this.v1 = $$12;
        this.color = $$13;
        this.scissorArea = $$14;
        this.bounds = $$15;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TiledBlitRenderState.class), TiledBlitRenderState.class, "pipeline;textureSetup;pose;tileWidth;tileHeight;x0;y0;x1;y1;u0;u1;v0;v1;color;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->pose:Lorg/joml/Matrix3x2f;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->tileWidth:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->tileHeight:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->u0:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->u1:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->v0:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->v1:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->color:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TiledBlitRenderState.class), TiledBlitRenderState.class, "pipeline;textureSetup;pose;tileWidth;tileHeight;x0;y0;x1;y1;u0;u1;v0;v1;color;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->pose:Lorg/joml/Matrix3x2f;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->tileWidth:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->tileHeight:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->u0:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->u1:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->v0:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->v1:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->color:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TiledBlitRenderState.class, Object.class), TiledBlitRenderState.class, "pipeline;textureSetup;pose;tileWidth;tileHeight;x0;y0;x1;y1;u0;u1;v0;v1;color;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->pose:Lorg/joml/Matrix3x2f;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->tileWidth:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->tileHeight:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->u0:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->u1:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->v0:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->v1:F", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->color:I", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/TiledBlitRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public RenderPipeline pipeline() {
        return this.pipeline;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public TextureSetup textureSetup() {
        return this.textureSetup;
    }

    public Matrix3x2f pose() {
        return this.pose;
    }

    public int tileWidth() {
        return this.tileWidth;
    }

    public int tileHeight() {
        return this.tileHeight;
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

    public float u0() {
        return this.u0;
    }

    public float u1() {
        return this.u1;
    }

    public float v0() {
        return this.v0;
    }

    public float v1() {
        return this.v1;
    }

    public int color() {
        return this.color;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public ScreenRectangle scissorArea() {
        return this.scissorArea;
    }

    @Override // net.minecraft.client.gui.render.state.ScreenArea
    public ScreenRectangle bounds() {
        return this.bounds;
    }

    public TiledBlitRenderState(RenderPipeline $$0, TextureSetup $$1, Matrix3x2f $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, float $$9, float $$10, float $$11, float $$12, int $$13, ScreenRectangle $$14) {
        this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13, $$14, getBounds($$5, $$6, $$7, $$8, $$2, $$14));
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public void buildVertices(VertexConsumer $$0) {
        int $$7;
        float $$8;
        int $$13;
        float fLerp;
        int $$1 = x1() - x0();
        int $$2 = y1() - y0();
        int iTileWidth = 0;
        while (true) {
            int $$3 = iTileWidth;
            if ($$3 < $$1) {
                int $$4 = $$1 - $$3;
                if (tileWidth() <= $$4) {
                    $$7 = tileWidth();
                    $$8 = u1();
                } else {
                    $$7 = $$4;
                    $$8 = Mth.lerp($$4 / tileWidth(), u0(), u1());
                }
                int iTileHeight = 0;
                while (true) {
                    int $$9 = iTileHeight;
                    if ($$9 < $$2) {
                        int $$10 = $$2 - $$9;
                        if (tileHeight() <= $$10) {
                            $$13 = tileHeight();
                            fLerp = v1();
                        } else {
                            $$13 = $$10;
                            fLerp = Mth.lerp($$10 / tileHeight(), v0(), v1());
                        }
                        float $$14 = fLerp;
                        int $$15 = x0() + $$3;
                        int $$16 = x0() + $$3 + $$7;
                        int $$17 = y0() + $$9;
                        int $$18 = y0() + $$9 + $$13;
                        $$0.addVertexWith2DPose(pose(), $$15, $$17).setUv(u0(), v0()).setColor(color());
                        $$0.addVertexWith2DPose(pose(), $$15, $$18).setUv(u0(), $$14).setColor(color());
                        $$0.addVertexWith2DPose(pose(), $$16, $$18).setUv($$8, $$14).setColor(color());
                        $$0.addVertexWith2DPose(pose(), $$16, $$17).setUv($$8, v0()).setColor(color());
                        iTileHeight = $$9 + tileHeight();
                    }
                }
                iTileWidth = $$3 + tileWidth();
            } else {
                return;
            }
        }
    }

    private static ScreenRectangle getBounds(int $$0, int $$1, int $$2, int $$3, Matrix3x2f $$4, ScreenRectangle $$5) {
        ScreenRectangle $$6 = new ScreenRectangle($$0, $$1, $$2 - $$0, $$3 - $$1).transformMaxBounds($$4);
        return $$5 != null ? $$5.intersection($$6) : $$6;
    }
}
