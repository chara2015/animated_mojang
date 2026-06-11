package net.labymod.v1_21_10.mixins.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import net.labymod.v1_21_10.client.chat.ChatMessageMeta;
import net.labymod.v1_21_10.client.chat.VersionedChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/multiplayer/chat/MixinChatListener.class */
@Mixin({hag.class})
public class MixinChatListener {

    @Shadow
    @Final
    private fzz b;

    @Inject(method = {"handleSystemMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(xx message, boolean overlay, CallbackInfo ci) {
        if (overlay) {
            return;
        }
        chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }

    @Inject(method = {"handleSystemMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(xx message, boolean overlay, CallbackInfo ci) {
        chatComponent().clearMessageMeta(message);
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(xx component, a chatType, CallbackInfo ci) {
        chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(xx component, a chatType, CallbackInfo ci) {
        chatComponent().clearMessageMeta(component);
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeChatMessageChatType(yo chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.k.ap().c()).booleanValue();
        yo msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().setMessageMeta(chatType.a(msg.d()), ChatMessageMeta.player(gameProfile != null ? gameProfile.id() : null));
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearChatMessageChatType(yo chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.k.ap().c()).booleanValue();
        yo msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().clearMessageMeta(chatType.a(msg.d()));
    }

    private VersionedChatComponent chatComponent() {
        return fzz.W().j.e();
    }
}
