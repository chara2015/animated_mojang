package net.labymod.v1_21_11.mixins.client.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.GuiGraphics;
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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/screen/MixinChatScreen.class */
@Mixin({ChatScreen.class})
public abstract class MixinChatScreen extends Screen implements ChatScreenAccessor {

    @Shadow
    protected EditBox b;

    @Shadow
    CommandSuggestions v;

    @Shadow
    public abstract String a(String str);

    public MixinChatScreen(Component component) {
        super(component);
    }

    public void insertChatText(String text, boolean override, boolean skipInput) {
        insertText(text, override);
    }

    @Inject(method = {"init"}, at = {@At("TAIL")})
    private void labyMod$modifyInputBounds(CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$modifyInputBounds(GuiGraphics param0, int param1, int param2, float param3, CallbackInfo ci) {
        labyMod$updateInputBounds();
    }

    @WrapOperation(method = {"mouseClicked"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ActiveTextCollector$ClickableStyleFinder;result()Lnet/minecraft/network/chat/Style;")})
    public Style cancelHoveredComponent(ActiveTextCollector.ClickableStyleFinder instance, Operation<Style> original) {
        if (((Boolean) Laby.labyAPI().config().ingame().advancedChat().enabled().get()).booleanValue()) {
            return null;
        }
        return (Style) original.call(new Object[]{instance});
    }

    @Inject(method = {"mouseScrolled"}, at = {@At(value = "RETURN", ordinal = 1)}, cancellable = true)
    public void cancelMouseScrolled(double $$0, double $$1, double $$2, double $$3, CallbackInfoReturnable<Boolean> cir) {
        if (((Boolean) Laby.labyAPI().config().ingame().advancedChat().enabled().get()).booleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void labyMod$preventBackgroundRender(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
    }

    @Overwrite
    public void b(String message, boolean showInHistory) {
        ChatMessageSendEvent event = Laby.fireEvent(new ChatMessageSendEvent(a(message), showInHistory));
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
        ChatInputOverlay chatInputOverlayCurrentLabyScreen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (!(chatInputOverlayCurrentLabyScreen instanceof ChatInputOverlay)) {
            return;
        }
        ChatInputOverlay overlay = chatInputOverlayCurrentLabyScreen;
        DivWidget widget = overlay.getInputUnderlay();
        if (widget == null) {
            return;
        }
        widget.backgroundColor().set(Integer.valueOf(this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE)));
        ReasonableMutableRectangle reasonableMutableRectangleRectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.b.setX(((int) reasonableMutableRectangleRectangle.getX()) + 2);
        this.b.setY(((int) reasonableMutableRectangleRectangle.getY()) + 2);
        this.b.setWidth((int) reasonableMutableRectangleRectangle.getWidth());
        this.b.setHeight((int) reasonableMutableRectangleRectangle.getHeight());
        this.v.setBottomY((int) (reasonableMutableRectangleRectangle.getY() - 2.0f));
    }

    public String getChatText() {
        return this.b.getValue();
    }
}
