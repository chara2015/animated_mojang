package net.minecraft.client.renderer.state;

import net.minecraft.client.renderer.SubmitNodeCollector;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/state/ParticleGroupRenderState.class */
public interface ParticleGroupRenderState {
    void submit(SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState);

    default void clear() {
    }
}
