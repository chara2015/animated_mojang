package net.labymod.v1_21_11.client.particle;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/particle/VersionedParticleController.class */
@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        is position = new is(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        gfj.V().r.d(position, getMinecraftDirection(direction));
    }

    public iz getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return iz.a;
            case UP:
                return iz.b;
            case NORTH:
                return iz.c;
            case SOUTH:
                return iz.d;
            case WEST:
                return iz.e;
            case EAST:
                return iz.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
