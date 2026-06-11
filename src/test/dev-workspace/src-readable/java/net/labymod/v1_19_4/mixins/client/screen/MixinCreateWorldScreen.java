package net.labymod.v1_19_4.mixins.client.screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/screen/MixinCreateWorldScreen.class */
@Mixin({exn.class})
public class MixinCreateWorldScreen extends etd {

    @Shadow
    private ept H;

    protected MixinCreateWorldScreen(tj $$0) {
        super($$0);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.AFTER)}, cancellable = true)
    public void labyMod$cancelFooterRender(ehe $$0, int $$1, int $$2, float $$3, CallbackInfo ci) {
        super.a($$0, $$1, $$2, $$3);
        ci.cancel();
    }

    @Inject(method = {"renderDirtBackground"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$disableDirtBackground(ehe $$0, CallbackInfo ci) {
        super.b($$0);
        ci.cancel();
    }
}
