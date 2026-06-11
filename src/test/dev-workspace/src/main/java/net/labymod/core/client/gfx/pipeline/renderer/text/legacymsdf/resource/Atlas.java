package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas.class */
public final class Atlas extends Record {

    @SerializedName(ParameterType.TYPE)
    private final String type;

    @SerializedName("distanceRange")
    private final float distanceRange;

    @SerializedName(ItemMetadata.SIZE_KEY)
    private final float size;

    @SerializedName("width")
    private final float width;

    @SerializedName("height")
    private final float height;

    @SerializedName("yOrigin")
    private final String yOrigin;

    public Atlas(String type, float distanceRange, float size, float width, float height, String yOrigin) {
        this.type = type;
        this.distanceRange = distanceRange;
        this.size = size;
        this.width = width;
        this.height = height;
        this.yOrigin = yOrigin;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Atlas.class), Atlas.class, "type;distanceRange;size;width;height;yOrigin", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->type:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->distanceRange:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->size:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->width:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->height:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->yOrigin:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Atlas.class), Atlas.class, "type;distanceRange;size;width;height;yOrigin", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->type:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->distanceRange:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->size:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->width:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->height:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->yOrigin:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Atlas.class, Object.class), Atlas.class, "type;distanceRange;size;width;height;yOrigin", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->type:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->distanceRange:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->size:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->width:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->height:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Atlas;->yOrigin:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @SerializedName(ParameterType.TYPE)
    public String type() {
        return this.type;
    }

    @SerializedName("distanceRange")
    public float distanceRange() {
        return this.distanceRange;
    }

    @SerializedName(ItemMetadata.SIZE_KEY)
    public float size() {
        return this.size;
    }

    @SerializedName("width")
    public float width() {
        return this.width;
    }

    @SerializedName("height")
    public float height() {
        return this.height;
    }

    @SerializedName("yOrigin")
    public String yOrigin() {
        return this.yOrigin;
    }
}
