package net.labymod.v1_12_2.client.client.particle;

import javax.inject.Inject;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/client/particle/VersionedParticleController.class */
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        et position = new et(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        bib.z().j.a(position, getMinecraftDirection(direction));
    }

    public fa getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return fa.a;
            case UP:
                return fa.b;
            case NORTH:
                return fa.c;
            case SOUTH:
                return fa.d;
            case WEST:
                return fa.e;
            case EAST:
                return fa.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
