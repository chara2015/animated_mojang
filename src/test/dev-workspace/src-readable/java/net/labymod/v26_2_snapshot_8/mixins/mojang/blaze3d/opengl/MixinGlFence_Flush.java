package net.labymod.v26_2_snapshot_8.mixins.mojang.blaze3d.opengl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.opengl.GlFence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/mojang/blaze3d/opengl/MixinGlFence_Flush.class */
@Mixin({GlFence.class})
public class MixinGlFence_Flush {
    @WrapOperation(method = {"awaitCompletion"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_glClientWaitSync(JIJ)I")})
    private int labyMod$ensureFlushBit(long sync, int flags, long timeoutMs, Operation<Integer> original) {
        return ((Integer) original.call(new Object[]{Long.valueOf(sync), Integer.valueOf(flags | 1), Long.valueOf(timeoutMs)})).intValue();
    }
}
