package net.minecraft.client.gui.render.state.pip;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.model.object.book.BookModel;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/pip/GuiBookModelRenderState.class */
public final class GuiBookModelRenderState extends Record implements PictureInPictureRenderState {
    private final BookModel bookModel;
    private final Identifier texture;
    private final float open;
    private final float flip;
    private final int x0;
    private final int y0;
    private final int x1;
    private final int y1;
    private final float scale;
    private final ScreenRectangle scissorArea;
    private final ScreenRectangle bounds;

    public GuiBookModelRenderState(BookModel $$0, Identifier $$1, float $$2, float $$3, int $$4, int $$5, int $$6, int $$7, float $$8, ScreenRectangle $$9, ScreenRectangle $$10) {
        this.bookModel = $$0;
        this.texture = $$1;
        this.open = $$2;
        this.flip = $$3;
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
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GuiBookModelRenderState.class), GuiBookModelRenderState.class, "bookModel;texture;open;flip;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->bookModel:Lnet/minecraft/client/model/object/book/BookModel;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->open:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->flip:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GuiBookModelRenderState.class), GuiBookModelRenderState.class, "bookModel;texture;open;flip;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->bookModel:Lnet/minecraft/client/model/object/book/BookModel;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->open:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->flip:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GuiBookModelRenderState.class, Object.class), GuiBookModelRenderState.class, "bookModel;texture;open;flip;x0;y0;x1;y1;scale;scissorArea;bounds", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->bookModel:Lnet/minecraft/client/model/object/book/BookModel;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->open:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->flip:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->x0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->y0:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->x1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->y1:I", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->scale:F", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;", "FIELD:Lnet/minecraft/client/gui/render/state/pip/GuiBookModelRenderState;->bounds:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BookModel bookModel() {
        return this.bookModel;
    }

    public Identifier texture() {
        return this.texture;
    }

    public float open() {
        return this.open;
    }

    public float flip() {
        return this.flip;
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

    public GuiBookModelRenderState(BookModel $$0, Identifier $$1, float $$2, float $$3, int $$4, int $$5, int $$6, int $$7, float $$8, ScreenRectangle $$9) {
        this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, PictureInPictureRenderState.getBounds($$4, $$5, $$6, $$7, $$9));
    }
}
