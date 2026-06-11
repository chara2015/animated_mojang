package net.labymod.v1_21_8.mixins.client.renderer.fov;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/fov/MixinGameRenderer_Fov.class */
@Mixin({gxb.class})
public class MixinGameRenderer_Fov {

    @Shadow
    @Final
    private fue k;

    @Shadow
    private float r;

    @Shadow
    private float s;
    private int labyMod$tick;

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void labyMod$tick(CallbackInfo ci) {
        this.labyMod$tick++;
    }

    @Inject(method = {"tickFov()V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier(ZF)F", shift = At.Shift.BEFORE)}, cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(CallbackInfo ci) {
        float modifier = 1.0f;
        gwf gwfVarAp = this.k.ap();
        if (gwfVarAp instanceof gwf) {
            gwf cameraEntity = gwfVarAp;
            modifier = cameraEntity.a(this.k.n.aH().a(), ((Double) this.k.n.ao().c()).floatValue());
        }
        FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.r, this.s, modifier, this.labyMod$tick);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.r = fieldOfViewTickEvent.getFov();
        this.s = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Inject(method = {"getFov"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$fireFieldOfViewEvent(ftm $$0, float $$1, boolean $$2, CallbackInfoReturnable<Float> cir) throws MatchException {
        FieldOfViewEvent.FogType fogType;
        float originalFieldOfView = cir.getReturnValueF();
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$material$FogType[$$0.m().ordinal()]) {
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
                fogType = FieldOfViewEvent.FogType.DIMENSION_OR_BOSS;
                break;
            case 5:
                fogType = FieldOfViewEvent.FogType.ATMOSPHERIC;
                break;
            case 6:
                fogType = FieldOfViewEvent.FogType.NONE;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        FieldOfViewEvent.FogType type = fogType;
        FieldOfViewEvent event = (FieldOfViewEvent) Laby.fireEvent(new FieldOfViewEvent(type, originalFieldOfView, $$1, $$2));
        if (event.isCancelled()) {
            cir.setReturnValue(Float.valueOf(event.getFov()));
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_8.mixins.client.renderer.fov.MixinGameRenderer_Fov$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/fov/MixinGameRenderer_Fov$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$material$FogType = new int[fan.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[fan.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[fan.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[fan.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[fan.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[fan.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[fan.f.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
