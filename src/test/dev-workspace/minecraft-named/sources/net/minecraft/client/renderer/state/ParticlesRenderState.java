package net.minecraft.client.renderer.state;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.SubmitNodeStorage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/state/ParticlesRenderState.class */
public class ParticlesRenderState {
    public final List<ParticleGroupRenderState> particles = new ArrayList();

    public void reset() {
        this.particles.forEach((v0) -> {
            v0.clear();
        });
        this.particles.clear();
    }

    public void add(ParticleGroupRenderState $$0) {
        this.particles.add($$0);
    }

    public void submit(SubmitNodeStorage $$0, CameraRenderState $$1) {
        for (ParticleGroupRenderState $$2 : this.particles) {
            $$2.submit($$0, $$1);
        }
    }
}
