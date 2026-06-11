package net.labymod.v1_21_11.mixins.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import net.labymod.v1_21_11.client.chat.ChatMessageMeta;
import net.labymod.v1_21_11.client.chat.VersionedChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/multiplayer/chat/MixinChatListener.class */
@Mixin({hiy.class})
public class MixinChatListener {

    @Shadow
    @Final
    private gfj b;

    @Inject(method = {"handleSystemMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(yh message, boolean overlay, CallbackInfo ci) {
        if (overlay) {
            return;
        }
        chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }

    @Inject(method = {"handleSystemMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(yh message, boolean overlay, CallbackInfo ci) {
        chatComponent().clearMessageMeta(message);
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(yh component, a chatType, CallbackInfo ci) {
        chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(yh component, a chatType, CallbackInfo ci) {
        chatComponent().clearMessageMeta(component);
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeChatMessageChatType(yy chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.k.ax().b()).booleanValue();
        yy msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().setMessageMeta(chatType.a(msg.d()), ChatMessageMeta.player(gameProfile != null ? gameProfile.id() : null));
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearChatMessageChatType(yy chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.b.k.ax().b()).booleanValue();
        yy msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().clearMessageMeta(chatType.a(msg.d()));
    }

    private VersionedChatComponent chatComponent() {
        return gfj.V().j.e();
    }
}
