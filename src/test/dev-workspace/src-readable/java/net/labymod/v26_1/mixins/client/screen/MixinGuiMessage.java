package net.labymod.v26_1.mixins.client.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.List;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.v26_1.client.chat.VersionedChatMessageCharSequence;
import net.labymod.v26_1.client.chat.VersionedChatMessageComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/screen/MixinGuiMessage.class */
@Mixin({GuiMessage.class})
public class MixinGuiMessage {
    @WrapOperation(method = {"splitLines"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;")})
    private List<FormattedCharSequence> labyMod$wrapComponents(FormattedText message, int maxWidth, Font font, Operation<List<FormattedCharSequence>> original) {
        List<FormattedCharSequence> lines = (List) original.call(new Object[]{message, Integer.valueOf(maxWidth), font});
        if (!(message instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        VersionedChatMessageComponent versionedMessage = (VersionedChatMessageComponent) message;
        ChatMessage chatMessage = versionedMessage.message();
        lines.replaceAll(wrapped -> {
            return new VersionedChatMessageCharSequence(chatMessage, wrapped);
        });
        return lines;
    }
}
