package net.labymod.v1_20_4.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/screen/MixinChatScreen.class */
@Mixin({fbs.class})
public abstract class MixinChatScreen extends fdb implements ChatScreenAccessor {

    @Shadow
    protected exp b;

    @Shadow
    exj o;

    @Shadow
    @Nullable
    protected abstract wc b(double d, double d2);

    @Shadow
    public abstract String a(String str);

    public MixinChatScreen(vf component) {
        super(component);
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public void insertChatText(String text, boolean override, boolean skipInput) {
        a_(text, override);
    }

    @Inject(method = {"init"}, at = {@At("TAIL")})
    private void labyMod$modifyInputBounds(CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$modifyInputBounds(ewu param0, int param1, int param2, float param3, CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @Redirect(method = {"render", "mouseClicked"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;getComponentStyleAt(DD)Lnet/minecraft/network/chat/Style;"))
    public wc cancelHoveredComponent(fbs chatScreen, double x, double y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            return null;
        }
        return b(x, y);
    }

    @Inject(method = {"mouseScrolled"}, at = {@At(value = "RETURN", ordinal = 1)}, cancellable = true)
    public void cancelMouseScrolled(double $$0, double $$1, double $$2, double $$3, CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Insert(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V", shift = At.Shift.BEFORE), cancellable = true)
    public void cancelRenderTooltip(ewu $$0, int $$1, int $$2, float $$3, InsertInfo ci) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            ci.cancel();
        }
    }

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void labyMod$preventBackgroundRender(ewu graphics, int left, int top, int right, int bottom, int color) {
    }

    @Overwrite
    public boolean b(String message, boolean showInHistory) {
        ChatMessageSendEvent event = (ChatMessageSendEvent) Laby.fireEvent(new ChatMessageSendEvent(a(message), showInHistory));
        if (event.getHistoryText() != null && !event.getHistoryText().trim().isEmpty()) {
            this.f.l.d().a(event.getHistoryText());
        }
        if (event.isCancelled() || event.getMessage() == null || event.getMessage().trim().isEmpty()) {
            return true;
        }
        String message2 = event.getMessage();
        if (message2.startsWith("/")) {
            this.f.s.cn.c(message2.substring(1));
            return true;
        }
        this.f.s.cn.b(message2);
        return true;
    }

    private void labyMod$updateInputBounds() {
        LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (!(screen instanceof ChatInputOverlay)) {
            return;
        }
        ChatInputOverlay overlay = (ChatInputOverlay) screen;
        DivWidget widget = overlay.getInputUnderlay();
        if (widget == null) {
            return;
        }
        widget.backgroundColor().set(Integer.valueOf(this.f.m.a(Integer.MIN_VALUE)));
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.b.n(((int) rectangle.getX()) + 2);
        this.b.o(((int) rectangle.getY()) + 2);
        this.b.l((int) rectangle.getWidth());
        this.b.setHeight((int) rectangle.getHeight());
        this.o.setBottomY((int) (rectangle.getY() - 2.0f));
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public String getChatText() {
        return this.b.a();
    }
}
