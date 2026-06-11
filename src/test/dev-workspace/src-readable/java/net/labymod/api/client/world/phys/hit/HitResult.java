package net.labymod.api.client.world.phys.hit;

import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/phys/hit/HitResult.class */
public interface HitResult {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/phys/hit/HitResult$HitType.class */
    public enum HitType {
        MISS,
        BLOCK,
        ENTITY
    }

    FloatVector3 location();

    HitType type();
}
