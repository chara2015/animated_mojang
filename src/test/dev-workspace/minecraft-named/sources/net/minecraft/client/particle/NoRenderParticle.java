package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/particle/NoRenderParticle.class */
public class NoRenderParticle extends Particle {
    protected NoRenderParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
        super($$0, $$1, $$2, $$3);
    }

    protected NoRenderParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
    }

    @Override // net.minecraft.client.particle.Particle
    public ParticleRenderType getGroup() {
        return ParticleRenderType.NO_RENDER;
    }
}
