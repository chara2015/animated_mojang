package net.labymod.v26_1_2.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/MixinCamera_Events.class */
@Mixin({Camera.class})
public class MixinCamera_Events {

    @Shadow
    private int matrixPropertiesDirty;

    @Shadow
    @Final
    private static int DIRTY_VIEW_ROT;

    @Shadow
    @Final
    private static int DIRTY_VIEW_ROT_PROJ;

    @Inject(method = {"update"}, at = {@At("HEAD")})
    private void labyMod$invalidateMatrixProperties(DeltaTracker deltaTracker, CallbackInfo ci) {
        this.matrixPropertiesDirty |= DIRTY_VIEW_ROT;
        this.matrixPropertiesDirty |= DIRTY_VIEW_ROT_PROJ;
    }

    @WrapOperation(method = {"getViewRotationMatrix"}, at = {@At(value = "INVOKE", target = "Lorg/joml/Matrix4f;rotation(Lorg/joml/Quaternionfc;)Lorg/joml/Matrix4f;")})
    private Matrix4f labyMod$setViewMatrix(Matrix4f instance, Quaternionfc quat, Operation<Matrix4f> original) {
        ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera) Laby.labyAPI().minecraft().getCamera();
        Matrix4f instance2 = (Matrix4f) original.call(new Object[]{instance, quat});
        if (camera != null) {
            float partialTicks = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
            float eyeHeight = camera.getEyeHeight(partialTicks);
            CameraEyeHeightEvent event = (CameraEyeHeightEvent) Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
            instance2.translate(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
        }
        return instance2.set(MinecraftUtil.setViewMatrix(instance2));
    }
}
