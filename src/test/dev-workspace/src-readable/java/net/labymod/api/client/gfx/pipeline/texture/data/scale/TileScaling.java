package net.labymod.api.client.gfx.pipeline.texture.data.scale;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/data/scale/TileScaling.class */
public final class TileScaling extends Record implements SpriteScaling {
    private final int width;
    private final int height;

    public TileScaling(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TileScaling.class), TileScaling.class, "width;height", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/TileScaling;->width:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/TileScaling;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TileScaling.class), TileScaling.class, "width;height", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/TileScaling;->width:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/TileScaling;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TileScaling.class, Object.class), TileScaling.class, "width;height", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/TileScaling;->width:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/texture/data/scale/TileScaling;->height:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }
}
