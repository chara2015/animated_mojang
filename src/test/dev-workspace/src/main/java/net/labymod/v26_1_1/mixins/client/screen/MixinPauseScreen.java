package net.labymod.v26_1_1.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.main.LabyMod;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/screen/MixinPauseScreen.class */
@Mixin({PauseScreen.class})
public abstract class MixinPauseScreen extends Screen {

    @Unique
    private boolean labyMod$confirmDisconnect;

    protected MixinPauseScreen(Component $$0) {
        super($$0);
    }

    @Inject(method = {"lambda$createPauseMenu$6"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$confirmDisconnect(Button button, CallbackInfo ci) {
        if (LabyMod.getInstance().config().multiplayer().confirmDisconnect().get().booleanValue() && !this.labyMod$confirmDisconnect) {
            this.labyMod$confirmDisconnect = true;
            TranslatableComponent component = net.labymod.api.client.component.Component.translatable("labymod.activity.menu.button.confirmDisconnect", NamedTextColor.RED);
            button.setMessage((Component) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component));
            ci.cancel();
        }
    }
}
