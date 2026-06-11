package net.labymod.v1_21_11.mixins.client.renderer.fov;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.FieldOfViewEvent;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/fov/MixinGameRenderer_Fov.class */
@Mixin({GameRenderer.class})
public class MixinGameRenderer_Fov {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private float fovModifier;

    @Shadow
    private float oldFovModifier;
    private int labyMod$tick;

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void labyMod$tick(CallbackInfo ci) {
        this.labyMod$tick++;
    }

    @Inject(method = {"tickFov()V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier(ZF)F", shift = At.Shift.BEFORE)}, cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(CallbackInfo ci) {
        float modifier = 1.0f;
        AbstractClientPlayer cameraEntity = this.minecraft.getCameraEntity();
        if (cameraEntity instanceof AbstractClientPlayer) {
            AbstractClientPlayer cameraEntity2 = cameraEntity;
            modifier = cameraEntity2.getFieldOfViewModifier(this.minecraft.options.getCameraType().isFirstPerson(), ((Double) this.minecraft.options.fovEffectScale().get()).floatValue());
        }
        FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.fovModifier, this.oldFovModifier, modifier, this.labyMod$tick);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.fovModifier = fieldOfViewTickEvent.getFov();
        this.oldFovModifier = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Inject(method = {"getFov"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$fireFieldOfViewEvent(Camera $$0, float $$1, boolean $$2, CallbackInfoReturnable<Float> cir) throws MatchException {
        FieldOfViewEvent.FogType fogType;
        float originalFieldOfView = cir.getReturnValueF();
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$material$FogType[$$0.getFluidInCamera().ordinal()]) {
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
                fogType = FieldOfViewEvent.FogType.ATMOSPHERIC;
                break;
            case 5:
                fogType = FieldOfViewEvent.FogType.NONE;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        FieldOfViewEvent.FogType type = fogType;
        FieldOfViewEvent event = Laby.fireEvent(new FieldOfViewEvent(type, originalFieldOfView, $$1, $$2));
        if (event.isCancelled()) {
            cir.setReturnValue(Float.valueOf(event.getFov()));
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.renderer.fov.MixinGameRenderer_Fov$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/fov/MixinGameRenderer_Fov$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$material$FogType = new int[FogType.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[FogType.LAVA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[FogType.WATER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[FogType.POWDER_SNOW.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[FogType.ATMOSPHERIC.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$material$FogType[FogType.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }
}

