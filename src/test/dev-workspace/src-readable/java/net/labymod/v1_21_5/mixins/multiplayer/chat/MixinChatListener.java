package net.labymod.v1_21_5.mixins.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import net.labymod.v1_21_5.client.chat.ChatMessageMeta;
import net.labymod.v1_21_5.client.chat.VersionedChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/multiplayer/chat/MixinChatListener.class */
@Mixin({gmi.class})
public class MixinChatListener {

    @Shadow
    @Final
    private fqq b;

    @Inject(method = {"handleSystemMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(xg message, boolean overlay, CallbackInfo ci) {
        if (overlay) {
            return;
        }
        chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }

    @Inject(method = {"handleSystemMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(xg message, boolean overlay, CallbackInfo ci) {
        chatComponent().clearMessageMeta(message);
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(xg component, a chatType, CallbackInfo ci) {
        chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(xg component, a chatType, CallbackInfo ci) {
        chatComponent().clearMessageMeta(component);
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeChatMessageChatType(xw chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.n.aj().c()).booleanValue();
        xw msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().setMessageMeta(chatType.a(msg.d()), ChatMessageMeta.player(gameProfile != null ? gameProfile.getId() : null));
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearChatMessageChatType(xw chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.n.aj().c()).booleanValue();
        xw msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().clearMessageMeta(chatType.a(msg.d()));
    }

    private VersionedChatComponent chatComponent() {
        return fqq.Q().m.d();
    }
}
