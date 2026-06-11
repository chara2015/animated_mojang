package net.labymod.api.client.world.signobject;

import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/SignObjectPosition.class */
public interface SignObjectPosition {
    BlockPosition position();

    SignBlockEntity.SignSide side();

    float rotationYaw();

    FloatVector3 calculatePosition(float f, float f2, float f3);
}
