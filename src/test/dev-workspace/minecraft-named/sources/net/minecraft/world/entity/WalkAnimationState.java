package net.minecraft.world.entity;

import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/WalkAnimationState.class */
public class WalkAnimationState {
    private float speedOld;
    private float speed;
    private float position;
    private float positionScale = 1.0f;

    public void setSpeed(float $$0) {
        this.speed = $$0;
    }

    public void update(float $$0, float $$1, float $$2) {
        this.speedOld = this.speed;
        this.speed += ($$0 - this.speed) * $$1;
        this.position += this.speed;
        this.positionScale = $$2;
    }

    public void stop() {
        this.speedOld = 0.0f;
        this.speed = 0.0f;
        this.position = 0.0f;
    }

    public float speed() {
        return this.speed;
    }

    public float speed(float $$0) {
        return Math.min(Mth.lerp($$0, this.speedOld, this.speed), 1.0f);
    }

    public float position() {
        return this.position * this.positionScale;
    }

    public float position(float $$0) {
        return (this.position - (this.speed * (1.0f - $$0))) * this.positionScale;
    }

    public boolean isMoving() {
        return this.speed > 1.0E-5f;
    }
}
