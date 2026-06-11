package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials.class */
public final class LegacyMSDFMaterials extends Record {
    private final Material normal;
    private final Material seeThrough;
    private final Material polygonOffset;
    private final RenderState guiRenderState;

    public LegacyMSDFMaterials(Material normal, Material seeThrough, Material polygonOffset, RenderState guiRenderState) {
        this.normal = normal;
        this.seeThrough = seeThrough;
        this.polygonOffset = polygonOffset;
        this.guiRenderState = guiRenderState;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LegacyMSDFMaterials.class), LegacyMSDFMaterials.class, "normal;seeThrough;polygonOffset;guiRenderState", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->normal:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->seeThrough:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->polygonOffset:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->guiRenderState:Lnet/labymod/laby3d/api/pipeline/RenderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LegacyMSDFMaterials.class), LegacyMSDFMaterials.class, "normal;seeThrough;polygonOffset;guiRenderState", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->normal:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->seeThrough:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->polygonOffset:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->guiRenderState:Lnet/labymod/laby3d/api/pipeline/RenderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LegacyMSDFMaterials.class, Object.class), LegacyMSDFMaterials.class, "normal;seeThrough;polygonOffset;guiRenderState", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->normal:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->seeThrough:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->polygonOffset:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/LegacyMSDFMaterials;->guiRenderState:Lnet/labymod/laby3d/api/pipeline/RenderState;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Material normal() {
        return this.normal;
    }

    public Material seeThrough() {
        return this.seeThrough;
    }

    public Material polygonOffset() {
        return this.polygonOffset;
    }

    public RenderState guiRenderState() {
        return this.guiRenderState;
    }

    public Material select(int flags) {
        if (FontFlags.hasFlag(flags, 4)) {
            return this.normal;
        }
        if (FontFlags.hasFlag(flags, 8)) {
            return this.seeThrough;
        }
        if (FontFlags.hasFlag(flags, 16)) {
            return this.polygonOffset;
        }
        return this.normal;
    }
}
