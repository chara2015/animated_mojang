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
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/screen/MixinChatComponent.class */
@Mixin({gjf.class})
public abstract class MixinChatComponent implements VersionedChatComponent {
    private final gfc labyMod$invalidMessage = new gfc(0, yh.i(), (yu) null, (gfd) null);
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
    private final Map<yh, ChatMessageMeta> labyMod$chatMessageMeta = new HashMap();

    @Shadow
    @Final
    gfj k;

    @Shadow
    @Final
    private List<gfc> m;

    @Shadow
    @Final
    private List<a> n;

    @Unique
    @Nullable
    private gfc labyMod$cachedGuiMessage;

    @Shadow
    protected abstract int o();

    @Shadow
    protected abstract double q();

    @Shadow
    protected abstract gfc d(gfc gfcVar);

    @Inject(method = {"render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;IIIZZ)V"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelChatRender(gir $$0, gio $$1, int $$2, int $$3, int $$4, boolean $$5, boolean $$6, CallbackInfo ci) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
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
        Objects.requireNonNull(this.k.g);
        return 9;
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = @At(value = "NEW", target = "(ILnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)Lnet/minecraft/client/GuiMessage;"))
    private gfc labyMod$addMessage(int ticks, yh component, yu signature, gfd tag) {
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
            this.labyMod$cachedGuiMessage = new gfc(ticks, new VersionedChatMessageComponent(message, (yh) mapper.toMinecraftComponent(message.component())), signature, tag);
        }
        return this.labyMod$cachedGuiMessage;
    }

    @Inject(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = {@At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/components/ChatComponent;logChatMessage(Lnet/minecraft/client/GuiMessage;)V")}, cancellable = true)
    private void labyMod$preventNullMessageAdd(yh $$0, yu $$1, gfd $$2, CallbackInfo ci) {
        if (this.labyMod$cachedGuiMessage == this.labyMod$invalidMessage) {
            ci.cancel();
        }
        this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
    }

    @Inject(method = {"deleteMessageOrDelay"}, at = {@At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$deleteMessage(yu $$0, CallbackInfoReturnable<Object> cir, @Local gfc message) {
        VersionedChatMessageComponent versionedChatMessageComponentB = message.b();
        if (versionedChatMessageComponentB instanceof VersionedChatMessageComponent) {
            VersionedChatMessageComponent messageComponent = versionedChatMessageComponentB;
            ComponentMapper mapper = Laby.labyAPI().minecraft().componentMapper();
            gfc deletedMarker = d(message);
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
        gsb gsbVar = this.k.x;
        if (gsbVar instanceof LabyScreenRenderer) {
            LabyScreenRenderer screenRenderer = (LabyScreenRenderer) gsbVar;
            cir.setReturnValue(Boolean.valueOf(screenRenderer.screen() instanceof ChatInputOverlay));
        }
    }

    @Insert(method = {"rescaleChat()V"}, at = @At("TAIL"))
    private void labyMod$chatUpdated(InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }

    @Inject(method = {"storeState"}, at = {@At("HEAD")})
    private void labyMod$storeState(CallbackInfoReturnable<j> cir) {
        Laby.references().chatController().storeState();
    }

    @Inject(method = {"restoreState"}, at = {@At("TAIL")})
    private void labyMod$restoreState(j $$0, CallbackInfo ci) {
        Laby.references().chatController().restoreState();
    }

    @Overwrite
    public boolean e() {
        gsb gsbVar = this.k.x;
        if (!(gsbVar instanceof LabyScreenRenderer)) {
            return false;
        }
        LabyScreenRenderer screenRenderer = (LabyScreenRenderer) gsbVar;
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
        if (!(versionedScreen instanceof gqy)) {
            return false;
        }
        return true;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public List<gfc> getMessages() {
        return this.m;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public List<a> getFormattedMessages() {
        return this.n;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public void injectFormattedMessages(int index, yh component, gfc message) {
        gfd tag = message.d();
        int maxWidth = bgj.c(((double) o()) / q());
        if (tag != null && tag.f() != null) {
            maxWidth -= (tag.f().c + 4) + 2;
        }
        List<bfr> formatted = gjj.a(component, maxWidth, this.k.g);
        int i = 0;
        while (i < formatted.size()) {
            bfr sequence = formatted.get(i);
            boolean end = i == formatted.size() - 1;
            this.n.add(index, new a(message.a(), sequence, tag, end));
            i++;
        }
    }

    private ChatTrustLevel labyMod$evaluateTrustLevel(gfd messageTag) {
        if (messageTag == null || messageTag.h() == null) {
            return ChatTrustLevel.SECURE;
        }
        switch (messageTag.h().toLowerCase(Locale.ROOT)) {
        }
        return ChatTrustLevel.SECURE;
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public void setMessageMeta(yh component, ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }

    @Override // net.labymod.v1_21_11.client.chat.VersionedChatComponent
    public void clearMessageMeta(yh component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
