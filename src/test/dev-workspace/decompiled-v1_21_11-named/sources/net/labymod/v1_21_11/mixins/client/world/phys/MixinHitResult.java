package net.labymod.v1_21_11.mixins.client.world.phys;

import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/phys/MixinHitResult.class */
@Mixin({HitResult.class})
public abstract class MixinHitResult implements net.labymod.api.client.world.phys.hit.HitResult {

    @Shadow
    @Final
    protected Vec3 a;
    private FloatVector3 labyMod$hitLocation;

    public FloatVector3 location() {
        if (this.labyMod$hitLocation == null) {
            if (this.a == null) {
                this.labyMod$hitLocation = new FloatVector3(0.0f, 0.0f, 0.0f);
            } else {
                this.labyMod$hitLocation = new FloatVector3((float) this.a.x, (float) this.a.y, (float) this.a.z);
            }
        }
        return this.labyMod$hitLocation;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.world.phys.MixinHitResult$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/phys/MixinHitResult$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$phys$HitResult$Type = new int[HitResult.Type.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$phys$HitResult$Type[HitResult.Type.MISS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$phys$HitResult$Type[HitResult.Type.BLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$phys$HitResult$Type[HitResult.Type.ENTITY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    protected HitResult.HitType labyMod$getHitType(HitResult.Type type) {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$phys$HitResult$Type[type.ordinal()]) {
            case 1:
                return HitResult.HitType.MISS;
            case 2:
                return HitResult.HitType.BLOCK;
            case 3:
                return HitResult.HitType.ENTITY;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
    }
}
