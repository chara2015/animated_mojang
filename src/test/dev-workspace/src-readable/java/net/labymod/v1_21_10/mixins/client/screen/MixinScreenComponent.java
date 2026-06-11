package net.labymod.v1_21_10.mixins.client.screen;

import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/screen/MixinScreenComponent.class */
@Mixin({gmj.class})
public class MixinScreenComponent {

    @Shadow
    @Nullable
    protected fzz n;

    @Shadow
    @Final
    private static Logger a;

    @Inject(method = {"clickCommandAction"}, cancellable = true, at = {@At("HEAD")})
    private static void labyMod$clickCommandAction(hep player, String message, gmj screen, CallbackInfo ci) {
        String message2;
        ci.cancel();
        ChatMessageSendEvent event = (ChatMessageSendEvent) Laby.fireEvent(new ChatMessageSendEvent(message, false));
        if (event.isCancelled() || (message2 = event.getMessage()) == null || message2.isEmpty()) {
            return;
        }
        player.c.a(ek.a(bgh.g(message2)), screen);
    }
}
