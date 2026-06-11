package net.labymod.v1_8_9.mixins.client.renderer.fov;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/fov/MixinGameRenderer_Fov.class */
@Mixin({bfk.class})
public class MixinGameRenderer_Fov {

    @Shadow
    @Final
    private ave h;

    @Shadow
    private float x;

    @Shadow
    private float y;
    private int labyMod$tick;

    @Inject(method = {"updateRenderer"}, at = {@At("HEAD")})
    private void labyMod$tick(CallbackInfo ci) {
        this.labyMod$tick++;
    }

    @Inject(method = {"updateFovModifierHand()V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getFovModifier()F", shift = At.Shift.BEFORE)}, cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(CallbackInfo ci) {
        float modifier = 1.0f;
        bet betVarAc = this.h.ac();
        if (betVarAc instanceof bet) {
            bet cameraEntity = betVarAc;
            modifier = cameraEntity.o();
        }
        FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.x, this.y, modifier, this.labyMod$tick);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.x = fieldOfViewTickEvent.getFov();
        this.y = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }

    @Inject(method = {"getFOVModifier"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$fireFieldOfViewEvent(float $$1, boolean $$2, CallbackInfoReturnable<Float> cir) {
        float originalFieldOfView = cir.getReturnValueF();
        afh block = auz.a(this.h.f, this.h.ac(), $$1);
        FieldOfViewEvent.FogType type = FieldOfViewEvent.FogType.NONE;
        if (block.t() == arm.h) {
            type = FieldOfViewEvent.FogType.WATER;
        } else if (block.t() == arm.i) {
            type = FieldOfViewEvent.FogType.LAVA;
        }
        FieldOfViewEvent event = (FieldOfViewEvent) Laby.fireEvent(new FieldOfViewEvent(type, originalFieldOfView, $$1, $$2));
        if (event.isCancelled()) {
            cir.setReturnValue(Float.valueOf(event.getFov()));
        }
    }
}
