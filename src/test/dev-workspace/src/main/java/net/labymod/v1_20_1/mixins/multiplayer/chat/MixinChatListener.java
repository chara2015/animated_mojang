package net.labymod.v1_20_1.mixins.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import net.labymod.v1_20_1.client.chat.ChatMessageMeta;
import net.labymod.v1_20_1.client.chat.VersionedChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/multiplayer/chat/MixinChatListener.class */
@Mixin({ffg.class})
public class MixinChatListener {

    @Shadow
    @Final
    private enn a;

    @Inject(method = {"handleSystemMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(sw message, boolean overlay, CallbackInfo ci) {
        if (overlay) {
            return;
        }
        chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }

    @Inject(method = {"handleSystemMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(sw message, boolean overlay, CallbackInfo ci) {
        chatComponent().clearMessageMeta(message);
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(sw component, a chatType, CallbackInfo ci) {
        chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(sw component, a chatType, CallbackInfo ci) {
        chatComponent().clearMessageMeta(component);
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeChatMessageChatType(tl chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.a.m.ab().c()).booleanValue();
        tl msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().setMessageMeta(chatType.a(msg.c()), ChatMessageMeta.player(gameProfile != null ? gameProfile.getId() : null));
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearChatMessageChatType(tl chatMessage, GameProfile gameProfile, a chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.a.m.ab().c()).booleanValue();
        tl msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        chatComponent().clearMessageMeta(chatType.a(msg.c()));
    }

    private VersionedChatComponent chatComponent() {
        return enn.N().l.d();
    }
}
