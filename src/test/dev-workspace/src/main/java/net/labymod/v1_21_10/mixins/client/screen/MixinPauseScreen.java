package net.labymod.v1_21_10.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/screen/MixinPauseScreen.class */
@Mixin({gmf.class})
public abstract class MixinPauseScreen extends gmj {

    @Unique
    private boolean labyMod$confirmDisconnect;

    protected MixinPauseScreen(xx $$0) {
        super($$0);
    }

    @Inject(method = {"lambda$createPauseMenu$7"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$confirmDisconnect(gdp button, CallbackInfo ci) {
        if (LabyMod.getInstance().config().multiplayer().confirmDisconnect().get().booleanValue() && !this.labyMod$confirmDisconnect) {
            this.labyMod$confirmDisconnect = true;
            TranslatableComponent component = Component.translatable("labymod.activity.menu.button.confirmDisconnect", NamedTextColor.RED);
            button.a_((xx) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component));
            ci.cancel();
        }
    }
}
