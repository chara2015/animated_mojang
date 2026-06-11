package net.labymod.v26_1_2.mixins.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import net.labymod.v26_1_2.client.chat.ChatMessageMeta;
import net.labymod.v26_1_2.client.chat.VersionedChatComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/multiplayer/chat/MixinChatListener.class */
@Mixin({ChatListener.class})
public class MixinChatListener {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = {"handleSystemMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(Component message, boolean overlay, CallbackInfo ci) {
        if (overlay) {
            return;
        }
        chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }

    @Inject(method = {"handleSystemMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(Component message, boolean overlay, CallbackInfo ci) {
        chatComponent().clearMessageMeta(message);
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeSystemChatType(Component component, ChatType.Bound chatType, CallbackInfo ci) {
        chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }

    @Inject(method = {"handleDisguisedChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearSystemChatType(Component component, ChatType.Bound chatType, CallbackInfo ci) {
        chatComponent().clearMessageMeta(component);
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("HEAD")})
    private void labyMod$storeChatMessageChatType(PlayerChatMessage chatMessage, GameProfile gameProfile, ChatType.Bound chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.minecraft.options.onlyShowSecureChat().get()).booleanValue();
        PlayerChatMessage msg = onlyShowSecureChat ? chatMessage.removeUnsignedContent() : chatMessage;
        chatComponent().setMessageMeta(chatType.decorate(msg.decoratedContent()), ChatMessageMeta.player(gameProfile != null ? gameProfile.id() : null));
    }

    @Inject(method = {"handlePlayerChatMessage"}, at = {@At("RETURN")})
    private void labyMod$clearChatMessageChatType(PlayerChatMessage chatMessage, GameProfile gameProfile, ChatType.Bound chatType, CallbackInfo ci) {
        boolean onlyShowSecureChat = ((Boolean) this.minecraft.options.onlyShowSecureChat().get()).booleanValue();
        PlayerChatMessage msg = onlyShowSecureChat ? chatMessage.removeUnsignedContent() : chatMessage;
        chatComponent().clearMessageMeta(chatType.decorate(msg.decoratedContent()));
    }

    private VersionedChatComponent chatComponent() {
        return Minecraft.getInstance().gui.getChat();
    }
}
