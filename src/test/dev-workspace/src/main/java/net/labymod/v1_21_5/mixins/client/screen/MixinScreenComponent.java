package net.labymod.v1_21_5.mixins.client.screen;

import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/screen/MixinScreenComponent.class */
@Mixin({fzq.class})
public class MixinScreenComponent {

    @Shadow
    @Nullable
    protected fqq m;

    @Shadow
    @Final
    private static Logger a;

    @Inject(method = {"handleComponentClicked"}, cancellable = true, at = {@At(value = "INVOKE", target = "Lnet/minecraft/network/chat/ClickEvent$RunCommand;command()Ljava/lang/String;", shift = At.Shift.BEFORE)})
    public void labyMod$handleRunCommand(yd style, CallbackInfoReturnable<Boolean> cir) {
        String message;
        cir.setReturnValue(true);
        ChatMessageSendEvent event = (ChatMessageSendEvent) Laby.fireEvent(new ChatMessageSendEvent(style.i().b(), false));
        if (event.isCancelled() || (message = event.getMessage()) == null || message.isEmpty()) {
            return;
        }
        String message2 = bay.g(message);
        if (message2.startsWith("/")) {
            if (!this.m.t.j.d(message2.substring(1))) {
                a.error("Not allowed to run command with signed argument from click event: '{}'", message2);
                return;
            }
            return;
        }
        a.error("Failed to run command without '/' prefix from click event: '{}'", message2);
    }
}
