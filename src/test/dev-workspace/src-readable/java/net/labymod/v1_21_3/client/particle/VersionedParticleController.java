package net.labymod.v1_21_3.client.particle;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/particle/VersionedParticleController.class */
@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        jh position = new jh(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        fmg.Q().g.a(position, getMinecraftDirection(direction));
    }

    public jm getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return jm.a;
            case UP:
                return jm.b;
            case NORTH:
                return jm.c;
            case SOUTH:
                return jm.d;
            case WEST:
                return jm.e;
            case EAST:
                return jm.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
