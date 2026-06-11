package net.labymod.v1_20_1.laby3d.pipeline;

import com.google.common.collect.ImmutableMap;
import net.labymod.api.laby3d.vertex.VertexDescriptions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/laby3d/pipeline/LabyVertexFormat.class */
public final class LabyVertexFormat {
    public static final eio POSITION_TEX_COLOR_LIGHTMAP = new eio(ImmutableMap.builder().put(VertexDescriptions.POSITION_NAME, eih.a).put(VertexDescriptions.COLOR_NAME, eih.b).put(VertexDescriptions.UV0_NAME, eih.c).put(VertexDescriptions.UV2_NAME, eih.e).build());
}
