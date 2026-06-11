package net.labymod.v1_16_5.platform;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.laby3d.RenderStateLinkerSetupEvent;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.v1_16_5.laby3d.pipeline.MinecraftRenderState;
import net.labymod.v1_16_5.laby3d.pipeline.MinecraftRenderStates;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/platform/LinkerSetupHandler.class */
public class LinkerSetupHandler {
    @Subscribe
    public void onRenderStateLinkerSetup(RenderStateLinkerSetupEvent event) {
        linkState(event, MinecraftRenderStates.TEXT);
        linkState(event, MinecraftRenderStates.TEXT_SEE_THROUGH);
        linkState(event, MinecraftRenderStates.TEXT_INTENSITY);
        linkState(event, MinecraftRenderStates.TEXT_INTENSITY_SEE_THROUGH);
        linkState(event, MinecraftRenderStates.NAMETAG);
        linkState(event, MinecraftRenderStates.SEE_THROUGH_NAMETAG);
        event.link(RenderStates.VANILLA_TEXT, MinecraftRenderStates.TEXT);
        event.link(RenderStates.VANILLA_INTENSITY_TEXT, MinecraftRenderStates.TEXT_INTENSITY);
        event.link(RenderStates.VANILLA_SEE_THROUGH_TEXT, MinecraftRenderStates.TEXT_SEE_THROUGH);
        event.link(RenderStates.VANILLA_SEE_THROUGH_INTENSITY_TEXT, MinecraftRenderStates.TEXT_INTENSITY_SEE_THROUGH);
    }

    private void linkState(RenderStateLinkerSetupEvent event, MinecraftRenderState renderState) {
        event.link(renderState.getRenderState(), renderState);
    }
}
