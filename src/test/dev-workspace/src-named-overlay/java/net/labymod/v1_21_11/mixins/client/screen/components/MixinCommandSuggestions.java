package net.labymod.v1_21_11.mixins.client.screen.components;

import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/screen/components/MixinCommandSuggestions.class */
@Mixin({CommandSuggestions.class})
public class MixinCommandSuggestions implements CommandSuggestionsAccessor {
    private static final int BOTTOM_OFFSET = 12;
    private int bottomY = -1;

    @ModifyVariable(method = {"showSuggestions"}, at = @At("LOAD"), ordinal = 2)
    private int labyMod$modifyListPosition(int y) {
        return this.bottomY != -1 ? this.bottomY : y;
    }

    @Redirect(method = {"renderUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void labyMod$modifyBackgroundPosition(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
        graphics.fill(left, this.bottomY != -1 ? this.bottomY - BOTTOM_OFFSET : top, right, this.bottomY != -1 ? (this.bottomY - BOTTOM_OFFSET) + (bottom - top) : bottom, color);
    }

    @Redirect(method = {"renderUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;III)V"))
    private void labyMod$modifyTextPosition(GuiGraphics graphics, Font font, FormattedCharSequence text, int x, int y, int color) {
        graphics.drawString(font, text, x, this.bottomY != -1 ? (this.bottomY - BOTTOM_OFFSET) + 2 : y, color);
    }

    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }
}
