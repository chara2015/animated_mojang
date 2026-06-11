package net.labymod.core.main.listener;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.laby3d.api.RenderDevice;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/ShaderReloadListener.class */
public class ShaderReloadListener {
    private final Laby3D laby3D = Laby.references().laby3D();

    @Subscribe
    public void onResourceReload(ResourceReloadEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        RenderDevice renderDevice = this.laby3D.renderDevice();
        FontRenderStates.invalidateShaders(renderDevice);
    }
}
