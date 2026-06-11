package net.labymod.v1_20_1.mixins.client.renderer.fov;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/fov/MixinGameRenderer_Fov.class */
@Mixin({fjq.class})
public class MixinGameRenderer_Fov {

    @Shadow
    @Final
    enn j;

    @Shadow
    private float q;

    @Shadow
    private float r;
    private int labyMod$tick;

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void labyMod$tick(CallbackInfo ci) {
        this.labyMod$tick++;
    }

    @Inject(method = {"tickFov()V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F", shift = At.Shift.BEFORE)}, cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(CallbackInfo ci) {
        float modifier = 1.0f;
        fiv fivVarAl = this.j.al();
        if (fivVarAl instanceof fiv) {
            fiv cameraEntity = fivVarAl;
            modifier = cameraEntity.m();
        }
        FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.q, this.r, modifier, this.labyMod$tick);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.q = fieldOfViewTickEvent.getFov();
        this.r = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Inject(method = {"getFov"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$fireFieldOfViewEvent(emz $$0, float $$1, boolean $$2, CallbackInfoReturnable<Double> cir) throws MatchException {
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

    /* JADX INFO: renamed from: net.labymod.v1_20_1.mixins.client.renderer.fov.MixinGameRenderer_Fov$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/fov/MixinGameRenderer_Fov$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$material$FogType = new int[dxg.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[dxg.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[dxg.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[dxg.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[dxg.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
