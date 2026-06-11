package net.minecraft.client.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.ParticleGroupRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/particle/NoRenderParticleGroup.class */
public class NoRenderParticleGroup extends ParticleGroup<NoRenderParticle> {
    private static final ParticleGroupRenderState EMPTY_RENDER_STATE = ($$0, $$1) -> {
    };

    public NoRenderParticleGroup(ParticleEngine $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.particle.ParticleGroup
    public ParticleGroupRenderState extractRenderState(Frustum $$0, Camera $$1, float $$2) {
        return EMPTY_RENDER_STATE;
    }
}
