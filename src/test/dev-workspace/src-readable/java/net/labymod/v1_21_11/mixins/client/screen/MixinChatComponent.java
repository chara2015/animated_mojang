package net.labymod.v1_21_11.mixins.client.screen;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_21_11.client.chat.ChatMessageMeta;
import net.labymod.v1_21_11.client.chat.VersionedChatComponent;
import net.labymod.v1_21_11.client.chat.VersionedChatMessageComponent;
import net.labymod.v1_21_11.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_21_11.client.gui.screen.VersionedScreenWrapper;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/screen/MixinChatComponent.class */
@Mixin({ChatComponent.class})
public abstract class MixinChatComponent implements VersionedChatComponent {
    private final GuiMessage labyMod$invalidMessage = new GuiMessage(0, Component.empty(), (MessageSignature) null, (GuiMessageTag) null);
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
    private final Map<Component, ChatMessageMeta> labyMod$chatMessageMeta = new HashMap();

    @Shadow
    @Final
    Minecraft minecraft;

    @Shadow
    @Final
    private List<GuiMessage> allMessages;

    @Shadow
    @Final
    private List<GuiMessage.Line> trimmedMessages;

    @Unique
    @Nullable
    private GuiMessage labyMod$cachedGuiMessage;

    @Shadow
    protected abstract int getWidth();

    @Shadow
    protected abstract double getScale();

    @Shadow
    protected abstract GuiMessage createDeletedMarker(GuiMessage guiMessage);

