package net.labymod.v1_21_10.mixins.client.laby3d.matrix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.labymod.api.Laby;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/laby3d/matrix/MixinCachedPerspectiveProjectionMatrixBuffer_Tracking.class */
@Mixin({hfc.class})
public class MixinCachedPerspectiveProjectionMatrixBuffer_Tracking {

    @Unique
    private Matrix4f labyMod$projectionMatrix = new Matrix4f();

    @Inject(method = {"getBuffer"}, at = {@At("HEAD")})
    private void labyMod$updateMatrix(int $$0, int $$1, float $$2, CallbackInfoReturnable<GpuBufferSlice> cir) {
        Laby.references().gameTransformations().projectionMatrix().set(this.labyMod$projectionMatrix);
    }

    @WrapOperation(method = {"getBuffer"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CachedPerspectiveProjectionMatrixBuffer;createProjectionMatrix(IIF)Lorg/joml/Matrix4f;")})
    private Matrix4f labyMod$storeMatrix(hfc instance, int $$0, int $$1, float $$2, Operation<Matrix4f> original) {
        Matrix4f projectionMatrix = (Matrix4f) original.call(new Object[]{instance, Integer.valueOf($$0), Integer.valueOf($$1), Float.valueOf($$2)});
        this.labyMod$projectionMatrix.set(projectionMatrix);
        return projectionMatrix;
    }
}
