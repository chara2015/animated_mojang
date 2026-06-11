package net.labymod.v1_21_11.platform;

import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.laby3d.RenderStateLinkerSetupEvent;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.v1_21_11.laby3d.pipeline.LabyRenderPipelines;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/platform/LinkerSetupHandler.class */
public class LinkerSetupHandler {
    @Subscribe
    public void onRenderStateLinkerSetup(RenderStateLinkerSetupEvent event) {
        event.link(FontRenderStates.VANILLA_TEXT, hpa.J);
        event.link(FontRenderStates.VANILLA_SEE_THROUGH_TEXT, hpa.P);
        event.link(FontRenderStates.VANILLA_INTENSITY_TEXT, hpa.M);
        event.link(FontRenderStates.VANILLA_INTENSITY_SEE_THROUGH_TEXT, hpa.R);
        event.link(RenderStates.NAMETAG_ICON, LabyRenderPipelines.NAMETAG_ICON);
        event.link(RenderStates.SEE_THROUGH_NAMETAG_ICON, LabyRenderPipelines.SEE_THROUGH_NAMETAG_ICON);
    }
}
