package net.minecraft.client.gui.render.state.pip;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/pip/GuiEntityRenderState.class */
public final class GuiEntityRenderState extends Record implements PictureInPictureRenderState {
    private final EntityRenderState renderState;
    private final Vector3f translation;
    private final Quaternionf rotation;
    private final Quaternionf overrideCameraAngle;
    private final int x0;
    private final int y0;
    private final int x1;
    private final int y1;
    private final float scale;
    private final ScreenRectangle scissorArea;
    private final ScreenRectangle bounds;

    public GuiEntityRenderState(EntityRenderState $$0, Vector3f $$1, Quaternionf $$2, Quaternionf $$3, int $$4, int $$5, int $$6, int $$7, float $$8, ScreenRectangle $$9, ScreenRectangle $$10) {
        this.renderState = $$0;
        this.translation = $$1;
        this.rotation = $$2;
        this.overrideCameraAngle = $$3;
        this.x0 = $$4;
        this.y0 = $$5;
        this.x1 = $$6;
        this.y1 = $$7;
        this.scale = $$8;
        this.scissorArea = $$9;
        this.bounds = $$10;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GuiEntityRenderState.class), GuiEntityRenderState.class, "renderState;translation;rotation;overrideCameraAngle;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->renderState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->translation:Lorg/joml/Vector3f;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->rotation:Lorg/joml/Quaternionf;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->overrideCameraAngle:Lorg/joml/Quaternionf;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GuiEntityRenderState.class), GuiEntityRenderState.class, "renderState;translation;rotation;overrideCameraAngle;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->renderState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->translation:Lorg/joml/Vector3f;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->rotation:Lorg/joml/Quaternionf;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->overrideCameraAngle:Lorg/joml/Quaternionf;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GuiEntityRenderState.class, Object.class), GuiEntityRenderState.class, "renderState;translation;rotation;overrideCameraAngle;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->renderState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->translation:Lorg/joml/Vector3f;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->rotation:Lorg/joml/Quaternionf;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->overrideCameraAngle:Lorg/joml/Quaternionf;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiEntityRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public EntityRenderState renderState() {
        return this.renderState;
    }

    public Vector3f translation() {
        return this.translation;
    }

    public Quaternionf rotation() {
        return this.rotation;
    }

    public Quaternionf overrideCameraAngle() {
        return this.overrideCameraAngle;
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
        return this.scale;
    }

    @Override // net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState
    public ScreenRectangle scissorArea() {
        return this.scissorArea;
    }

    @Override // net.minecraft.client.gui.render.state.ScreenArea
    public ScreenRectangle bounds() {
        return this.bounds;
    }

    public GuiEntityRenderState(EntityRenderState $$0, Vector3f $$1, Quaternionf $$2, Quaternionf $$3, int $$4, int $$5, int $$6, int $$7, float $$8, ScreenRectangle $$9) {
        this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, PictureInPictureRenderState.getBounds($$4, $$5, $$6, $$7, $$9));
    }
}
