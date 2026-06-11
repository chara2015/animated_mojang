package net.labymod.v1_16_5.mixins.client.renderer.fov;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/fov/MixinGameRenderer_Fov.class */
@Mixin({dzz.class})
public class MixinGameRenderer_Fov {

    @Shadow
    @Final
    private djz e;

    @Shadow
    private float l;

    @Shadow
    private float m;
    private int labyMod$tick;

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void labyMod$tick(CallbackInfo ci) {
        this.labyMod$tick++;
    }

    @Inject(method = {"tickFov()V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F", shift = At.Shift.BEFORE)}, cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(CallbackInfo ci) {
        float modifier = 1.0f;
        dzj dzjVarAa = this.e.aa();
        if (dzjVarAa instanceof dzj) {
            dzj cameraEntity = dzjVarAa;
            modifier = cameraEntity.v();
        }
        FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.l, this.m, modifier, this.labyMod$tick);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.l = fieldOfViewTickEvent.getFov();
        this.m = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }

    @Inject(method = {"getFov"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$fireFieldOfViewEvent(djk $$0, float $$1, boolean $$2, CallbackInfoReturnable<Double> cir) {
        float originalFieldOfView = (float) cir.getReturnValueD();
        cux fluidState = $$0.k();
        FieldOfViewEvent.FogType type = FieldOfViewEvent.FogType.NONE;
        if (fluidState.a(aef.b)) {
            type = FieldOfViewEvent.FogType.WATER;
        } else if (fluidState.a(aef.c)) {
            type = FieldOfViewEvent.FogType.LAVA;
        }
        FieldOfViewEvent event = (FieldOfViewEvent) Laby.fireEvent(new FieldOfViewEvent(type, originalFieldOfView, $$1, $$2));
        if (event.isCancelled()) {
            cir.setReturnValue(Double.valueOf(event.getFov()));
        }
    }
}
