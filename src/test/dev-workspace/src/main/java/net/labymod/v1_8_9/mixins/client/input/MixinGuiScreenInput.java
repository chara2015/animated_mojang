package net.labymod.v1_8_9.mixins.client.input;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.JumpStatement;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_8_9.client.input.InputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/input/MixinGuiScreenInput.class */
@Mixin({axu.class})
public abstract class MixinGuiScreenInput {

    @Shadow
    protected ave j;

    @Insert(method = {"handleInput"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleMouseInput()V", shift = At.Shift.BEFORE), jumpStatement = JumpStatement.BREAK)
    private void labyMod$dispatchMouseInput(InsertInfo ii) {
        if (this.j.dispatchMouseInput()) {
            ii.jump();
        }
    }

    @Insert(method = {"handleInput"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleMouseInput()V", shift = At.Shift.AFTER))
    private void labyMod$fireMouseInput(InsertInfo ii) {
        InputHandler.fireMouseInput(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"handleInput"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleKeyboardInput()V", shift = At.Shift.BEFORE), jumpStatement = JumpStatement.CONTINUE)
    private void labyMod$dispatchKeyboardInput(InsertInfo ii) {
        LabyScreenRenderer screenRenderer = this instanceof LabyScreenRenderer ? (LabyScreenRenderer) this : null;
        if (this.j.dispatchKeyboardInput(screenRenderer)) {
            ii.jump();
        }
    }
}
