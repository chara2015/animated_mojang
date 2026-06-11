package net.labymod.v1_18_2.mixins.client.renderer.fov;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.FieldOfViewEvent;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/fov/MixinGameRenderer_Fov.class */
@Mixin({eql.class})
public class MixinGameRenderer_Fov {

    @Shadow
    @Final
    private dyr i;

    @Shadow
    private float p;

    @Shadow
    private float q;
    private int labyMod$tick;

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void labyMod$tick(CallbackInfo ci) {
        this.labyMod$tick++;
    }

    @Inject(method = {"tickFov()V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F", shift = At.Shift.BEFORE)}, cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(CallbackInfo ci) {
        float modifier = 1.0f;
        ept eptVarZ = this.i.Z();
        if (eptVarZ instanceof ept) {
            ept cameraEntity = eptVarZ;
            modifier = cameraEntity.o();
        }
        FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.p, this.q, modifier, this.labyMod$tick);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.p = fieldOfViewTickEvent.getFov();
        this.q = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Inject(method = {"getFov"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$fireFieldOfViewEvent(dyb $$0, float $$1, boolean $$2, CallbackInfoReturnable<Double> cir) throws MatchException {
        FieldOfViewEvent.FogType fogType;
        float originalFieldOfView = (float) cir.getReturnValueD();
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$material$FogType[$$0.k().ordinal()]) {
            case 1:
                fogType = FieldOfViewEvent.FogType.LAVA;
                break;
            case 2:
                fogType = FieldOfViewEvent.FogType.WATER;
                break;
            case 3:
                fogType = FieldOfViewEvent.FogType.POWDER_SNOW;
                break;
            case 4:
                fogType = FieldOfViewEvent.FogType.NONE;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        FieldOfViewEvent.FogType type = fogType;
        FieldOfViewEvent event = (FieldOfViewEvent) Laby.fireEvent(new FieldOfViewEvent(type, originalFieldOfView, $$1, $$2));
        if (event.isCancelled()) {
            cir.setReturnValue(Double.valueOf(event.getFov()));
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_18_2.mixins.client.renderer.fov.MixinGameRenderer_Fov$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/fov/MixinGameRenderer_Fov$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$material$FogType = new int[diz.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[diz.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[diz.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[diz.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[diz.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
