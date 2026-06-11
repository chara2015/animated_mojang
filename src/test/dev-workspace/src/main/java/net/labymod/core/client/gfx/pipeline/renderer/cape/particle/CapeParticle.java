package net.labymod.core.client.gfx.pipeline.renderer.cape.particle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/cape/particle/CapeParticle.class */
public class CapeParticle {
    private final float x;
    private final float y;
    private final float spawnTick;
    private float prevProgress = -1.0f;

    public CapeParticle(float x, float y, float spawnTick) {
        this.x = x;
        this.y = y;
        this.spawnTick = spawnTick;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getSpawnTick() {
        return this.spawnTick;
    }

    public float getPreviousProgress() {
        return this.prevProgress;
    }

    public void setPreviousProgress(float progress) {
        this.prevProgress = progress;
    }
}
