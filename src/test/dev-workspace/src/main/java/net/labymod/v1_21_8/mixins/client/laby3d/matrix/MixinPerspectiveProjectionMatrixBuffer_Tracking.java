package net.labymod.v1_21_8.mixins.client.laby3d.matrix;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.labymod.api.Laby;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/laby3d/matrix/MixinPerspectiveProjectionMatrixBuffer_Tracking.class */
@Mixin({gxr.class})
public class MixinPerspectiveProjectionMatrixBuffer_Tracking {
    @Inject(method = {"getBuffer"}, at = {@At("HEAD")})
    private void labyMod$updateMatrix(Matrix4f $$0, CallbackInfoReturnable<GpuBufferSlice> cir) {
        Laby.references().gameTransformations().projectionMatrix().set($$0);
    }
}
