package net.labymod.v1_21_11.client.particle;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/particle/VersionedParticleController.class */
@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController {
    @Inject
    public VersionedParticleController() {
    }

    public void crackBlock(FloatVector3 blockPosition, Direction direction) {
        BlockPos position = new BlockPos(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        Minecraft.getInstance().level.addBreakingBlockEffect(position, getMinecraftDirection(direction));
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.client.particle.VersionedParticleController$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/particle/VersionedParticleController$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$util$math$Direction = new int[Direction.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.UP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public net.minecraft.core.Direction getMinecraftDirection(Direction direction) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$util$math$Direction[direction.ordinal()]) {
            case 1:
                return net.minecraft.core.Direction.DOWN;
            case 2:
                return net.minecraft.core.Direction.UP;
            case 3:
                return net.minecraft.core.Direction.NORTH;
            case 4:
                return net.minecraft.core.Direction.SOUTH;
            case 5:
                return net.minecraft.core.Direction.WEST;
            case 6:
                return net.minecraft.core.Direction.EAST;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
