package net.labymod.v1_8_9.mixins.client.screen;

import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/screen/MixinGuiIngameMenu.class */
@Mixin({axp.class})
public class MixinGuiIngameMenu extends axu {
    private boolean isConfirmed = false;

    @Inject(method = {"initGui"}, at = {@At("HEAD")})
    public void resetConfirmed(CallbackInfo ci) {
        this.isConfirmed = false;
    }

    @Inject(method = {"actionPerformed"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isIntegratedServerRunning()Z", shift = At.Shift.BEFORE)}, cancellable = true)
    private void confirmDisconnect(avs button, CallbackInfo ci) {
        if (!this.j.F() && LabyMod.getInstance().config().multiplayer().confirmDisconnect().get().booleanValue() && !this.isConfirmed) {
            this.isConfirmed = true;
            button.j = "§c" + bnq.a("labymod.activity.menu.button.confirmDisconnect", new Object[0]);
            ci.cancel();
        }
    }
}
