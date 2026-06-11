package net.labymod.v1_8_9.client.client.particle;

import javax.inject.Inject;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/client/particle/VersionedParticleController.class */
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        cj position = new cj(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        ave.A().j.a(position, getMinecraftDirection(direction));
    }

    public cq getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return cq.a;
            case UP:
                return cq.b;
            case NORTH:
                return cq.c;
            case SOUTH:
                return cq.d;
            case WEST:
                return cq.e;
            case EAST:
                return cq.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
