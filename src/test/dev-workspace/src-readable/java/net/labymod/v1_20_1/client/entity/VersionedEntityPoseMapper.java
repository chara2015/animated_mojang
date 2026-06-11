package net.labymod.v1_20_1.client.entity;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/entity/VersionedEntityPoseMapper.class */
@Singleton
@Implements(EntityPoseMapper.class)
public class VersionedEntityPoseMapper implements EntityPoseMapper {
    @Inject
    public VersionedEntityPoseMapper() {
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    public EntityPose fromMinecraft(Object pose) {
        if (!(pose instanceof bgl)) {
            return null;
        }
        bgl minecraftPose = (bgl) pose;
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
            default:
                return EntityPose.UNKNOWN;
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_1.client.entity.VersionedEntityPoseMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/entity/VersionedEntityPoseMapper$1.class */
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
            $SwitchMap$net$minecraft$world$entity$Pose = new int[bgl.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.a.ordinal()] = 1;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.b.ordinal()] = 2;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.c.ordinal()] = 3;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.d.ordinal()] = 4;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.e.ordinal()] = 5;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.f.ordinal()] = 6;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.g.ordinal()] = 7;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.h.ordinal()] = 8;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.i.ordinal()] = 9;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.j.ordinal()] = 10;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.l.ordinal()] = 11;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.m.ordinal()] = 12;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.n.ordinal()] = 13;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.o.ordinal()] = 14;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$Pose[bgl.k.ordinal()] = 15;
            } catch (NoSuchFieldError e30) {
            }
        }
    }

    @Override // net.labymod.api.client.entity.EntityPoseMapper
    @Nullable
    public Object toMinecraft(EntityPose entityPose) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$entity$EntityPose[entityPose.ordinal()]) {
            case 1:
                return bgl.f;
            case 2:
                return bgl.c;
            case 3:
                return bgl.h;
            case 4:
                return bgl.b;
            case 5:
                return bgl.d;
            case 6:
                return bgl.a;
            case 7:
                return bgl.e;
            case 8:
                return bgl.g;
            case 9:
                return bgl.i;
            case 10:
                return bgl.j;
            case 11:
                return bgl.k;
            case 12:
                return bgl.l;
            case 13:
                return bgl.m;
            case 14:
                return bgl.n;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return bgl.o;
            default:
                return null;
        }
    }
}
