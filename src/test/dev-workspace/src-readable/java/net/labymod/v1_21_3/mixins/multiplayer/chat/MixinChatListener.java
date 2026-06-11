package net.labymod.v1_21_3.mixins.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import net.labymod.v1_21_3.client.chat.ChatMessageMeta;
import net.labymod.v1_21_3.client.chat.VersionedChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/multiplayer/chat/MixinChatListener.class */
@Mixin({gge.class})
public class MixinChatListener {

    @Shadow
    @Final
    private fmg b;

    @Inject(method = {"handleSystemMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(xv message, boolean overlay, CallbackInfo ci) {
        if (overlay) {
            return;
        }
        chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }

    @Inject(method = {"handleSystemMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(xv message, boolean overlay, CallbackInfo ci) {
        chatComponent().clearMessageMeta(message);
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(xv component, a chatType, CallbackInfo ci) {
        chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(xv component, a chatType, CallbackInfo ci) {
        chatComponent().clearMessageMeta(component);
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeChatMessageChatType(yl chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.n.aj().c()).booleanValue();
        yl msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().setMessageMeta(chatType.a(msg.d()), ChatMessageMeta.player(gameProfile != null ? gameProfile.getId() : null));
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearChatMessageChatType(yl chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.n.aj().c()).booleanValue();
        yl msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().clearMessageMeta(chatType.a(msg.d()));
    }

    private VersionedChatComponent chatComponent() {
        return fmg.Q().m.d();
    }
}
