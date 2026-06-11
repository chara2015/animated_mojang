package net.labymod.v1_21_10.client.particle;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/particle/VersionedParticleController.class */
@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    @Override // net.labymod.api.client.particle.ParticleController
    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        ja position = new ja(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        fzz.W().r.d(position, getMinecraftDirection(direction));
    }

    public jg getMinecraftDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                return jg.a;
            case UP:
                return jg.b;
            case NORTH:
                return jg.c;
            case SOUTH:
                return jg.d;
            case WEST:
                return jg.e;
            case EAST:
                return jg.f;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
