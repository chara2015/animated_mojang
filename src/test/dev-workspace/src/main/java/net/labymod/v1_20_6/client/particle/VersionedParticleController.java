package net.labymod.v1_20_6.client.particle;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/particle/VersionedParticleController.class */
@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        iz position = new iz(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        ffh.Q().g.a(position, getMinecraftDirection(direction));
    }

    public je getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return je.a;
            case UP:
                return je.b;
            case NORTH:
                return je.c;
            case SOUTH:
                return je.d;
            case WEST:
                return je.e;
            case EAST:
                return je.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
