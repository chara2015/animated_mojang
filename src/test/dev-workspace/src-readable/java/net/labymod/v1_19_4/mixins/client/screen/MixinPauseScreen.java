package net.labymod.v1_19_4.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/screen/MixinPauseScreen.class */
@Mixin({esy.class})
public abstract class MixinPauseScreen extends etd {
    private boolean isConfirmed;

    protected MixinPauseScreen(tj param0) {
        super(param0);
    }

    @Inject(method = {"createPauseMenu"}, at = {@At("HEAD")})
    private void resetState(CallbackInfo ci) {
        this.isConfirmed = false;
    }

    @Inject(method = {"lambda$createPauseMenu$5(Lnet/minecraft/client/gui/components/Button;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void confirmDisconnect(eoc button, CallbackInfo ci) {
        if (!this.e.Q() && LabyMod.getInstance().config().multiplayer().confirmDisconnect().get().booleanValue() && !this.isConfirmed) {
            this.isConfirmed = true;
            TranslatableComponent component = Component.translatable("labymod.activity.menu.button.confirmDisconnect", new Component[0]);
            button.b((tj) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component.style(Style.style(NamedTextColor.RED))));
            ci.cancel();
        }
    }
}