    @Inject(method = {"render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;IIIZZ)V"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelChatRender(GuiGraphics $$0, Font $$1, int $$2, int $$3, int $$4, boolean $$5, boolean $$6, CallbackInfo ci) {
        if (((Boolean) Laby.labyAPI().config().ingame().advancedChat().enabled().get()).booleanValue()) {
            ci.cancel();
        }
    }

    @ModifyConstant(method = {"getLineHeight"}, constant = {@Constant(doubleValue = 9.0d)})
    private double labyMod$modifyChatTextHeight(double value) {
        return labyMod$getFontHeight();
    }

    @ModifyConstant(method = {"render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V"}, constant = {@Constant(doubleValue = 8.0d)})
    private double labyMod$modifyChatTextHeight0(double value) {
        return ((double) labyMod$getFontHeight()) - 1.0d;
    }

    @ModifyConstant(method = {"render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V"}, constant = {@Constant(intValue = 9)})
    private int labyMod$modifyChatBackgroundHeight(int value) {
        return labyMod$getFontHeight();
    }

    private int labyMod$getFontHeight() {
        Objects.requireNonNull(this.minecraft.font);
        return 9;
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = @At(value = "NEW", target = "(ILnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)Lnet/minecraft/client/GuiMessage;"))
    private GuiMessage labyMod$addMessage(int ticks, Component component, MessageSignature signature, GuiMessageTag tag) {
        ChatMessageMeta meta = this.labyMod$chatMessageMeta.remove(component);
        if (meta == null) {
            meta = ChatMessageMeta.system();
        }
        LabyAPI labyAPI = Laby.labyAPI();
        ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        net.labymod.api.client.component.Component mapped = mapper.fromMinecraftComponent(component);
        ChatTrustLevel trustLevel = labyMod$evaluateTrustLevel(tag);
        ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(meta.visibility()).trustLevel(trustLevel).sender(meta.getSender()));
        if (message == null) {
            this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
        } else {
            this.labyMod$cachedGuiMessage = new GuiMessage(ticks, new VersionedChatMessageComponent(message, (Component) mapper.toMinecraftComponent(message.component())), signature, tag);
        }
        return this.labyMod$cachedGuiMessage;
    }

    @Inject(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = {@At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/components/ChatComponent;logChatMessage(Lnet/minecraft/client/GuiMessage;)V")}, cancellable = true)
    private void labyMod$preventNullMessageAdd(Component $$0, MessageSignature $$1, GuiMessageTag $$2, CallbackInfo ci) {
        if (this.labyMod$cachedGuiMessage == this.labyMod$invalidMessage) {
            ci.cancel();
        }
        this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
    }

    @Inject(method = {"deleteMessageOrDelay"}, at = {@At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$deleteMessage(MessageSignature $$0, CallbackInfoReturnable<Object> cir, @Local GuiMessage message) {
        VersionedChatMessageComponent versionedChatMessageComponentContent = message.content();
        if (versionedChatMessageComponentContent instanceof VersionedChatMessageComponent) {
            VersionedChatMessageComponent messageComponent = versionedChatMessageComponentContent;
            ComponentMapper mapper = Laby.labyAPI().minecraft().componentMapper();
            GuiMessage deletedMarker = createDeletedMarker(message);
            net.labymod.api.client.component.Component mappedDeletedMarker = mapper.fromMinecraftComponent(deletedMarker.content());
            messageComponent.message().edit(mappedDeletedMarker);
            cir.setReturnValue((Object) null);
        }
    }

    @Insert(method = {"clearMessages(Z)V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$clearMessages(boolean clearHistory, InsertInfo callback) {
        if (Laby.fireEvent(new ChatClearEvent(clearHistory)).isCancelled()) {
            callback.cancel();
        }
    }

    @Insert(method = {"isChatFocused()Z"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$activityChatFocused(InsertInfoReturnable<Boolean> cir) {
        Screen screen = this.minecraft.screen;
        if (screen instanceof LabyScreenRenderer) {
            LabyScreenRenderer screenRenderer = (LabyScreenRenderer) screen;
            cir.setReturnValue(Boolean.valueOf(screenRenderer.screen() instanceof ChatInputOverlay));
        }
    }

    @Insert(method = {"rescaleChat()V"}, at = @At("TAIL"))
    private void labyMod$chatUpdated(InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }

    @Inject(method = {"storeState"}, at = {@At("HEAD")})
    private void labyMod$storeState(CallbackInfoReturnable<ChatComponent.State> cir) {
        Laby.references().chatController().storeState();
    }

    @Inject(method = {"restoreState"}, at = {@At("TAIL")})
    private void labyMod$restoreState(ChatComponent.State $$0, CallbackInfo ci) {
        Laby.references().chatController().restoreState();
    }

    @Overwrite
    public boolean e() {
        Screen screen = this.minecraft.screen;
        if (!(screen instanceof LabyScreenRenderer)) {
            return false;
        }
        LabyScreenRenderer screenRenderer = (LabyScreenRenderer) screen;
        ChatInputOverlay chatInputOverlayScreen = screenRenderer.screen();
        if (!(chatInputOverlayScreen instanceof ChatInputOverlay)) {
            return false;
        }
        ChatInputOverlay overlay = chatInputOverlayScreen;
        VersionedScreenWrapper versionedScreenWrapperMostInnerScreenInstance = overlay.mostInnerScreenInstance();
        if (!(versionedScreenWrapperMostInnerScreenInstance instanceof VersionedScreenWrapper)) {
            return false;
        }
        VersionedScreenWrapper screenWrapper = versionedScreenWrapperMostInnerScreenInstance;
        Object versionedScreen = screenWrapper.getVersionedScreen();
        if (!(versionedScreen instanceof ChatScreen)) {
            return false;
        }
        return true;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public List<GuiMessage> getMessages() {
        return this.allMessages;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public List<GuiMessage.Line> getFormattedMessages() {
        return this.trimmedMessages;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public void injectFormattedMessages(int index, Component component, GuiMessage message) {
        GuiMessageTag tag = message.tag();
        int maxWidth = Mth.floor(((double) getWidth()) / getScale());
        if (tag != null && tag.icon() != null) {
            maxWidth -= (tag.icon().width + 4) + 2;
        }
        List<FormattedCharSequence> formatted = ComponentRenderUtils.wrapComponents(component, maxWidth, this.minecraft.font);
        int i = 0;
        while (i < formatted.size()) {
            FormattedCharSequence sequence = formatted.get(i);
            boolean end = i == formatted.size() - 1;
            this.trimmedMessages.add(index, new GuiMessage.Line(message.addedTime(), sequence, tag, end));
            i++;
        }
    }

    private ChatTrustLevel labyMod$evaluateTrustLevel(GuiMessageTag messageTag) {
        if (messageTag == null || messageTag.logTag() == null) {
            return ChatTrustLevel.SECURE;
        }
        switch (messageTag.logTag().toLowerCase(Locale.ROOT)) {
        }
        return ChatTrustLevel.SECURE;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public void setMessageMeta(Component component, ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public void clearMessageMeta(Component component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}

