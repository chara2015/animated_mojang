package net.labymod.v1_21_1.mixins.client.screen.components;

import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/screen/components/MixinCommandSuggestions.class */
@Mixin({fip.class})
public class MixinCommandSuggestions implements CommandSuggestionsAccessor {
    private static final int BOTTOM_OFFSET = 12;
    private int bottomY = -1;

    @ModifyVariable(method = {"showSuggestions"}, at = @At("LOAD"), ordinal = 2)
    private int labyMod$modifyListPosition(int y) {
        return this.bottomY != -1 ? this.bottomY : y;
    }

    @Redirect(method = {"renderUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void labyMod$modifyBackgroundPosition(fhz graphics, int left, int top, int right, int bottom, int color) {
        graphics.a(left, this.bottomY != -1 ? this.bottomY - 12 : top, right, this.bottomY != -1 ? (this.bottomY - 12) + (bottom - top) : bottom, color);
    }

    @Redirect(method = {"renderUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;III)I"))
    private int labyMod$modifyTextPosition(fhz graphics, fhx font, aya text, int x, int y, int color) {
        return graphics.b(font, text, x, this.bottomY != -1 ? (this.bottomY - 12) + 2 : y, color);
    }

    @Override // net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor
    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }
}
