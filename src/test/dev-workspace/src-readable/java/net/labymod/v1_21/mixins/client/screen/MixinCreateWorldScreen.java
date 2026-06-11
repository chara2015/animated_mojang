package net.labymod.v1_21.mixins.client.screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/screen/MixinCreateWorldScreen.class */
@Mixin({ftc.class})
public class MixinCreateWorldScreen extends fod {

    @Shadow
    private fkq J;

    protected MixinCreateWorldScreen(wz $$0) {
        super($$0);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.AFTER)}, cancellable = true)
    public void labyMod$cancelFooterRender(fhz $$0, int $$1, int $$2, float $$3, CallbackInfo ci) {
        super.a($$0, $$1, $$2, $$3);
        ci.cancel();
    }
}
