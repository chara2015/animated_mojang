package net.labymod.v1_19_4.laby3d.pipeline;

import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.loader.MinecraftVersions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/laby3d/pipeline/MinecraftRenderStates.class */
public final class MinecraftRenderStates {
    private static String versionId;
    public static final MinecraftRenderState TEXT = new MinecraftRenderState(FontRenderStates.VANILLA_TEXT, ehc.t, b.h, createMinecraftConfigId("rendertype_text"));
    public static final MinecraftRenderState TEXT_SEE_THROUGH = new MinecraftRenderState(FontRenderStates.VANILLA_SEE_THROUGH_TEXT, ehc.t, b.h, createMinecraftConfigId("rendertype_text_see_through"));
    public static final MinecraftRenderState TEXT_INTENSITY = new MinecraftRenderState(FontRenderStates.VANILLA_INTENSITY_TEXT, ehc.t, b.h, createMinecraftConfigId("rendertype_text_intensity"));
    public static final MinecraftRenderState TEXT_INTENSITY_SEE_THROUGH = new MinecraftRenderState(FontRenderStates.VANILLA_INTENSITY_SEE_THROUGH_TEXT, ehc.t, b.h, createMinecraftConfigId("rendertype_text_see_through"));
    public static final MinecraftRenderState NAMETAG = new MinecraftRenderState(RenderStates.NAMETAG_ICON, LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, b.h, createConfigId("nametag_icon"));
    public static final MinecraftRenderState SEE_THROUGH_NAMETAG = new MinecraftRenderState(RenderStates.SEE_THROUGH_NAMETAG_ICON, LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, b.h, createConfigId("see_through_nametag_icon"));

    private static ResourceLocation createMinecraftConfigId(String path) {
        return ResourceLocation.create(Namespaces.MINECRAFT, path);
    }

    private static ResourceLocation createConfigId(String path) {
        if (versionId == null) {
            versionId = MinecraftVersions.current().getFormattedVersion();
        }
        return ResourceLocation.create("labymod", path + "_" + versionId);
    }
}
