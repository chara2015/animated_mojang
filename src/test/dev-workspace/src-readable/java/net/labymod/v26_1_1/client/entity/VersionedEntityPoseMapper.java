package net.labymod.v26_1_1.client.entity;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.models.Implements;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/entity/VersionedEntityPoseMapper.class */
@Singleton
@Implements(EntityPoseMapper.class)
public class VersionedEntityPoseMapper implements EntityPoseMapper {
    @Inject
    public VersionedEntityPoseMapper() {
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    public EntityPose fromMinecraft(Object pose) {
        if (!(pose instanceof Pose)) {
            return null;
        }
        Pose minecraftPose = (Pose) pose;
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
            case 9:
                return EntityPose.CROAKING;
            case 10:
                return EntityPose.USING_TONGUE;
            case 11:
                return EntityPose.ROARING;
            case 12:
                return EntityPose.SNIFFING;
            case 13:
                return EntityPose.EMERGING;
            case 14:
                return EntityPose.DIGGING;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return EntityPose.SITTING;
            case 16:
                return EntityPose.SLIDING;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                return EntityPose.SHOOTING;
            case 18:
                return EntityPose.INHALING;
            default:
                return EntityPose.UNKNOWN;
        }
    }

    /* JADX INFO: renamed from: net.labymod.v26_1_1.client.entity.VersionedEntityPoseMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/entity/VersionedEntityPoseMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$Pose;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$entity$EntityPose = new int[EntityPose.values().length];

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
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.CROAKING.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.USING_TONGUE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.SITTING.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.ROARING.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.SNIFFING.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.EMERGING.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.DIGGING.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.SLIDING.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.SHOOTING.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$EntityPose[EntityPose.INHALING.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            $SwitchMap$net$minecraft$world$entity$Pose = new int[Pose.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.STANDING.ordinal()] = 1;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.FALL_FLYING.ordinal()] = 2;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.SLEEPING.ordinal()] = 3;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.SWIMMING.ordinal()] = 4;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.SPIN_ATTACK.ordinal()] = 5;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.CROUCHING.ordinal()] = 6;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.LONG_JUMPING.ordinal()] = 7;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.DYING.ordinal()] = 8;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.CROAKING.ordinal()] = 9;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.USING_TONGUE.ordinal()] = 10;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.ROARING.ordinal()] = 11;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.SNIFFING.ordinal()] = 12;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.EMERGING.ordinal()] = 13;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.DIGGING.ordinal()] = 14;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.SITTING.ordinal()] = 15;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.SLIDING.ordinal()] = 16;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.SHOOTING.ordinal()] = 17;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[Pose.INHALING.ordinal()] = 18;
            } catch (NoSuchFieldError e36) {
            }
        }
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    @Nullable
    public Object toMinecraft(EntityPose entityPose) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$entity$EntityPose[entityPose.ordinal()]) {
            case 1:
                return Pose.CROUCHING;
            case 2:
                return Pose.SLEEPING;
            case 3:
                return Pose.DYING;
            case 4:
                return Pose.FALL_FLYING;
            case 5:
                return Pose.SWIMMING;
            case 6:
                return Pose.STANDING;
            case 7:
                return Pose.SPIN_ATTACK;
            case 8:
                return Pose.LONG_JUMPING;
            case 9:
                return Pose.CROAKING;
            case 10:
                return Pose.USING_TONGUE;
            case 11:
                return Pose.SITTING;
            case 12:
                return Pose.ROARING;
            case 13:
                return Pose.SNIFFING;
            case 14:
                return Pose.EMERGING;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return Pose.DIGGING;
            case 16:
                return Pose.SLIDING;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                return Pose.SHOOTING;
            case 18:
                return Pose.INHALING;
            default:
                return null;
        }
    }
}
