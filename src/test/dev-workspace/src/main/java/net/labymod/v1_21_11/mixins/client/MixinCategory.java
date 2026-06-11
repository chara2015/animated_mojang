package net.labymod.v1_21_11.mixins.client;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinCategory.class */
@Mixin({a.class})
public class MixinCategory {

    @Shadow
    @Final
    private amo i;

    @Inject(method = {"label"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$label(CallbackInfoReturnable<yh> cir) {
        if (this.i.b().equalsIgnoreCase("labymod")) {
            String path = this.i.a();
            String displayName = Laby.labyAPI().getNiceNamespace(path);
            cir.setReturnValue(yh.b(displayName));
        }
    }
}
