package net.labymod.v1_19_4.mixins.client.window;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/window/MixinWindowVideoMode.class */
@Mixin({etj.class})
public class MixinWindowVideoMode {
    @Inject(at = {@At("HEAD")}, method = {"removed"})
    private void screenRemoved(CallbackInfo ci) {
        emh.N().aM().g();
    }
}
