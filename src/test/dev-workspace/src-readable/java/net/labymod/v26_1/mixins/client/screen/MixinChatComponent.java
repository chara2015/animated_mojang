package net.labymod.v26_1.mixins.client.screen;

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
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v26_1.client.chat.ChatMessageMeta;
import net.labymod.v26_1.client.chat.VersionedChatComponent;
import net.labymod.v26_1.client.chat.VersionedChatMessageComponent;
import net.labymod.v26_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.v26_1.client.gui.screen.VersionedScreenWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.client.multiplayer.chat.GuiMessageSource;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/screen/MixinChatComponent.class */
@Mixin({ChatComponent.class})
public abstract class MixinChatComponent implements VersionedChatComponent {
    private final GuiMessage labyMod$invalidMessage = new GuiMessage(0, Component.empty(), (MessageSignature) null, (GuiMessageSource) null, (GuiMessageTag) null);
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
    private final Map<Component, ChatMessageMeta> labyMod$chatMessageMeta = new HashMap();

    @Shadow
    @Final
    private Minecraft minecraft;

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
    private static GuiMessage createDeletedMarker(GuiMessage $$0) {
        return null;
    }

    @Inject(method = {"extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;IIILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;Z)V"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelChatRender(GuiGraphicsExtractor graphics, Font font, int ticks, int mouseX, int mouseY, ChatComponent.DisplayMode displayMode, boolean changeCursorOnInsertions, CallbackInfo ci) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            ci.cancel();
        }
    }

    @ModifyConstant(method = {"getLineHeight"}, constant = {@Constant(doubleValue = 9.0d)})
    private double labyMod$modifyChatTextHeight(double value) {
        return labyMod$getFontHeight();
    }

    @ModifyConstant(method = {"extractRenderState(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;)V"}, constant = {@Constant(doubleValue = 8.0d)})
    private double labyMod$modifyChatTextHeight0(double value) {
        return ((double) labyMod$getFontHeight()) - 1.0d;
    }

    @ModifyConstant(method = {"extractRenderState(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;)V"}, constant = {@Constant(intValue = 9)})
    private int labyMod$modifyChatBackgroundHeight(int value) {
        return labyMod$getFontHeight();
    }

    private int labyMod$getFontHeight() {
        Objects.requireNonNull(this.minecraft.font);
        return 9;
    }

    @Redirect(method = {"addMessage"}, at = @At(value = "NEW", target = "(ILnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/multiplayer/chat/GuiMessageSource;Lnet/minecraft/client/multiplayer/chat/GuiMessageTag;)Lnet/minecraft/client/multiplayer/chat/GuiMessage;"))
    private GuiMessage labyMod$addMessage(int addedTime, Component content, MessageSignature signature, GuiMessageSource source, GuiMessageTag tag) {
        ChatMessageMeta meta = this.labyMod$chatMessageMeta.remove(content);
        if (meta == null) {
            meta = ChatMessageMeta.system();
        }
        LabyAPI labyAPI = Laby.labyAPI();
        ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        net.labymod.api.client.component.Component mapped = mapper.fromMinecraftComponent(content);
        ChatTrustLevel trustLevel = labyMod$evaluateTrustLevel(tag);
        ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(meta.visibility()).trustLevel(trustLevel).sender(meta.getSender()));
        if (message == null) {
            this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
        } else {
            this.labyMod$cachedGuiMessage = new GuiMessage(addedTime, new VersionedChatMessageComponent(message, (Component) mapper.toMinecraftComponent(message.component())), signature, source, tag);
        }
        return this.labyMod$cachedGuiMessage;
    }

    @Inject(method = {"addMessage"}, at = {@At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/components/ChatComponent;logChatMessage(Lnet/minecraft/client/multiplayer/chat/GuiMessage;)V")}, cancellable = true)
    private void labyMod$preventNullMessageAdd(CallbackInfo ci) {
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
        if (((ChatClearEvent) Laby.fireEvent(new ChatClearEvent(clearHistory))).isCancelled()) {
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
    public boolean isChatFocused() {
        Screen screen = this.minecraft.screen;
        if (!(screen instanceof LabyScreenRenderer)) {
            return false;
        }
        LabyScreenRenderer screenRenderer = (LabyScreenRenderer) screen;
        LabyScreen labyScreenScreen = screenRenderer.screen();
        if (!(labyScreenScreen instanceof ChatInputOverlay)) {
            return false;
        }
        ChatInputOverlay overlay = (ChatInputOverlay) labyScreenScreen;
        ScreenInstance screenInstanceMostInnerScreenInstance = overlay.mostInnerScreenInstance();
        if (!(screenInstanceMostInnerScreenInstance instanceof VersionedScreenWrapper)) {
            return false;
        }
        VersionedScreenWrapper screenWrapper = (VersionedScreenWrapper) screenInstanceMostInnerScreenInstance;
        Object versionedScreen = screenWrapper.getVersionedScreen();
        if (!(versionedScreen instanceof ChatScreen)) {
            return false;
        }
        return true;
    }

    @Override // net.labymod.v26_1.client.chat.VersionedChatComponent
    public List<GuiMessage> getMessages() {
        return this.allMessages;
    }

    @Override // net.labymod.v26_1.client.chat.VersionedChatComponent
    public List<GuiMessage.Line> getFormattedMessages() {
        return this.trimmedMessages;
    }

    @Override // net.labymod.v26_1.client.chat.VersionedChatComponent
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
            this.trimmedMessages.add(index, new GuiMessage.Line(message, sequence, end));
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

    @Override // net.labymod.v26_1.client.chat.VersionedChatComponent
    public void setMessageMeta(Component component, ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }

    @Override // net.labymod.v26_1.client.chat.VersionedChatComponent
    public void clearMessageMeta(Component component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
