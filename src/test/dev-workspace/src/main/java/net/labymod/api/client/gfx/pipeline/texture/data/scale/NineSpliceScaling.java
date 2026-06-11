package net.labymod.api.client.gfx.pipeline.texture.data.scale;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling.class */
public final class NineSpliceScaling extends Record implements SpriteScaling {
    private final int width;
    private final int height;
    private final Border border;
    private final boolean stretchInner;

    public NineSpliceScaling(int width, int height, Border border, boolean stretchInner) {
        this.width = width;
        this.height = height;
        this.border = border;
        this.stretchInner = stretchInner;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NineSpliceScaling.class), NineSpliceScaling.class, "width;height;border;stretchInner", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->width:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->height:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->border:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->stretchInner:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NineSpliceScaling.class), NineSpliceScaling.class, "width;height;border;stretchInner", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->width:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->height:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->border:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->stretchInner:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NineSpliceScaling.class, Object.class), NineSpliceScaling.class, "width;height;border;stretchInner", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->width:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->height:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->border:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling;->stretchInner:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public Border border() {
        return this.border;
    }

    public boolean stretchInner() {
        return this.stretchInner;
    }

    public NineSpliceScaling(int width, int height, Border border) {
        this(width, height, border, false);
    }

    public static NineSpliceScaling of(float width, float height, Border border) {
        return new NineSpliceScaling(MathHelper.ceil(width), MathHelper.ceil(height), border);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border.class */
    public static final class Border extends Record {
        private final int left;
        private final int top;
        private final int right;
        private final int bottom;

        public Border(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Border.class), Border.class, "left;top;right;bottom", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->left:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->top:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->right:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->bottom:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Border.class), Border.class, "left;top;right;bottom", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->left:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->top:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->right:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->bottom:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Border.class, Object.class), Border.class, "left;top;right;bottom", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->left:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->top:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->right:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/NineSpliceScaling$Border;->bottom:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public int left() {
            return this.left;
        }

        public int top() {
            return this.top;
        }

        public int right() {
            return this.right;
        }

        public int bottom() {
            return this.bottom;
        }

        public Border(int size) {
            this(size, size, size, size);
        }
    }
}
