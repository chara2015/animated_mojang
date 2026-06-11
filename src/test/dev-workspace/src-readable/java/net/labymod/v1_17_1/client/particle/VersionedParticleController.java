package net.labymod.v1_17_1.client.particle;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/particle/VersionedParticleController.class */
@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        gg position = new gg(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        dvp.C().g.a(position, getMinecraftDirection(direction));
    }

    public gl getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return gl.a;
            case UP:
                return gl.b;
            case NORTH:
                return gl.c;
            case SOUTH:
                return gl.d;
            case WEST:
                return gl.e;
            case EAST:
                return gl.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
