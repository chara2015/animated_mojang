package net.labymod.api.client.particle;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/particle/ParticleController.class */
@Referenceable
public interface ParticleController {
    void crackBlock(FloatVector3 floatVector3, Direction direction);
}
