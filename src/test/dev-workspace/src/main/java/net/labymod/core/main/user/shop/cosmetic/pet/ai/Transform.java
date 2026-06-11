package net.labymod.core.main.user.shop.cosmetic.pet.ai;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/Transform.class */
public class Transform {
    private final DoubleVector3 position = new DoubleVector3();
    private final DoubleVector3 previousPosition = new DoubleVector3();
    private final FloatVector3 rotation = new FloatVector3();
    private final FloatVector3 previousRotation = new FloatVector3();

    public void update() {
        this.previousPosition.set(this.position);
        this.previousRotation.set(this.rotation);
    }

    public void set(double x, double y, double z) {
        this.position.set(x, y, z);
    }

    public void set(DoubleVector3 position) {
        this.position.set(position);
    }

    public void rotate(Stack stack, float partialTicks) {
        float rotX = MathHelper.interpolateRotation(this.previousRotation.getX(), this.rotation.getX(), partialTicks);
        float rotY = MathHelper.interpolateRotation(this.previousRotation.getY(), this.rotation.getY(), partialTicks);
        float rotZ = MathHelper.interpolateRotation(this.previousRotation.getZ(), this.rotation.getZ(), partialTicks);
        stack.rotate(-rotZ, 0.0f, 0.0f, 1.0f);
        stack.rotate(-rotY, 0.0f, 1.0f, 0.0f);
        stack.rotate(-rotX, 1.0f, 0.0f, 0.0f);
    }

    public DoubleVector3 position() {
        return this.position;
    }

    public DoubleVector3 previousPosition() {
        return this.previousPosition;
    }

    public FloatVector3 rotation() {
        return this.rotation;
    }

    public FloatVector3 previousRotation() {
        return this.previousRotation;
    }
}
