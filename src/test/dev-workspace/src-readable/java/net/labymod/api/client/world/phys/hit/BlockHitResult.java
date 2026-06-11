package net.labymod.api.client.world.phys.hit;

import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/phys/hit/BlockHitResult.class */
public interface BlockHitResult extends HitResult {
    FloatVector3 getBlockPosition();

    Direction getBlockDirection();

    boolean isInside();
}
