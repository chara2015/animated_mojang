package net.labymod.v1_21_5.mixins.client.screen;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.FontHeightAccessor;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_21_5.client.chat.ChatMessageMeta;
import net.labymod.v1_21_5.client.chat.VersionedChatComponent;
import net.labymod.v1_21_5.client.chat.VersionedChatMessageCharSequence;
import net.labymod.v1_21_5.client.chat.VersionedChatMessageComponent;
import net.labymod.v1_21_5.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_21_5.client.gui.screen.VersionedScreenWrapper;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/screen/MixinChatComponent.class */
@Mixin({ftz.class})
public abstract class MixinChatComponent implements VersionedChatComponent {
    private final fqj labyMod$invalidMessage = new fqj(0, xg.i(), (xs) null, (fqk) null);
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
    private final Map<xg, ChatMessageMeta> labyMod$chatMessageMeta = new HashMap();

    @Shadow
    @Final
    private fqq i;

    @Shadow
    @Final
    private List<fqj> k;

    @Shadow
    @Final
    private List<a> l;

    @Unique
    @Nullable
    private fqj labyMod$cachedGuiMessage;

    @Shadow
    public abstract int f();

    @Shadow
    public abstract double h();

    @Shadow
    protected abstract fqj d(fqj fqjVar);

    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelChatRender(ftk $$0, int $$1, int $$2, int $$3, boolean $$4, CallbackInfo ci) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            ci.cancel();
        }
    }

    @ModifyConstant(method = {"getLineHeight"}, constant = {@Constant(doubleValue = 9.0d)})
    private double labyMod$modifyChatTextHeight(double value) {
        return labyMod$getFontHeight();
    }

    @ModifyConstant(method = {"render"}, constant = {@Constant(doubleValue = -8.0d)})
    private double labyMod$modifyChatTextHeight0(double value) {
        return -(((double) labyMod$getFontHeight()) - 1.0d);
    }

    @ModifyConstant(method = {"render"}, constant = {@Constant(intValue = 9)})
    private int labyMod$modifyChatBackgroundHeight(int value) {
        return labyMod$getFontHeight();
    }

    private int labyMod$getFontHeight() {
        FontHeightAccessor fontHeightAccessor = fqq.Q().h;
        if (!(fontHeightAccessor instanceof FontHeightAccessor)) {
            Objects.requireNonNull(fontHeightAccessor);
            return 9;
        }
        FontHeightAccessor fontHeightAccessor2 = fontHeightAccessor;
        return MathHelper.ceil(fontHeightAccessor2.getHeight());
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = @At(value = "NEW", target = "(ILnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)Lnet/minecraft/client/GuiMessage;"))
    private fqj labyMod$addMessage(int ticks, xg component, xs signature, fqk tag) {
        ChatMessageMeta meta = this.labyMod$chatMessageMeta.remove(component);
        if (meta == null) {
            meta = ChatMessageMeta.system();
        }
        LabyAPI labyAPI = Laby.labyAPI();
        ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        Component mapped = mapper.fromMinecraftComponent(component);
        ChatTrustLevel trustLevel = labyMod$evaluateTrustLevel(tag);
        ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(meta.visibility()).trustLevel(trustLevel).sender(meta.getSender()));
        if (message == null) {
            this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
        } else {
            this.labyMod$cachedGuiMessage = new fqj(ticks, new VersionedChatMessageComponent(message, (xg) mapper.toMinecraftComponent(message.component())), signature, tag);
        }
        return this.labyMod$cachedGuiMessage;
    }

    @Redirect(method = {"addMessageToDisplayQueue"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<azk> labyMod$injectChatMessages(xl text, int maxWidth, fti font) {
        List<azk> lines = fud.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        ChatMessage message = ((VersionedChatMessageComponent) text).message();
        lines.replaceAll(wrapped -> {
            return new VersionedChatMessageCharSequence(message, wrapped);
        });
        return lines;
    }

    @Inject(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = {@At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/components/ChatComponent;logChatMessage(Lnet/minecraft/client/GuiMessage;)V")}, cancellable = true)
    private void labyMod$preventNullMessageAdd(xg $$0, xs $$1, fqk $$2, CallbackInfo ci) {
        if (this.labyMod$cachedGuiMessage == this.labyMod$invalidMessage) {
            ci.cancel();
        }
        this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
    }

    @Inject(method = {"deleteMessageOrDelay(Lnet/minecraft/network/chat/MessageSignature;)Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;"}, at = {@At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V", shift = At.Shift.BEFORE)}, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void labyMod$deleteMessage(xs messageSignature, CallbackInfoReturnable<Object> cir, int guiTicks, ListIterator<fqj> iterator, fqj message) {
        VersionedChatMessageComponent versionedChatMessageComponentC = message.c();
        if (versionedChatMessageComponentC instanceof VersionedChatMessageComponent) {
            VersionedChatMessageComponent messageComponent = versionedChatMessageComponentC;
            ComponentMapper mapper = Laby.labyAPI().minecraft().componentMapper();
            fqj deletedMarker = d(message);
            Component mappedDeletedMarker = mapper.fromMinecraftComponent(deletedMarker.c());
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
        fzq fzqVar = this.i.z;
        if (fzqVar instanceof LabyScreenRenderer) {
            LabyScreenRenderer screenRenderer = (LabyScreenRenderer) fzqVar;
            cir.setReturnValue(Boolean.valueOf(screenRenderer.screen() instanceof ChatInputOverlay));
        }
    }

    @Insert(method = {"rescaleChat()V"}, at = @At("TAIL"))
    private void labyMod$chatUpdated(InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }

    @Inject(method = {"storeState"}, at = {@At("HEAD")})
    private void labyMod$storeState(CallbackInfoReturnable<b> cir) {
        Laby.references().chatController().storeState();
    }

    @Inject(method = {"restoreState"}, at = {@At("TAIL")})
    private void labyMod$restoreState(b $$0, CallbackInfo ci) {
        Laby.references().chatController().restoreState();
    }

    @Overwrite
    public boolean e() {
        fzq fzqVar = this.i.z;
        if (!(fzqVar instanceof LabyScreenRenderer)) {
            return false;
        }
        LabyScreenRenderer screenRenderer = (LabyScreenRenderer) fzqVar;
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
        if (!(versionedScreen instanceof fym)) {
            return false;
        }
        return true;
    }

    @Override // net.labymod.v1_21_5.client.chat.VersionedChatComponent
    public List<fqj> getMessages() {
        return this.k;
    }

    @Override // net.labymod.v1_21_5.client.chat.VersionedChatComponent
    public List<a> getFormattedMessages() {
        return this.l;
    }

    @Override // net.labymod.v1_21_5.client.chat.VersionedChatComponent
    public void injectFormattedMessages(int index, xg component, fqj message) {
        fqk tag = message.e();
        int maxWidth = azz.a(((double) f()) / h());
        if (tag != null && tag.f() != null) {
            maxWidth -= (tag.f().c + 4) + 2;
        }
        List<azk> formatted = labyMod$injectChatMessages(component, maxWidth, this.i.h);
        int i = 0;
        while (i < formatted.size()) {
            azk sequence = formatted.get(i);
            boolean end = i == formatted.size() - 1;
            this.l.add(index, new a(message.b(), sequence, tag, end));
            i++;
        }
    }

    private ChatTrustLevel labyMod$evaluateTrustLevel(fqk messageTag) {
        if (messageTag == null || messageTag.h() == null) {
            return ChatTrustLevel.SECURE;
        }
        switch (messageTag.h().toLowerCase(Locale.ROOT)) {
        }
        return ChatTrustLevel.SECURE;
    }

    @Override // net.labymod.v1_21_5.client.chat.VersionedChatComponent
    public void setMessageMeta(xg component, ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }

    @Override // net.labymod.v1_21_5.client.chat.VersionedChatComponent
    public void clearMessageMeta(xg component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
