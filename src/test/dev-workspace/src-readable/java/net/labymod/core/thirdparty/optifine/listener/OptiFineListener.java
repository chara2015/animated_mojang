package net.labymod.core.thirdparty.optifine.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.shader.ShaderPipelineContextEvent;
import net.labymod.api.thirdparty.optifine.OptiFine;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/optifine/listener/OptiFineListener.class */
public class OptiFineListener {
    @Subscribe
    public void onShaderPipelineContext(ShaderPipelineContextEvent event) {
        event.setActiveShaderPackSupplier(() -> {
            return OptiFine.config().hasShaders();
        });
    }
}
