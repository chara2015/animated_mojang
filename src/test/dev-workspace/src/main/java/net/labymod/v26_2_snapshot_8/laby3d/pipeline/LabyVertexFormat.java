package net.labymod.v26_2_snapshot_8.laby3d.pipeline;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.laby3d.vertex.VertexDescriptions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/pipeline/LabyVertexFormat.class */
public final class LabyVertexFormat {
    public static final GpuFormat POSITION_FORMAT = GpuFormat.RGB32_FLOAT;
    public static final GpuFormat COLOR_FORMAT = GpuFormat.RGBA8_UNORM;
    public static final GpuFormat UV0_FORMAT = GpuFormat.RG32_FLOAT;
    public static final GpuFormat UV1_FORMAT = GpuFormat.RG16_SINT;
    public static final GpuFormat UV2_FORMAT = GpuFormat.RG16_SINT;
    public static final GpuFormat NORMAL_FORMAT = GpuFormat.RGBA8_SNORM;
    public static final GpuFormat LINE_WIDTH_FORMAT = GpuFormat.R32_FLOAT;
    public static final VertexFormat POSITION_TEX_COLOR_LIGHTMAP = VertexFormat.builder(0).addAttribute(VertexDescriptions.POSITION_NAME, POSITION_FORMAT).addAttribute(VertexDescriptions.COLOR_NAME, COLOR_FORMAT).addAttribute(VertexDescriptions.UV0_NAME, UV0_FORMAT).addAttribute(VertexDescriptions.UV2_NAME, UV2_FORMAT).build();
}
