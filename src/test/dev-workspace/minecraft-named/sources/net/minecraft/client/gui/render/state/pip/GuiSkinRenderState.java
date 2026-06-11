package net.minecraft.client.gui.render.state.pip;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/pip/GuiSkinRenderState.class */
public final class GuiSkinRenderState extends Record implements PictureInPictureRenderState {
    private final PlayerModel playerModel;
    private final Identifier texture;
    private final float rotationX;
    private final float rotationY;
    private final float pivotY;
    private final int x0;
    private final int y0;
    private final int x1;
    private final int y1;
    private final float scale;
    private final ScreenRectangle scissorArea;
    private final ScreenRectangle bounds;

    public GuiSkinRenderState(PlayerModel $$0, Identifier $$1, float $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8, float $$9, ScreenRectangle $$10, ScreenRectangle $$11) {
        this.playerModel = $$0;
        this.texture = $$1;
        this.rotationX = $$2;
        this.rotationY = $$3;
        this.pivotY = $$4;
        this.x0 = $$5;
        this.y0 = $$6;
        this.x1 = $$7;
        this.y1 = $$8;
        this.scale = $$9;
        this.scissorArea = $$10;
        this.bounds = $$11;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GuiSkinRenderState.class), GuiSkinRenderState.class, "playerModel;texture;rotationX;rotationY;pivotY;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->playerModel:Lnet/minecraft/client/model/player/PlayerModel;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->rotationX:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->rotationY:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->pivotY:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GuiSkinRenderState.class), GuiSkinRenderState.class, "playerModel;texture;rotationX;rotationY;pivotY;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->playerModel:Lnet/minecraft/client/model/player/PlayerModel;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->rotationX:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->rotationY:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->pivotY:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GuiSkinRenderState.class, Object.class), GuiSkinRenderState.class, "playerModel;texture;rotationX;rotationY;pivotY;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->playerModel:Lnet/minecraft/client/model/player/PlayerModel;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->rotationX:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->rotationY:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->pivotY:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiSkinRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public PlayerModel playerModel() {
        return this.playerModel;
    }

    public Identifier texture() {
        return this.texture;
    }

    public float rotationX() {
        return this.rotationX;
    }

    public float rotationY() {
        return this.rotationY;
    }

    public float pivotY() {
        return this.pivotY;
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

    public GuiSkinRenderState(PlayerModel $$0, Identifier $$1, float $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8, float $$9, ScreenRectangle $$10) {
        this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, PictureInPictureRenderState.getBounds($$5, $$6, $$7, $$8, $$10));
    }
}
