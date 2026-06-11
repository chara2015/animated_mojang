package net.labymod.v1_18_2.client.entity;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/entity/VersionedEntityPoseMapper.class */
@Singleton
@Implements(EntityPoseMapper.class)
public class VersionedEntityPoseMapper implements EntityPoseMapper {
    @Inject
    public VersionedEntityPoseMapper() {
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    public EntityPose fromMinecraft(Object pose) {
        if (!(pose instanceof ayk)) {
            return null;
        }
        ayk minecraftPose = (ayk) pose;
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$Pose[minecraftPose.ordinal()]) {
            case 1:
                return EntityPose.STANDING;
            case 2:
                return EntityPose.FALL_FLYING;
            case 3:
                return EntityPose.SLEEPING;
            case 4:
                return EntityPose.SWIMMING;
            case 5:
                return EntityPose.SPIN_ATTACK;
            case 6:
                return EntityPose.CROUCHING;
            case 7:
                return EntityPose.LONG_JUMPING;
            case 8:
                return EntityPose.DYING;
            default:
                return EntityPose.UNKNOWN;
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_18_2.client.entity.VersionedEntityPoseMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/entity/VersionedEntityPoseMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$Pose;

        static {
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.CROUCHING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.SLEEPING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.DYING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.FALL_FLYING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.SWIMMING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.STANDING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.SPIN_ATTACK.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.LONG_JUMPING.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            $SwitchMap$net$minecraft$world$entity$Pose = new int[ayk.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.a.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.b.ordinal()] = 2;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.c.ordinal()] = 3;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.d.ordinal()] = 4;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.e.ordinal()] = 5;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.f.ordinal()] = 6;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.g.ordinal()] = 7;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[ayk.h.ordinal()] = 8;
            } catch (NoSuchFieldError e16) {
            }
        }
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    @Nullable
    public Object toMinecraft(EntityPose entityPose) {
        switch (entityPose) {
            case CROUCHING:
                return ayk.f;
            case SLEEPING:
                return ayk.c;
            case DYING:
                return ayk.h;
            case FALL_FLYING:
                return ayk.b;
            case SWIMMING:
                return ayk.d;
            case STANDING:
                return ayk.a;
            case SPIN_ATTACK:
                return ayk.e;
            case LONG_JUMPING:
                return ayk.g;
            default:
                return null;
        }
    }
}
