package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_12_2.client.gui.ScrollableGuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiChat.class */
@Mixin({bkn.class})
public abstract class MixinGuiChat extends blk implements ChatScreenAccessor, ScrollableGuiScreen {

    @Shadow
    protected bje a;
    private int labyMod$scrollAmount;

    @Shadow
    protected abstract void a(String str, boolean z);

    @Shadow
    public abstract void b();

    @Redirect(method = {"drawScreen(IIF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;getChatComponent(II)Lnet/minecraft/util/text/ITextComponent;"))
    public hh labyMod$cancelHoveredComponent$drawScreen(bjb gui, int x, int y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            return null;
        }
        return gui.a(x, y);
    }

    @Redirect(method = {"mouseClicked(III)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;getChatComponent(II)Lnet/minecraft/util/text/ITextComponent;"))
    public hh labyMod$cancelHoveredComponent$mouseClicked(bjb gui, int x, int y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            return null;
        }
        return gui.a(x, y);
    }

    @Redirect(method = {"drawScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiChat;drawRect(IIIII)V"))
    private void labyMod$preventBackgroundRender(int left, int top, int right, int bottom, int color) {
    }

    @Inject(method = {"initGui"}, at = {@At("TAIL")})
    public void labyMod$modifyInputBounds(CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @Inject(method = {"drawScreen"}, at = {@At("HEAD")})
    public void labyMod$modifyInputBounds(int param1, int param2, float param3, CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    private void labyMod$updateInputBounds() {
        DivWidget widget;
        LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (!(screen instanceof ChatInputOverlay) || (widget = ((ChatInputOverlay) screen).getInputUnderlay()) == null) {
            return;
        }
        widget.backgroundColor().set(Integer.MIN_VALUE);
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.a.a = ((int) rectangle.getX()) + 2;
        this.a.f = ((int) rectangle.getY()) + 2;
        this.a.setWidth((int) rectangle.getWidth());
        this.a.setHeight((int) rectangle.getHeight());
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public void insertChatText(String text, boolean override, boolean skipInput) {
        a(text, override);
    }

    @Redirect(method = {"handleMouseInput"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", remap = false))
    private int labyMod$customScrollHandling() {
        int delta = this.labyMod$scrollAmount;
        this.labyMod$scrollAmount = 0;
        return delta;
    }

    @Override // net.labymod.v1_12_2.client.gui.ScrollableGuiScreen
    public boolean mouseScrolled(double delta) {
        this.labyMod$scrollAmount = (int) delta;
        return true;
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public String getChatText() {
        return this.a.b();
    }
}
