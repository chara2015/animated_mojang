package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo.class */
public final class GlyphInfo extends Record {

    @SerializedName("unicode")
    private final int unicode;

    @SerializedName("advance")
    private final float advance;

    @SerializedName("planeBounds")
    @Nullable
    private final Bounds planeBounds;

    @SerializedName("atlasBounds")
    @Nullable
    private final Bounds atlasBounds;

    public GlyphInfo(int unicode, float advance, @Nullable Bounds planeBounds, @Nullable Bounds atlasBounds) {
        this.unicode = unicode;
        this.advance = advance;
        this.planeBounds = planeBounds;
        this.atlasBounds = atlasBounds;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GlyphInfo.class), GlyphInfo.class, "unicode;advance;planeBounds;atlasBounds", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->unicode:I", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->advance:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->planeBounds:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->atlasBounds:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GlyphInfo.class), GlyphInfo.class, "unicode;advance;planeBounds;atlasBounds", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->unicode:I", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->advance:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->planeBounds:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->atlasBounds:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GlyphInfo.class, Object.class), GlyphInfo.class, "unicode;advance;planeBounds;atlasBounds", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->unicode:I", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->advance:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->planeBounds:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/GlyphInfo;->atlasBounds:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @SerializedName("unicode")
    public int unicode() {
        return this.unicode;
    }

    @SerializedName("advance")
    public float advance() {
        return this.advance;
    }

    @SerializedName("planeBounds")
    @Nullable
    public Bounds planeBounds() {
        return this.planeBounds;
    }

    @SerializedName("atlasBounds")
    @Nullable
    public Bounds atlasBounds() {
        return this.atlasBounds;
    }
}
