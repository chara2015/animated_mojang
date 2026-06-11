package net.labymod.v1_21_11.mixins.client.renderer.feature;

import net.labymod.api.Laby;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/feature/MixinModelPartFeatureRenderer_HackyRenderPlayerHandEvent.class */
@Mixin({ModelPartFeatureRenderer.class})
public class MixinModelPartFeatureRenderer_HackyRenderPlayerHandEvent {
    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$fakePlayerHandPre(CallbackInfo ci) {
        if (MinecraftUtil.prePlayerModelRenderHandEvent != null) {
            Laby.fireEvent(MinecraftUtil.prePlayerModelRenderHandEvent);
            MinecraftUtil.prePlayerModelRenderHandEvent = null;
        }
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$fakePlayerHandPost(SubmitNodeCollection $$0, MultiBufferSource.BufferSource $$1, OutlineBufferSource $$2, MultiBufferSource.BufferSource $$3, CallbackInfo ci) {
        if (MinecraftUtil.postPlayerModelRenderHandEvent != null) {
            $$1.endBatch();
            Laby.fireEvent(MinecraftUtil.postPlayerModelRenderHandEvent);
            MinecraftUtil.postPlayerModelRenderHandEvent = null;
        }
    }
}
