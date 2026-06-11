package net.labymod.v1_16_5.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/screen/MixinChatScreen.class */
@Mixin({dnq.class})
public abstract class MixinChatScreen extends dot implements ChatScreenAccessor {

    @Shadow
    protected dlq a;

    @Shadow
    dlm q;

    @Shadow
    protected abstract void a(String str, boolean z);

    @Shadow
    protected abstract void b();

    public MixinChatScreen(nr param0) {
        super(param0);
    }

    @Redirect(method = {"render", "mouseClicked"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;getClickedComponentStyleAt(DD)Lnet/minecraft/network/chat/Style;"))
    public ob cancelHoveredComponent(dlk chatComponent, double x, double y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            return null;
        }
        return chatComponent.b(x, y);
    }

    @Inject(method = {"mouseScrolled"}, at = {@At(value = "RETURN", ordinal = 1)}, cancellable = true)
    public void cancelMouseScrolled(double $$0, double $$1, double $$2, CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V"))
    private void labyMod$preventBackgroundRender(dfm poseStack, int left, int top, int right, int bottom, int color) {
    }

    @Inject(method = {"init"}, at = {@At("TAIL")})
    public void labyMod$modifyInputBounds(CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    public void labyMod$modifyInputBounds(dfm param0, int param1, int param2, float param3, CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    private void labyMod$updateInputBounds() {
        DivWidget widget;
        LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (!(screen instanceof ChatInputOverlay) || (widget = ((ChatInputOverlay) screen).getInputUnderlay()) == null) {
            return;
        }
        widget.backgroundColor().set(Integer.valueOf(this.i.k.a(Integer.MIN_VALUE)));
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.a.l = ((int) rectangle.getX()) + 2;
        this.a.m = ((int) rectangle.getY()) + 2;
        this.a.b((int) rectangle.getWidth());
        this.a.setHeight((int) rectangle.getHeight());
        this.q.setBottomY((int) (rectangle.getY() - 2.0f));
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public void insertChatText(String text, boolean override, boolean skipInput) {
        a(text, override);
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public String getChatText() {
        return this.a.b();
    }
}
