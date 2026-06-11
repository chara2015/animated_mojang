package net.labymod.core.main.user.shop.cosmetic.pet.ai.controller;

import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.Transform;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/controller/LookController.class */
public class LookController {
    private static final float TOLERANCE = 1.0E-5f;
    private static final float DEFAULT_Y_ROTATION_SPEED = 10.0f;
    private final PetBehavior behavior;
    private final DoubleVector3 lookAt = new DoubleVector3();
    private float yMaxRotationSpeed;
    private int lookAtCooldown;
    private boolean rotationLocked;

    public LookController(PetBehavior behavior) {
        this.behavior = behavior;
    }

    public void tick() {
        if (this.rotationLocked || this.lookAtCooldown <= 0) {
            return;
        }
        this.lookAtCooldown--;
        calculateYRotationDelta(delta -> {
            Transform transform = this.behavior.transform();
            FloatVector3 rotation = transform.rotation();
            float currentY = rotation.getY();
            rotation.setY(MathHelper.rotateTowards(currentY, delta, this.yMaxRotationSpeed));
        });
    }

    public void setLookAt(DoubleVector3 target) {
        setLookAt(target, DEFAULT_Y_ROTATION_SPEED);
    }

    public void setLookAt(DoubleVector3 target, float yMaxRotationSpeed) {
        setLookAt(target.getX(), target.getY(), target.getZ(), yMaxRotationSpeed);
    }

    public void setLookAt(double x, double y, double z) {
        setLookAt(x, y, z, DEFAULT_Y_ROTATION_SPEED);
    }

    public void setLookAt(double x, double y, double z, float yMaxRotationSpeed) {
        this.lookAt.set(x, y, z);
        this.yMaxRotationSpeed = yMaxRotationSpeed;
        this.lookAtCooldown = 30;
    }

    public void setRotationLocked(boolean rotationLocked) {
        this.rotationLocked = rotationLocked;
    }

    public DoubleVector3 lookAt() {
        return this.lookAt;
    }

    private void calculateYRotationDelta(FloatConsumer deltaConsumer) {
        double diffX = this.lookAt.getX() - this.behavior.position().getX();
        double diffZ = this.lookAt.getZ() - this.behavior.position().getZ();
        if (Math.abs(diffX) <= 9.999999747378752E-6d && Math.abs(diffZ) <= 9.999999747378752E-6d) {
            return;
        }
        float delta = (float) ((((-Math.atan2(diffZ, diffX)) * 180.0d) / 3.141592653589793d) + 90.0d);
        deltaConsumer.accept(delta);
    }
}
