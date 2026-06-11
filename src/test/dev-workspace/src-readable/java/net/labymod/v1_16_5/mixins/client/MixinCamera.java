package net.labymod.v1_16_5.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinCamera.class */
@Mixin({djk.class})
public abstract class MixinCamera implements ExtendedMinecraftCamera {
    private final Quaternionf labymod$rotation = new Quaternionf();
    private final DoubleVector3 labyMod$position = new DoubleVector3();

    @Shadow
    private float o;

    @Shadow
    private float n;

    @Shadow
    @Final
    private d k;

    @Shadow
    public abstract dcn b();

    @Shadow
    public abstract float e();

    @Shadow
    public abstract float d();

    @ModifyArgs(method = {"setup"}, at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.setRotation(FF)V", ordinal = 0))
    private void setDirectionYawAndPitch(Args args) {
        CameraRotationEvent event = (CameraRotationEvent) Laby.fireEvent(new CameraRotationEvent(((Float) args.get(0)).floatValue(), ((Float) args.get(1)).floatValue()));
        args.setAll(new Object[]{Float.valueOf(event.getYaw()), Float.valueOf(event.getPitch())});
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    @NotNull
    public DoubleVector3 position() {
        dcn position = b();
        this.labyMod$position.set(position.a(a.a), position.a(a.b), position.a(a.c));
        return this.labyMod$position;
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    @NotNull
    public Quaternionf cameraRotation() {
        this.labymod$rotation.set(this.k.a(), this.k.b(), this.k.c(), this.k.d());
        return this.labymod$rotation;
    }

    @Override // net.labymod.core.client.world.ExtendedMinecraftCamera
    public float getEyeHeight(float partialTicks) {
        return MathHelper.lerp(this.n, this.o, partialTicks);
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    public float getYaw() {
        return e();
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    public float getPitch() {
        return d();
    }
}
