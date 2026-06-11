package net.labymod.v1_21.mixins.mojang.blaze3d.vertex;

import java.util.Deque;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/mojang/blaze3d/vertex/MixinPoseStack.class */
@Mixin({fbi.class})
public class MixinPoseStack implements VanillaStackAccessor {
    private Stack labyMod$stack;

    @Shadow
    @Final
    private Deque<a> a;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$createApiStack(CallbackInfo ci) {
        this.labyMod$stack = Stack.create(this);
    }

    @Override // net.labymod.api.client.render.matrix.VanillaStackAccessor
    public Stack stack(Object bufferSource) {
        return this.labyMod$stack.multiBufferSource(bufferSource);
    }

    @Override // net.labymod.api.client.render.matrix.VanillaStackAccessor
    public int index() {
        return this.a.size() - 1;
    }
}
