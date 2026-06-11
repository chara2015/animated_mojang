package net.labymod.core.client.world.signobject;

import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.FloatVector4;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/signobject/DefaultSignObjectPosition.class */
public class DefaultSignObjectPosition implements SignObjectPosition {
    private final BlockPosition position;
    private final SignBlockEntity.SignSide side;
    private final float rotationYaw;
    private final FloatVector3 center = new FloatVector3();
    private final FloatMatrix4 target = new FloatMatrix4();
    private final FloatVector4 rotation = new FloatVector4();

    public DefaultSignObjectPosition(BlockPosition position, SignBlockEntity.SignSide side, float rotationYaw) {
        this.position = position;
        this.side = side;
        this.rotationYaw = rotationYaw;
    }

    @Override // net.labymod.api.client.world.signobject.SignObjectPosition
    public BlockPosition position() {
        return this.position;
    }

    @Override // net.labymod.api.client.world.signobject.SignObjectPosition
    public SignBlockEntity.SignSide side() {
        return this.side;
    }

    @Override // net.labymod.api.client.world.signobject.SignObjectPosition
    public float rotationYaw() {
        return this.side.modifyYaw(this.rotationYaw);
    }

    @Override // net.labymod.api.client.world.signobject.SignObjectPosition
    public FloatVector3 calculatePosition(float deltaX, float deltaY, float deltaZ) {
        BlockPosition sign = position();
        this.center.set(sign.getX() + 0.5f, sign.getY(), sign.getZ() + 0.5f);
        this.target.identity();
        this.target.rotate(rotationYaw(), 0.0f, 1.0f, 0.0f);
        return transform(deltaX, deltaY, deltaZ);
    }

    private FloatVector3 transform(float offsetX, float offsetY, float offsetZ) {
        this.rotation.set(offsetX, 0.0f, offsetZ, 0.0f);
        this.rotation.transform(this.target);
        return new FloatVector3(this.rotation.getX() + this.center.getX(), offsetY + this.center.getY(), this.rotation.getZ() + this.center.getZ());
    }

    public String toString() {
        BlockPosition pos = position();
        return "SignObjectPosition(" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + "," + rotationYaw() + "°)";
    }
}
