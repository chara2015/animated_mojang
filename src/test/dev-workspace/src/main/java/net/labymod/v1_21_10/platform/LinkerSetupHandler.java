package net.labymod.v1_21_10.platform;

import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.laby3d.RenderStateLinkerSetupEvent;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.v1_21_10.laby3d.pipeline.LabyRenderPipelines;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/platform/LinkerSetupHandler.class */
public class LinkerSetupHandler {
    @Subscribe
    public void onRenderStateLinkerSetup(RenderStateLinkerSetupEvent event) {
        event.link(FontRenderStates.VANILLA_TEXT, hgi.H);
        event.link(FontRenderStates.VANILLA_SEE_THROUGH_TEXT, hgi.N);
        event.link(FontRenderStates.VANILLA_INTENSITY_TEXT, hgi.K);
        event.link(FontRenderStates.VANILLA_INTENSITY_SEE_THROUGH_TEXT, hgi.P);
        event.link(RenderStates.NAMETAG_ICON, LabyRenderPipelines.NAMETAG_ICON);
        event.link(RenderStates.SEE_THROUGH_NAMETAG_ICON, LabyRenderPipelines.SEE_THROUGH_NAMETAG_ICON);
    }
}
