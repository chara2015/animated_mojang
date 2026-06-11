package net.labymod.v1_21_1.client.entity;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/entity/VersionedEntityPoseMapper.class */
@Singleton
@Implements(EntityPoseMapper.class)
public class VersionedEntityPoseMapper implements EntityPoseMapper {
    @Inject
    public VersionedEntityPoseMapper() {
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    public EntityPose fromMinecraft(Object pose) {
        if (!(pose instanceof bua)) {
            return null;
        }
        bua minecraftPose = (bua) pose;
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

    /* JADX INFO: renamed from: net.labymod.v1_21_1.client.entity.VersionedEntityPoseMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/entity/VersionedEntityPoseMapper$1.class */
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
            $SwitchMap$net$minecraft$world$entity$Pose = new int[bua.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.a.ordinal()] = 1;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.b.ordinal()] = 2;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.c.ordinal()] = 3;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.d.ordinal()] = 4;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.e.ordinal()] = 5;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.f.ordinal()] = 6;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.g.ordinal()] = 7;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.h.ordinal()] = 8;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.i.ordinal()] = 9;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.j.ordinal()] = 10;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.l.ordinal()] = 11;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.m.ordinal()] = 12;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.n.ordinal()] = 13;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.o.ordinal()] = 14;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.k.ordinal()] = 15;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.p.ordinal()] = 16;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.q.ordinal()] = 17;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bua.r.ordinal()] = 18;
            } catch (NoSuchFieldError e36) {
            }
        }
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    @Nullable
    public Object toMinecraft(EntityPose entityPose) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$entity$EntityPose[entityPose.ordinal()]) {
            case 1:
                return bua.f;
            case 2:
                return bua.c;
            case 3:
                return bua.h;
            case 4:
                return bua.b;
            case 5:
                return bua.d;
            case 6:
                return bua.a;
            case 7:
                return bua.e;
            case 8:
                return bua.g;
            case 9:
                return bua.i;
            case 10:
                return bua.j;
            case 11:
                return bua.k;
            case 12:
                return bua.l;
            case 13:
                return bua.m;
            case 14:
                return bua.n;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return bua.o;
            case 16:
                return bua.p;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                return bua.q;
            case 18:
                return bua.r;
            default:
                return null;
        }
    }
}
