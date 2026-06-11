package net.labymod.v1_21_11.mixins.client.laby3d.matrix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.labymod.api.Laby;
import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/laby3d/matrix/MixinCachedOrthoProjectionMatrixBuffer_Tracking.class */
@Mixin({CachedOrthoProjectionMatrixBuffer.class})
public class MixinCachedOrthoProjectionMatrixBuffer_Tracking {

    @Unique
    private Matrix4f labyMod$projectionMatrix = new Matrix4f();

    @Inject(method = {"getBuffer"}, at = {@At("HEAD")})
    private void labyMod$updateMatrix(float $$0, float $$1, CallbackInfoReturnable<GpuBufferSlice> cir) {
        Laby.references().gameTransformations().guiProjectionMatrix().set(this.labyMod$projectionMatrix);
    }

    @WrapOperation(method = {"getBuffer"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CachedOrthoProjectionMatrixBuffer;createProjectionMatrix(FF)Lorg/joml/Matrix4f;")})
    private Matrix4f labyMod$trackMatrix(CachedOrthoProjectionMatrixBuffer instance, float $$0, float $$1, Operation<Matrix4f> original) {
        Matrix4f projectionMatrix = (Matrix4f) original.call(new Object[]{instance, Float.valueOf($$0), Float.valueOf($$1)});
        this.labyMod$projectionMatrix.set(projectionMatrix);
        return projectionMatrix;
    }
}
