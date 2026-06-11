package net.labymod.v1_19_4.mixins.client.screen;

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
import net.labymod.v1_19_4.client.chat.ChatMessageMeta;
import net.labymod.v1_19_4.client.chat.VersionedChatComponent;
import net.labymod.v1_19_4.client.chat.VersionedChatMessageCharSequence;
import net.labymod.v1_19_4.client.chat.VersionedChatMessageComponent;
import net.labymod.v1_19_4.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_19_4.client.gui.screen.VersionedScreenWrapper;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/screen/MixinChatComponent.class */
@Mixin({eod.class})
public abstract class MixinChatComponent implements VersionedChatComponent {
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
    private final Map<tj, ChatMessageMeta> labyMod$chatMessageMeta = new HashMap();

    @Shadow
    @Final
    private emh m;

    @Shadow
    @Final
    private List<emb> o;

    @Shadow
    @Final
    private List<a> p;

    @Shadow
    protected abstract void a(tj tjVar, @Nullable tu tuVar, int i, @Nullable emc emcVar, boolean z);

    @Shadow
    public abstract int e();

    @Shadow
    public abstract double g();

    @Shadow
    protected abstract emb a(emb embVar);

    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelChatRender(ehe $$0, int $$1, int $$2, int $$3, CallbackInfo ci) {
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
        FontHeightAccessor fontHeightAccessor = emh.N().h;
        if (!(fontHeightAccessor instanceof FontHeightAccessor)) {
            Objects.requireNonNull(fontHeightAccessor);
            return 9;
        }
        FontHeightAccessor fontHeightAccessor2 = fontHeightAccessor;
        return MathHelper.ceil(fontHeightAccessor2.getHeight());
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"))
    public void labyMod$addMessage(eod instance, tj component, tu signature, int ticks, emc tag, boolean v) {
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
        a(new VersionedChatMessageComponent(message, (tj) mapper.toMinecraftComponent(message.component())), signature, ticks, tag, v);
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<aov> labyMod$injectChatMessages(tn text, int maxWidth, enp font) {
        List<aov> lines = eoh.a(text, maxWidth, font);
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
    private void labyMod$preventNullMessageAdd(tj component, tu signature, int $$2, emc messageTag, boolean $$4, InsertInfo callback) {
        if (component == null) {
            callback.cancel();
        }
    }

    @Inject(method = {"deleteMessageOrDelay(Lnet/minecraft/network/chat/MessageSignature;)Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;"}, at = {@At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V", shift = At.Shift.BEFORE)}, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void labyMod$deleteMessage(tu messageSignature, CallbackInfoReturnable<Object> cir, int guiTicks, ListIterator<emb> iterator, emb message) {
        VersionedChatMessageComponent versionedChatMessageComponentB = message.b();
        if (versionedChatMessageComponentB instanceof VersionedChatMessageComponent) {
            VersionedChatMessageComponent messageComponent = versionedChatMessageComponentB;
            ComponentMapper mapper = Laby.labyAPI().minecraft().componentMapper();
            emb deletedMarker = a(message);
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
        etd etdVar = this.m.z;
        if (etdVar instanceof LabyScreenRenderer) {
            LabyScreenRenderer screenRenderer = (LabyScreenRenderer) etdVar;
            cir.setReturnValue(Boolean.valueOf(screenRenderer.screen() instanceof ChatInputOverlay));
        }
    }

    @Insert(method = {"rescaleChat()V"}, at = @At("TAIL"))
    private void labyMod$chatUpdated(InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }

    @Overwrite
    private boolean n() {
        etd etdVar = this.m.z;
        if (!(etdVar instanceof LabyScreenRenderer)) {
            return false;
        }
        LabyScreenRenderer screenRenderer = (LabyScreenRenderer) etdVar;
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
        if (!(versionedScreen instanceof erw)) {
            return false;
        }
        return true;
    }

    @Override // net.labymod.v1_19_4.client.chat.VersionedChatComponent
    public List<emb> getMessages() {
        return this.o;
    }

    @Override // net.labymod.v1_19_4.client.chat.VersionedChatComponent
    public List<a> getFormattedMessages() {
        return this.p;
    }

    @Override // net.labymod.v1_19_4.client.chat.VersionedChatComponent
    public void injectFormattedMessages(int index, tj component, emb message) {
        emc tag = message.d();
        int maxWidth = apj.a(((double) e()) / g());
        if (tag != null && tag.e() != null) {
            maxWidth -= (tag.e().d + 4) + 2;
        }
        List<aov> formatted = labyMod$injectChatMessages(component, maxWidth, this.m.h);
        int i = 0;
        while (i < formatted.size()) {
            aov sequence = formatted.get(i);
            boolean end = i == formatted.size() - 1;
            this.p.add(index, new a(message.a(), sequence, tag, end));
            i++;
        }
    }

    private ChatTrustLevel labyMod$evaluateTrustLevel(emc messageTag) {
        if (messageTag == null || messageTag.g() == null) {
            return ChatTrustLevel.SECURE;
        }
        switch (messageTag.g().toLowerCase(Locale.ROOT)) {
        }
        return ChatTrustLevel.SECURE;
    }

    @Override // net.labymod.v1_19_4.client.chat.VersionedChatComponent
    public void setMessageMeta(tj component, ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }

    @Override // net.labymod.v1_19_4.client.chat.VersionedChatComponent
    public void clearMessageMeta(tj component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
