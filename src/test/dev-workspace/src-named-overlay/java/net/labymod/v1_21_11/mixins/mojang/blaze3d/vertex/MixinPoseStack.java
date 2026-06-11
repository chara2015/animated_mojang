package net.labymod.v1_21_11.mixins.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/mojang/blaze3d/vertex/MixinPoseStack.class */
@Mixin({PoseStack.class})
public class MixinPoseStack implements VanillaStackAccessor {
    private Stack labyMod$stack;

    @Shadow
    private int lastIndex;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$createApiStack(CallbackInfo ci) {
        this.labyMod$stack = Stack.create(this);
    }

    public Stack stack(Object bufferSource) {
        return this.labyMod$stack.multiBufferSource(bufferSource);
    }

    public int index() {
        return this.lastIndex;
    }
}

