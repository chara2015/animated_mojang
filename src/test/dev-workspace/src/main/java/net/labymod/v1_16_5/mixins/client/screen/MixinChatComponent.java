package net.labymod.v1_16_5.mixins.client.screen;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.FontHeightAccessor;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_16_5.client.chat.VersionedChatComponent;
import net.labymod.v1_16_5.client.chat.VersionedChatMessageCharSequence;
import net.labymod.v1_16_5.client.chat.VersionedChatMessageComponent;
import net.labymod.v1_16_5.client.gui.screen.LabyScreenRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/screen/MixinChatComponent.class */
@Mixin({dlk.class})
public abstract class MixinChatComponent implements VersionedChatComponent {

    @Shadow
    @Final
    private djz b;

    @Shadow
    @Final
    private List<dju<nr>> d;

    @Shadow
    @Final
    private List<dju<afa>> e;
    private final ChatScreenUpdateEvent refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
    private final Collection<nr> chatVisibilityMessages = new HashSet();

    @Shadow
    public abstract int d();

    @Shadow
    public abstract double f();

    @Insert(method = {"enqueueMessage"}, at = @At("HEAD"))
    private void labyMod$enqueueMessage(nr message, InsertInfo ci) {
        this.chatVisibilityMessages.add(message);
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;I)V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$cancelChatRender(dfm stack, int i, InsertInfo callback) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            callback.cancel();
        }
    }

    @ModifyConstant(method = {"render"}, constant = {@Constant(doubleValue = 9.0d)})
    private double labyMod$modifyChatTextHeight(double value) {
        return getFontHeight();
    }

    @ModifyConstant(method = {"render"}, constant = {@Constant(doubleValue = -8.0d)})
    private double labyMod$modifyChatTextHeight0(double value) {
        return -(((double) getFontHeight()) - 1.0d);
    }

    @ModifyConstant(method = {"render"}, constant = {@Constant(intValue = 9)})
    private int labyMod$modifyChatBackgroundHeight(int value) {
        return getFontHeight();
    }

    private int getFontHeight() {
        FontHeightAccessor fontHeightAccessor = djz.C().g;
        if (!(fontHeightAccessor instanceof FontHeightAccessor)) {
            Objects.requireNonNull(fontHeightAccessor);
            return 9;
        }
        FontHeightAccessor fontHeightAccessor2 = fontHeightAccessor;
        return MathHelper.ceil(fontHeightAccessor2.getHeight());
    }

    @ModifyVariable(method = {"addMessage(Lnet/minecraft/network/chat/Component;I)V"}, at = @At("HEAD"), argsOnly = true)
    private nr labyMod$addMessage(nr component) {
        ChatVisibility chatVisibility;
        if (this.chatVisibilityMessages.remove(component)) {
            chatVisibility = ChatVisibility.SHOWN;
        } else {
            chatVisibility = ChatVisibility.COMMANDS_ONLY;
        }
        ChatVisibility visibility = chatVisibility;
        LabyAPI labyAPI = Laby.labyAPI();
        ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        Component mapped = mapper.fromMinecraftComponent(component);
        ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(visibility));
        if (message != null) {
            return new VersionedChatMessageComponent(message, (nr) mapper.toMinecraftComponent(message.component()));
        }
        return null;
    }

    @Redirect(method = {"addMessage(Lnet/minecraft/network/chat/Component;IIZ)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<afa> labyMod$injectChatMessages(nu text, int maxWidth, dku font) {
        List<afa> lines = dln.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        ChatMessage message = ((VersionedChatMessageComponent) text).message();
        lines.replaceAll(wrapped -> {
            return new VersionedChatMessageCharSequence(message, wrapped);
        });
        return lines;
    }

    @Insert(method = {"addMessage(Lnet/minecraft/network/chat/Component;I)V"}, at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$preventNullMessageAdd(nr component, int index, InsertInfo callback) {
        if (component == null) {
            callback.cancel();
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
        dot screen = this.b.y;
        if (screen instanceof LabyScreenRenderer) {
            cir.setReturnValue(Boolean.valueOf(((LabyScreenRenderer) screen).screen() instanceof ChatInputOverlay));
        }
    }

    @Insert(method = {"rescaleChat()V"}, at = @At("TAIL"))
    private void labyMod$chatUpdated(InsertInfo callback) {
        Laby.fireEvent(this.refreshScreenChatScreenUpdateEvent);
    }

    @Override // net.labymod.v1_16_5.client.chat.VersionedChatComponent
    public List<dju<nr>> getMessages() {
        return this.d;
    }

    @Override // net.labymod.v1_16_5.client.chat.VersionedChatComponent
    public List<dju<afa>> getFormattedMessages() {
        return this.e;
    }

    @Override // net.labymod.v1_16_5.client.chat.VersionedChatComponent
    public void injectFormattedMessages(int index, nr component, dju<nr> message) {
        int maxWidth = afm.c(((double) d()) / f());
        List<afa> formatted = labyMod$injectChatMessages(component, maxWidth, this.b.g);
        for (afa sequence : formatted) {
            this.e.add(index, new dju<>(message.b(), sequence, message.c()));
        }
    }
}
