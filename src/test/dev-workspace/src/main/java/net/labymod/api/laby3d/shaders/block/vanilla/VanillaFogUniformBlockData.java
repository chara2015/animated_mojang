package net.labymod.api.laby3d.shaders.block.vanilla;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/vanilla/VanillaFogUniformBlockData.class */
public class VanillaFogUniformBlockData implements UniformBlockData<VanillaFogUniformBlock> {
    private final Vector4f fogColor = new Vector4f(0.0f);
    private float fogEnvironmentalStart = Float.MAX_VALUE;
    private float fogEnvironmentalEnd = Float.MAX_VALUE;
    private float fogRenderDistanceStart = Float.MAX_VALUE;
    private float fogRenderDistanceEnd = Float.MAX_VALUE;
    private float fogSkyEnd = Float.MAX_VALUE;
    private float fogCloudsEnd = Float.MAX_VALUE;

    public Vector4f fogColor() {
        return this.fogColor;
    }

    public float getFogEnvironmentalStart() {
        return this.fogEnvironmentalStart;
    }

    public void setFogEnvironmentalStart(float fogEnvironmentalStart) {
        this.fogEnvironmentalStart = fogEnvironmentalStart;
    }

    public float getFogEnvironmentalEnd() {
        return this.fogEnvironmentalEnd;
    }

    public void setFogEnvironmentalEnd(float fogEnvironmentalEnd) {
        this.fogEnvironmentalEnd = fogEnvironmentalEnd;
    }

    public float getFogRenderDistanceStart() {
        return this.fogRenderDistanceStart;
    }

    public void setFogRenderDistanceStart(float fogRenderDistanceStart) {
        this.fogRenderDistanceStart = fogRenderDistanceStart;
    }

    public float getFogRenderDistanceEnd() {
        return this.fogRenderDistanceEnd;
    }

    public void setFogRenderDistanceEnd(float fogRenderDistanceEnd) {
        this.fogRenderDistanceEnd = fogRenderDistanceEnd;
    }

    public float getFogSkyEnd() {
        return this.fogSkyEnd;
    }

    public void setFogSkyEnd(float fogSkyEnd) {
        this.fogSkyEnd = fogSkyEnd;
    }

    public float getFogCloudsEnd() {
        return this.fogCloudsEnd;
    }

    public void setFogCloudsEnd(float fogCloudsEnd) {
        this.fogCloudsEnd = fogCloudsEnd;
    }

    public void update(VanillaFogUniformBlock block) {
        block.fogColor().set(this.fogColor);
        block.fogEnvironmentalStart().set(Float.valueOf(this.fogEnvironmentalStart));
        block.fogEnvironmentalEnd().set(Float.valueOf(this.fogEnvironmentalEnd));
        block.fogRenderDistanceStart().set(Float.valueOf(this.fogRenderDistanceStart));
        block.fogRenderDistanceEnd().set(Float.valueOf(this.fogRenderDistanceEnd));
        block.fogSkyEnd().set(Float.valueOf(this.fogSkyEnd));
        block.fogCloudsEnd().set(Float.valueOf(this.fogCloudsEnd));
    }
}
