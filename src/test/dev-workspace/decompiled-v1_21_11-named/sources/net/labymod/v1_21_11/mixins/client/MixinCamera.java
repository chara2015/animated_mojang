package net.labymod.v1_21_11.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import net.minecraft.client.Camera;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinCamera.class */
@Mixin({Camera.class})
public abstract class MixinCamera implements ExtendedMinecraftCamera {
    private final Quaternionf labymod$rotation = new Quaternionf();
    private final DoubleVector3 labyMod$position = new DoubleVector3();

    @Shadow
    private float r;

    @Shadow
    private float q;

    @Shadow
    @Final
    private Quaternionf o;

    @Shadow
    public abstract Vec3 shadow$b();

    @Shadow
    public abstract float f();

    @Shadow
    public abstract float e();

    @ModifyArgs(method = {"setup"}, at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.setRotation(FF)V", ordinal = 1))
    private void setDirectionYawAndPitch(Args args) {
        CameraRotationEvent event = Laby.fireEvent(new CameraRotationEvent(((Float) args.get(0)).floatValue(), ((Float) args.get(1)).floatValue()));
        args.setAll(new Object[]{Float.valueOf(event.getYaw()), Float.valueOf(event.getPitch())});
    }

    @NotNull
    public DoubleVector3 position() {
        Vec3 position = shadow$b();
        this.labyMod$position.set(position.get(Direction.Axis.X), position.get(Direction.Axis.Y), position.get(Direction.Axis.Z));
        return this.labyMod$position;
    }

    @NotNull
    public Quaternionf cameraRotation() {
        this.labymod$rotation.set(this.o);
        return this.labymod$rotation;
    }

    public float getEyeHeight(float partialTicks) {
        return MathHelper.lerp(this.q, this.r, partialTicks);
    }

    public float getYaw() {
        return f();
    }

    public float getPitch() {
        return e();
    }
}
