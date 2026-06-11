package net.labymod.v1_21_5.mixins.client.world.phys;

import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/world/phys/MixinHitResult.class */
@Mixin({fga.class})
public abstract class MixinHitResult implements HitResult {

    @Shadow
    @Final
    protected fgc a;
    private FloatVector3 labyMod$hitLocation;

    @Override // net.labymod.api.client.world.phys.hit.HitResult
    public FloatVector3 location() {
        if (this.labyMod$hitLocation == null) {
            if (this.a == null) {
                this.labyMod$hitLocation = new FloatVector3(0.0f, 0.0f, 0.0f);
            } else {
                this.labyMod$hitLocation = new FloatVector3((float) this.a.d, (float) this.a.e, (float) this.a.f);
            }
        }
        return this.labyMod$hitLocation;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_5.mixins.client.world.phys.MixinHitResult$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/world/phys/MixinHitResult$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$phys$HitResult$Type = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$phys$HitResult$Type[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$phys$HitResult$Type[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$phys$HitResult$Type[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    protected HitResult.HitType labyMod$getHitType(a type) {
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
