package net.labymod.v26_1_1.mixins.client.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/screen/MixinChatScreen.class */
@Mixin({ChatScreen.class})
public abstract class MixinChatScreen extends Screen implements ChatScreenAccessor {

    @Shadow
    protected EditBox input;

    @Shadow
    CommandSuggestions commandSuggestions;

    @Shadow
    public abstract String normalizeChatMessage(String str);

    public MixinChatScreen(Component component) {
        super(component);
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public void insertChatText(String text, boolean override, boolean skipInput) {
        insertText(text, override);
    }

    @Inject(method = {"init"}, at = {@At("TAIL")})
    private void labyMod$modifyInputBounds(CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @Inject(method = {"extractRenderState"}, at = {@At("HEAD")})
    private void labyMod$modifyInputBounds(GuiGraphicsExtractor param0, int param1, int param2, float param3, CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @WrapOperation(method = {"mouseClicked"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ActiveTextCollector$ClickableStyleFinder;result()Lnet/minecraft/network/chat/Style;")})
    public Style cancelHoveredComponent(ActiveTextCollector.ClickableStyleFinder instance, Operation<Style> original) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            return null;
        }
        return (Style) original.call(new Object[]{instance});
    }

    @Inject(method = {"mouseScrolled"}, at = {@At(value = "RETURN", ordinal = 1)}, cancellable = true)
    public void cancelMouseScrolled(double $$0, double $$1, double $$2, double $$3, CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get().booleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Redirect(method = {"extractRenderState"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"))
    private void labyMod$preventBackgroundRender(GuiGraphicsExtractor graphics, int left, int top, int right, int bottom, int color) {
    }

    @Overwrite
    public void handleChatInput(String message, boolean showInHistory) {
        ChatMessageSendEvent event = (ChatMessageSendEvent) Laby.fireEvent(new ChatMessageSendEvent(normalizeChatMessage(message), showInHistory));
        if (event.getHistoryText() != null && !event.getHistoryText().trim().isEmpty()) {
            this.minecraft.gui.getChat().addRecentChat(event.getHistoryText());
        }
        if (event.isCancelled() || event.getMessage() == null || event.getMessage().trim().isEmpty()) {
            return;
        }
        String message2 = event.getMessage();
        if (message2.startsWith("/")) {
            this.minecraft.player.connection.sendCommand(message2.substring(1));
        } else {
            this.minecraft.player.connection.sendChat(message2);
        }
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
        widget.backgroundColor().set(Integer.valueOf(this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE)));
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.input.setX(((int) rectangle.getX()) + 2);
        this.input.setY(((int) rectangle.getY()) + 2);
        this.input.setWidth((int) rectangle.getWidth());
        this.input.setHeight((int) rectangle.getHeight());
        this.commandSuggestions.setBottomY((int) (rectangle.getY() - 2.0f));
    }

    @Override // net.labymod.core.client.accessor.chat.ChatScreenAccessor
    public String getChatText() {
        return this.input.getValue();
    }
}
