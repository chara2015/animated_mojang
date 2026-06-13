package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/CircleDataUniformBlockData.class */
public class CircleDataUniformBlockData implements UniformBlockData<CircleDataUniformBlock> {
    private final Vector2f circleCenter = new Vector2f();
    private float circleRadius;
    private float circleInnerRadius;
    private float circleStartAngle;
    private float circleEndAngle;

    public Vector2f circleCenter() {
        return this.circleCenter;
    }

    public float circleRadius() {
        return this.circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public float circleInnerRadius() {
        return this.circleInnerRadius;
    }

    public void setCircleInnerRadius(float circleInnerRadius) {
        this.circleInnerRadius = circleInnerRadius;
    }

    public float circleStartAngle() {
        return this.circleStartAngle;
    }

    public void setCircleStartAngle(float circleStartAngle) {
        this.circleStartAngle = circleStartAngle;
    }

    public float circleEndAngle() {
        return this.circleEndAngle;
    }

    public void setCircleEndAngle(float circleEndAngle) {
        this.circleEndAngle = circleEndAngle;
    }

    public void update(CircleDataUniformBlock block) {
        block.circleCenter().set(this.circleCenter);
        block.circleRadius().set(Float.valueOf(this.circleRadius));
        block.circleInnerRadius().set(Float.valueOf(this.circleInnerRadius));
        block.circleStartAngle().set(Float.valueOf(this.circleStartAngle));
        block.circleEndAngle().set(Float.valueOf(this.circleEndAngle));
    }
}
