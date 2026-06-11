package net.labymod.v1_21_10.laby3d.pipeline;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.labymod.api.laby3d.vertex.VertexDescriptions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/laby3d/pipeline/LabyVertexFormat.class */
public final class LabyVertexFormat {
    public static final VertexFormat POSITION_TEX_COLOR_LIGHTMAP = VertexFormat.builder().add(VertexDescriptions.POSITION_NAME, VertexFormatElement.POSITION).add(VertexDescriptions.COLOR_NAME, VertexFormatElement.COLOR).add(VertexDescriptions.UV0_NAME, VertexFormatElement.UV0).add(VertexDescriptions.UV2_NAME, VertexFormatElement.UV2).build();
}
