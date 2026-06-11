package net.labymod.v1_16_5.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/screen/MixinPauseScreen.class */
@Mixin({doo.class})
public abstract class MixinPauseScreen extends dot {
    private boolean isConfirmed;

    protected MixinPauseScreen(nr param0) {
        super(param0);
    }

    @Inject(method = {"createPauseMenu"}, at = {@At("HEAD")})
    private void resetState(CallbackInfo ci) {
        this.isConfirmed = false;
    }

    @Inject(method = {"lambda$createPauseMenu$9(Lnet/minecraft/client/gui/components/Button;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void confirmDisconnect(dlj button, CallbackInfo ci) {
        if (!this.i.F() && LabyMod.getInstance().config().multiplayer().confirmDisconnect().get().booleanValue() && !this.isConfirmed) {
            this.isConfirmed = true;
            Component component = Component.translatable("labymod.activity.menu.button.confirmDisconnect", new Component[0]);
            button.a((nr) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component.style(Style.style(NamedTextColor.RED))));
            ci.cancel();
        }
    }
}
