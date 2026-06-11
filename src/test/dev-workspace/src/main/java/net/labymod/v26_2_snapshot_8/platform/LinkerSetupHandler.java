package net.labymod.v26_2_snapshot_8.platform;

import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.laby3d.RenderStateLinkerSetupEvent;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.v26_2_snapshot_8.laby3d.pipeline.LabyRenderPipelines;
import net.minecraft.client.renderer.RenderPipelines;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/platform/LinkerSetupHandler.class */
public class LinkerSetupHandler {
    @Subscribe
    public void onRenderStateLinkerSetup(RenderStateLinkerSetupEvent event) {
        event.link(FontRenderStates.VANILLA_TEXT, RenderPipelines.TEXT);
        event.link(FontRenderStates.VANILLA_SEE_THROUGH_TEXT, RenderPipelines.TEXT_SEE_THROUGH);
        event.link(FontRenderStates.VANILLA_INTENSITY_TEXT, RenderPipelines.TEXT_GRAYSCALE);
        event.link(FontRenderStates.VANILLA_INTENSITY_SEE_THROUGH_TEXT, RenderPipelines.TEXT_GRAYSCALE_SEE_THROUGH);
        event.link(RenderStates.NAMETAG_ICON, LabyRenderPipelines.NAMETAG_ICON);
        event.link(RenderStates.SEE_THROUGH_NAMETAG_ICON, LabyRenderPipelines.SEE_THROUGH_NAMETAG_ICON);
    }
}
