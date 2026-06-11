package net.labymod.v1_21_3.laby3d.pipeline;

import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.loader.MinecraftVersions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/laby3d/pipeline/MinecraftRenderStates.class */
public final class MinecraftRenderStates {
    private static String versionId;
    public static final MinecraftRenderState TEXT = new MinecraftRenderState(FontRenderStates.VANILLA_TEXT, fgq.k, c.h, createMinecraftConfigId("core/rendertype_text"));
    public static final MinecraftRenderState TEXT_SEE_THROUGH = new MinecraftRenderState(FontRenderStates.VANILLA_SEE_THROUGH_TEXT, fgq.k, c.h, createMinecraftConfigId("core/rendertype_text_see_through"));
    public static final MinecraftRenderState TEXT_INTENSITY = new MinecraftRenderState(FontRenderStates.VANILLA_INTENSITY_TEXT, fgq.k, c.h, createMinecraftConfigId("core/rendertype_text_intensity"));
    public static final MinecraftRenderState TEXT_INTENSITY_SEE_THROUGH = new MinecraftRenderState(FontRenderStates.VANILLA_INTENSITY_SEE_THROUGH_TEXT, fgq.k, c.h, createMinecraftConfigId("core/rendertype_text_see_through"));
    public static final MinecraftRenderState NAMETAG = new MinecraftRenderState(RenderStates.NAMETAG_ICON, LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, c.h, createConfigId("core/nametag_icon"));
    public static final MinecraftRenderState SEE_THROUGH_NAMETAG = new MinecraftRenderState(RenderStates.SEE_THROUGH_NAMETAG_ICON, LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, c.h, createConfigId("core/see_through_nametag_icon"));

    private static alz createMinecraftConfigId(String path) {
        return alz.b(Namespaces.MINECRAFT, path);
    }

    private static alz createConfigId(String path) {
        if (versionId == null) {
            versionId = MinecraftVersions.current().getFormattedVersion();
        }
        return alz.b("labymod", path + "_" + versionId);
    }
}
