package net.labymod.v1_18_2.mixins.client.screen;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_18_2.client.chat.VersionedChatMessageComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/screen/MixinChatComponentDeleteMessage.class */
@Mixin({eaf.class})
public class MixinChatComponentDeleteMessage {

    @Shadow
    @Final
    private List<dym<qk>> e;

    @Insert(method = {"removeById"}, at = @At("HEAD"))
    private void labyMod$deleteChatMessage(int index, InsertInfo callback) {
        for (dym<qk> message : this.e) {
            if (message.c() == index) {
                VersionedChatMessageComponent versionedChatMessageComponent = (qk) message.a();
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
