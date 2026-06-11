package net.labymod.v1_21_11.mixins.client.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.List;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.v1_21_11.client.chat.VersionedChatMessageCharSequence;
import net.labymod.v1_21_11.client.chat.VersionedChatMessageComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/screen/MixinGuiMessage.class */
@Mixin({gfc.class})
public class MixinGuiMessage {
    @WrapOperation(method = {"splitLines"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;")})
    private List<bfr> labyMod$wrapComponents(yn message, int maxWidth, gio font, Operation<List<bfr>> original) {
        List<bfr> lines = (List) original.call(new Object[]{message, Integer.valueOf(maxWidth), font});
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
