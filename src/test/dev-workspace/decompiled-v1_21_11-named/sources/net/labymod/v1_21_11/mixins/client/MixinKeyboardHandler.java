package net.labymod.v1_21_11.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import net.labymod.api.event.client.input.CharacterTypedEvent;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.core.client.input.KeyboardBridge;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinKeyboardHandler.class */
@Mixin({KeyboardHandler.class})
public class MixinKeyboardHandler {
    private final ChatScreenUpdateEvent tooltipChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.ITEM_TOOLTIPS);
    private Key labyMod$cancelledCharKey;

    @Shadow
    @Final
    private Minecraft c;
    private Key labyMod$pseudoUnpressedKey;

    @Inject(method = {"keyPress"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$handleKeyPress(long windowHandle, int action, KeyEvent event, CallbackInfo callback) {
        KeyEvent.State state;
        switch (action) {
            case 1:
                state = KeyEvent.State.PRESS;
                break;
            case 2:
                state = KeyEvent.State.HOLDING;
                break;
            default:
                state = KeyEvent.State.UNPRESSED;
                break;
        }
        KeyEvent.State state2 = state;
        boolean press = state2 != KeyEvent.State.UNPRESSED;
        int glfwKey = event.key();
        Key key = DefaultKeyMapper.resolveGlfwKey(press, glfwKey, event.modifiers(), event.scancode());
        if (key == null || key == Key.NONE) {
            return;
        }
        InputType inputType = KeyMapper.getInputType(key);
        net.labymod.api.event.client.input.KeyEvent keyEvent = Laby.fireEvent(new net.labymod.api.event.client.input.KeyEvent(state2, key));
        if (keyEvent.inputType() == InputType.CHARACTER) {
            if (press && keyEvent.isCancelled()) {
                this.labyMod$cancelledCharKey = key;
            } else {
                this.labyMod$cancelledCharKey = null;
            }
        }
        if (keyEvent.isCancelled()) {
            callback.cancel();
            return;
        }
        if (!labyMod$handToKeyboardBridge(windowHandle)) {
            return;
        }
        KeyboardBridge bridge = Laby.references().keyboardBridge();
        if (press) {
            if (!bridge.onKeyPress(key, inputType)) {
                return;
            }
        } else if (!bridge.onKeyRelease(key, inputType)) {
            return;
        }
        if (inputType == InputType.CHARACTER) {
            this.labyMod$cancelledCharKey = key;
        }
        callback.cancel();
    }

    @Inject(method = {"charTyped"}, at = {@At("TAIL")})
    private void labyMod$resetPseudoUnpressedKey(long windowHandle, CharacterEvent event, CallbackInfo callback) {
        if (this.labyMod$pseudoUnpressedKey != null) {
            DefaultKeyMapper.setLastPressed(this.labyMod$pseudoUnpressedKey == Key.NONE ? null : this.labyMod$pseudoUnpressedKey);
            this.labyMod$pseudoUnpressedKey = null;
        }
    }

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$handleCharTyped(long windowHandle, CharacterEvent event, CallbackInfo callback) {
        Key key = DefaultKeyMapper.lastPressed();
        if (key == null || KeyMapper.getInputType(key) != InputType.CHARACTER) {
            this.labyMod$pseudoUnpressedKey = key;
            DefaultKeyMapper.setLastPressed(Key.NONE);
            key = Key.NONE;
        }
        if (key == this.labyMod$cancelledCharKey) {
            this.labyMod$cancelledCharKey = null;
            callback.cancel();
            return;
        }
        boolean handToBridge = labyMod$handToKeyboardBridge(windowHandle);
        KeyboardBridge keyboardBridge = Laby.references().keyboardBridge();
        int codepoint = event.codepoint();
        if (Character.charCount(codepoint) == 1) {
            char character = (char) codepoint;
            if (Laby.fireEvent(new CharacterTypedEvent(key, character)).isCancelled()) {
                callback.cancel();
                return;
            } else {
                if (handToBridge && keyboardBridge.onCharTyped(key, character)) {
                    callback.cancel();
                    return;
                }
                return;
            }
        }
        boolean consumed = false;
        for (char character2 : Character.toChars(codepoint)) {
            if (Laby.fireEvent(new CharacterTypedEvent(key, character2)).isCancelled()) {
                consumed = true;
            } else if (handToBridge) {
                consumed |= keyboardBridge.onCharTyped(key, character2);
            }
        }
        if (consumed) {
            callback.cancel();
        }
    }

    @Inject(method = {"handleDebugKeys"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Options;advancedItemTooltips:Z", shift = At.Shift.AFTER, ordinal = 2)})
    private void labyMod$refreshChatForAdvancedItemTooltips(net.minecraft.client.input.KeyEvent $$0, CallbackInfoReturnable<Boolean> cir) {
        Laby.fireEvent(this.tooltipChatScreenUpdateEvent);
    }

    @Redirect(method = {"keyPress"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyEvent;hasControlDownWithQuirk()Z"))
    private boolean labyMod$disableNarratorHotkey(net.minecraft.client.input.KeyEvent instance) {
        if (((Boolean) Laby.labyAPI().config().hotkeys().disableNarratorHotkey().get()).booleanValue()) {
            return false;
        }
        return instance.hasControlDown();
    }

    private boolean labyMod$handToKeyboardBridge(long windowHandle) {
        return Laby.labyAPI().minecraft().isIngame() && windowHandle == this.c.getWindow().handle() && this.c.screen != null;
    }
}
