package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Vector2f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/RoundDataUniformBlockData.class */
public class RoundDataUniformBlockData implements UniformBlockData<RoundDataUniformBlock> {
    private final Vector4f cornerRoundingRadius = new Vector4f();
    private final Vector4f rectangleBounds = new Vector4f();
    private final Vector2f rectangleDimensions = new Vector2f();
    private final Vector4f borderColor = new Vector4f();
    private float innerShapeSoftness;
    private float outerShapeSoftness;
    private float borderWidth;
    private float borderFadeDistance;

    public Vector4f cornerRoundingRadius() {
        return this.cornerRoundingRadius;
    }

    public Vector4f rectangleBounds() {
        return this.rectangleBounds;
    }

    public Vector2f rectangleDimensions() {
        return this.rectangleDimensions;
    }

    public Vector4f borderColor() {
        return this.borderColor;
    }

    public float innerShapeSoftness() {
        return this.innerShapeSoftness;
    }

    public void setInnerShapeSoftness(float innerShapeSoftness) {
        this.innerShapeSoftness = innerShapeSoftness;
    }

    public float outerShapeSoftness() {
        return this.outerShapeSoftness;
    }

    public void setOuterShapeSoftness(float outerShapeSoftness) {
        this.outerShapeSoftness = outerShapeSoftness;
    }

    public float borderWidth() {
        return this.borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public float borderFadeDistance() {
        return this.borderFadeDistance;
    }

    public void setBorderFadeDistance(float borderFadeDistance) {
        this.borderFadeDistance = borderFadeDistance;
    }

    public void update(RoundDataUniformBlock block) {
        block.cornerRoundingRadius().set(this.cornerRoundingRadius);
        block.rectangleBounds().set(this.rectangleBounds);
        block.rectangleDimensions().set(this.rectangleDimensions);
        block.borderColor().set(this.borderColor);
        block.innerShapeSoftness().set(Float.valueOf(this.innerShapeSoftness));
        block.outerShapeSoftness().set(Float.valueOf(this.outerShapeSoftness));
        block.borderWidth().set(Float.valueOf(this.borderWidth));
        block.borderFadeDistance().set(Float.valueOf(this.borderFadeDistance));
    }
}
