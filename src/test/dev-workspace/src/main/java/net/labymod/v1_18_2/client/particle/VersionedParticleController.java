package net.labymod.v1_18_2.client.particle;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/particle/VersionedParticleController.class */
@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        gj position = new gj(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        dyr.D().g.a(position, getMinecraftDirection(direction));
    }

    public go getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return go.a;
            case UP:
                return go.b;
            case NORTH:
                return go.c;
            case SOUTH:
                return go.d;
            case WEST:
                return go.e;
            case EAST:
                return go.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
