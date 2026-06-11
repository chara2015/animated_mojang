package net.labymod.v1_20_1.mixins.client.screen;

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
import net.labymod.v1_20_1.client.chat.ChatMessageMeta;
import net.labymod.v1_20_1.client.chat.VersionedChatComponent;
import net.labymod.v1_20_1.client.chat.VersionedChatMessageCharSequence;
import net.labymod.v1_20_1.client.chat.VersionedChatMessageComponent;
import net.labymod.v1_20_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_20_1.client.gui.screen.VersionedScreenWrapper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/screen/MixinChatComponent.class */
@Mixin({epj.class})
public abstract class MixinChatComponent implements VersionedChatComponent {
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
    private final Map<sw, ChatMessageMeta> labyMod$chatMessageMeta = new HashMap();

    @Shadow
    @Final
    private enn i;

    @Shadow
    @Final
    private List<enh> k;

    @Shadow
    @Final
    private List<a> l;

    @Shadow
    protected abstract void a(sw swVar, @Nullable th thVar, int i, @Nullable eni eniVar, boolean z);

    @Shadow
    public abstract int e();

    @Shadow
    public abstract double g();

    @Shadow
    protected abstract enh a(enh enhVar);

    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelChatRender(eox $$0, int $$1, int $$2, int $$3, CallbackInfo ci) {
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
        FontHeightAccessor fontHeightAccessor = enn.N().h;
        if (!(fontHeightAccessor instanceof FontHeightAccessor)) {
            Objects.requireNonNull(fontHeightAccessor);
            return 9;
        }
        FontHeightAccessor fontHeightAccessor2 = fontHeightAccessor;
        return MathHelper.ceil(fontHeightAccessor2.getHeight());
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"))
    public void labyMod$addMessage(epj instance, sw component, th signature, int ticks, eni tag, boolean v) {
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
            return;
        }
        a(new VersionedChatMessageComponent(message, (sw) mapper.toMinecraftComponent(message.component())), signature, ticks, tag, v);
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<aom> labyMod$injectChatMessages(ta text, int maxWidth, eov font) {
        List<aom> lines = epn.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        ChatMessage message = ((VersionedChatMessageComponent) text).message();
        lines.replaceAll(wrapped -> {
            return new VersionedChatMessageCharSequence(message, wrapped);
        });
        return lines;
    }

    @Insert(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"}, at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$preventNullMessageAdd(sw component, th signature, int $$2, eni messageTag, boolean $$4, InsertInfo callback) {
        if (component == null) {
            callback.cancel();
        }
    }

    @Inject(method = {"deleteMessageOrDelay(Lnet/minecraft/network/chat/MessageSignature;)Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;"}, at = {@At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V", shift = At.Shift.BEFORE)}, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void labyMod$deleteMessage(th messageSignature, CallbackInfoReturnable<Object> cir, int guiTicks, ListIterator<enh> iterator, enh message) {
        VersionedChatMessageComponent versionedChatMessageComponentB = message.b();
        if (versionedChatMessageComponentB instanceof VersionedChatMessageComponent) {
            VersionedChatMessageComponent messageComponent = versionedChatMessageComponentB;
            ComponentMapper mapper = Laby.labyAPI().minecraft().componentMapper();
            enh deletedMarker = a(message);
            Component mappedDeletedMarker = mapper.fromMinecraftComponent(deletedMarker.b());
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
        euq euqVar = this.i.z;
        if (euqVar instanceof LabyScreenRenderer) {
            LabyScreenRenderer screenRenderer = (LabyScreenRenderer) euqVar;
            cir.setReturnValue(Boolean.valueOf(screenRenderer.screen() instanceof ChatInputOverlay));
        }
    }

    @Insert(method = {"rescaleChat()V"}, at = @At("TAIL"))
    private void labyMod$chatUpdated(InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }

    @Overwrite
    private boolean m() {
        euq euqVar = this.i.z;
        if (!(euqVar instanceof LabyScreenRenderer)) {
            return false;
        }
        LabyScreenRenderer screenRenderer = (LabyScreenRenderer) euqVar;
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
        if (!(versionedScreen instanceof eti)) {
            return false;
        }
        return true;
    }

    @Override // net.labymod.v1_20_1.client.chat.VersionedChatComponent
    public List<enh> getMessages() {
        return this.k;
    }

    @Override // net.labymod.v1_20_1.client.chat.VersionedChatComponent
    public List<a> getFormattedMessages() {
        return this.l;
    }

    @Override // net.labymod.v1_20_1.client.chat.VersionedChatComponent
    public void injectFormattedMessages(int index, sw component, enh message) {
        eni tag = message.d();
        int maxWidth = apa.a(((double) e()) / g());
        if (tag != null && tag.e() != null) {
            maxWidth -= (tag.e().d + 4) + 2;
        }
        List<aom> formatted = labyMod$injectChatMessages(component, maxWidth, this.i.h);
        int i = 0;
        while (i < formatted.size()) {
            aom sequence = formatted.get(i);
            boolean end = i == formatted.size() - 1;
            this.l.add(index, new a(message.a(), sequence, tag, end));
            i++;
        }
    }

    private ChatTrustLevel labyMod$evaluateTrustLevel(eni messageTag) {
        if (messageTag == null || messageTag.g() == null) {
            return ChatTrustLevel.SECURE;
        }
        switch (messageTag.g().toLowerCase(Locale.ROOT)) {
        }
        return ChatTrustLevel.SECURE;
    }

    @Override // net.labymod.v1_20_1.client.chat.VersionedChatComponent
    public void setMessageMeta(sw component, ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }

    @Override // net.labymod.v1_20_1.client.chat.VersionedChatComponent
    public void clearMessageMeta(sw component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
