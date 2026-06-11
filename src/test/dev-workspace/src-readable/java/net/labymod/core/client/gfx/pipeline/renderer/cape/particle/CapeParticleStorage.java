package net.labymod.core.client.gfx.pipeline.renderer.cape.particle;

import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/cape/particle/CapeParticleStorage.class */
public class CapeParticleStorage {
    private final Matrix4f modelViewMatrix;
    private final float alpha;

    public CapeParticleStorage(Matrix4f modelViewMatrix, float alpha) {
        this.modelViewMatrix = modelViewMatrix;
        this.alpha = 1.0f - (alpha - 1.0f);
    }

    public Matrix4f modelViewMatrix() {
        return this.modelViewMatrix;
    }

    public float getAlpha() {
        return this.alpha;
    }
}
