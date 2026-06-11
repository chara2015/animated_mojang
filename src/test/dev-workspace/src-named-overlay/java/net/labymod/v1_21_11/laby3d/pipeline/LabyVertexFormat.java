package net.labymod.v1_21_11.laby3d.pipeline;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/pipeline/LabyVertexFormat.class */
public final class LabyVertexFormat {
    public static final VertexFormat POSITION_TEX_COLOR_LIGHTMAP = VertexFormat.builder().add("Position", VertexFormatElement.POSITION).add("Color", VertexFormatElement.COLOR).add("UV0", VertexFormatElement.UV0).add("UV2", VertexFormatElement.UV2).build();
}
