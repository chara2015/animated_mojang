package net.labymod.v1_21_1.platform;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.laby3d.RenderStateLinkerSetupEvent;
import net.labymod.v1_21_1.laby3d.pipeline.MinecraftRenderState;
import net.labymod.v1_21_1.laby3d.pipeline.MinecraftRenderStates;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/platform/LinkerSetupHandler.class */
public class LinkerSetupHandler {
    @Subscribe
    public void onRenderStateLinkerSetup(RenderStateLinkerSetupEvent event) {
        linkState(event, MinecraftRenderStates.TEXT);
        linkState(event, MinecraftRenderStates.TEXT_SEE_THROUGH);
        linkState(event, MinecraftRenderStates.TEXT_INTENSITY);
        linkState(event, MinecraftRenderStates.TEXT_INTENSITY_SEE_THROUGH);
        linkState(event, MinecraftRenderStates.NAMETAG);
        linkState(event, MinecraftRenderStates.SEE_THROUGH_NAMETAG);
    }

    private void linkState(RenderStateLinkerSetupEvent event, MinecraftRenderState renderState) {
        event.link(renderState.getRenderState(), renderState);
    }
}
