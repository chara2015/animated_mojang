package net.labymod.v1_18_2.laby3d.pipeline;

import com.google.common.collect.ImmutableMap;
import net.labymod.api.laby3d.vertex.VertexDescriptions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/laby3d/pipeline/LabyVertexFormat.class */
public final class LabyVertexFormat {
    public static final dtr POSITION_TEX_COLOR_LIGHTMAP = new dtr(ImmutableMap.builder().put(VertexDescriptions.POSITION_NAME, dtk.a).put(VertexDescriptions.COLOR_NAME, dtk.b).put(VertexDescriptions.UV0_NAME, dtk.c).put(VertexDescriptions.UV2_NAME, dtk.e).build());
}
