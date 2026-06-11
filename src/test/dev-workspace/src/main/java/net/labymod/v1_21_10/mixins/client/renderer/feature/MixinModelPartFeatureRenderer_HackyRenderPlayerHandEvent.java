package net.labymod.v1_21_10.mixins.client.renderer.feature;

import net.labymod.api.Laby;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/feature/MixinModelPartFeatureRenderer_HackyRenderPlayerHandEvent.class */
@Mixin({hxp.class})
public class MixinModelPartFeatureRenderer_HackyRenderPlayerHandEvent {
    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$fakePlayerHandPre(CallbackInfo ci) {
        if (MinecraftUtil.prePlayerModelRenderHandEvent != null) {
            Laby.fireEvent(MinecraftUtil.prePlayerModelRenderHandEvent);
            MinecraftUtil.prePlayerModelRenderHandEvent = null;
        }
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$fakePlayerHandPost(hgx $$0, a $$1, hfz $$2, a $$3, CallbackInfo ci) {
        if (MinecraftUtil.postPlayerModelRenderHandEvent != null) {
            $$1.b();
            Laby.fireEvent(MinecraftUtil.postPlayerModelRenderHandEvent);
            MinecraftUtil.postPlayerModelRenderHandEvent = null;
        }
    }
}
