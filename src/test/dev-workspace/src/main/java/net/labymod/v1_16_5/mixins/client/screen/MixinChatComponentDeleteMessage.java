package net.labymod.v1_16_5.mixins.client.screen;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_16_5.client.chat.VersionedChatMessageComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/screen/MixinChatComponentDeleteMessage.class */
@Mixin({dlk.class})
public class MixinChatComponentDeleteMessage {

    @Shadow
    @Final
    private List<dju<nr>> d;

    @Insert(method = {"removeById"}, at = @At("HEAD"))
    private void labyMod$deleteChatMessage(int index, InsertInfo callback) {
        for (dju<nr> message : this.d) {
            if (message.c() == index) {
                VersionedChatMessageComponent versionedChatMessageComponent = (nr) message.a();
                if (versionedChatMessageComponent instanceof VersionedChatMessageComponent) {
                    ChatMessage chatMessage = versionedChatMessageComponent.message();
                    if (!chatMessage.wasDeleted()) {
                        chatMessage.delete();
                        return;
                    }
                    return;
                }
            }
        }
    }
}
