package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphRenderResources.class */
public interface GlyphRenderResources {
    Material material(int i);

    RenderState guiRenderState();

    DeviceTextureView textureView();
}
