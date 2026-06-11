package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics.class */
public final class Metrics extends Record {

    @SerializedName("emSize")
    private final float emSize;

    @SerializedName("lineHeight")
    private final float lineHeight;

    @SerializedName("ascender")
    private final float ascender;

    @SerializedName("descender")
    private final float descender;

    @SerializedName("underlineY")
    private final float underlineY;

    @SerializedName("underlineThickness")
    private final float underlineThickness;

    public Metrics(float emSize, float lineHeight, float ascender, float descender, float underlineY, float underlineThickness) {
        this.emSize = emSize;
        this.lineHeight = lineHeight;
        this.ascender = ascender;
        this.descender = descender;
        this.underlineY = underlineY;
        this.underlineThickness = underlineThickness;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Metrics.class), Metrics.class, "emSize;lineHeight;ascender;descender;underlineY;underlineThickness", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->emSize:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->lineHeight:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->ascender:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->descender:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->underlineY:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->underlineThickness:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Metrics.class), Metrics.class, "emSize;lineHeight;ascender;descender;underlineY;underlineThickness", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->emSize:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->lineHeight:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->ascender:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->descender:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->underlineY:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->underlineThickness:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Metrics.class, Object.class), Metrics.class, "emSize;lineHeight;ascender;descender;underlineY;underlineThickness", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->emSize:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->lineHeight:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->ascender:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->descender:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->underlineY:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Metrics;->underlineThickness:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @SerializedName("emSize")
    public float emSize() {
        return this.emSize;
    }

    @SerializedName("lineHeight")
    public float lineHeight() {
        return this.lineHeight;
    }

    @SerializedName("ascender")
    public float ascender() {
        return this.ascender;
    }

    @SerializedName("descender")
    public float descender() {
        return this.descender;
    }

    @SerializedName("underlineY")
    public float underlineY() {
        return this.underlineY;
    }

    @SerializedName("underlineThickness")
    public float underlineThickness() {
        return this.underlineThickness;
    }
}
