package net.labymod.v1_20_6.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/screen/MixinPauseScreen.class */
@Mixin({fna.class})
public abstract class MixinPauseScreen extends fnf {
    private boolean isConfirmed;

    protected MixinPauseScreen(xp param0) {
        super(param0);
    }

    @Inject(method = {"createPauseMenu"}, at = {@At("HEAD")})
    private void resetState(CallbackInfo ci) {
        this.isConfirmed = false;
    }

    @Inject(method = {"lambda$createPauseMenu$6(Lnet/minecraft/client/gui/components/Button;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void confirmDisconnect(fhg button, CallbackInfo ci) {
        if (!this.m.T() && LabyMod.getInstance().config().multiplayer().confirmDisconnect().get().booleanValue() && !this.isConfirmed) {
            this.isConfirmed = true;
            TranslatableComponent component = Component.translatable("labymod.activity.menu.button.confirmDisconnect", NamedTextColor.RED);
            button.b((xp) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component));
            ci.cancel();
        }
    }
}
