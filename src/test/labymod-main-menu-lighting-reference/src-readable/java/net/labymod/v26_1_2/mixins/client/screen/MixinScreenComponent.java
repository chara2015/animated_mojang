package net.labymod.v26_1_2.mixins.client.screen;

import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.Commands;
import net.minecraft.util.StringUtil;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/screen/MixinScreenComponent.class */
@Mixin({Screen.class})
public class MixinScreenComponent {

    @Shadow
    @Nullable
    protected Minecraft minecraft;

    @Shadow
    @Final
    private static Logger LOGGER;

    @Inject(method = {"clickCommandAction"}, cancellable = true, at = {@At("HEAD")})
    private static void labyMod$clickCommandAction(LocalPlayer player, String message, Screen screen, CallbackInfo ci) {
        String message2;
        ci.cancel();
        ChatMessageSendEvent event = (ChatMessageSendEvent) Laby.fireEvent(new ChatMessageSendEvent(message, false));
        if (event.isCancelled() || (message2 = event.getMessage()) == null || message2.isEmpty()) {
            return;
        }
        player.connection.sendUnattendedCommand(Commands.trimOptionalPrefix(StringUtil.filterText(message2)), screen);
    }
}
