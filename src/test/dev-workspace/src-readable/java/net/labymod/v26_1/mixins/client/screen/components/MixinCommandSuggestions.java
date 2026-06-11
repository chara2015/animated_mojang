package net.labymod.v26_1.mixins.client.screen.components;

import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/screen/components/MixinCommandSuggestions.class */
@Mixin({CommandSuggestions.class})
public class MixinCommandSuggestions implements CommandSuggestionsAccessor {
    private static final int BOTTOM_OFFSET = 12;
    private int bottomY = -1;

    @ModifyVariable(method = {"showSuggestions"}, at = @At("LOAD"), ordinal = 2)
    private int labyMod$modifyListPosition(int y) {
        return this.bottomY != -1 ? this.bottomY : y;
    }

    @Redirect(method = {"extractUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"))
    private void labyMod$modifyBackgroundPosition(GuiGraphicsExtractor graphics, int left, int top, int right, int bottom, int color) {
        graphics.fill(left, this.bottomY != -1 ? this.bottomY - 12 : top, right, this.bottomY != -1 ? (this.bottomY - 12) + (bottom - top) : bottom, color);
    }

    @Redirect(method = {"extractUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;III)V"))
    private void labyMod$modifyTextPosition(GuiGraphicsExtractor graphics, Font font, FormattedCharSequence text, int x, int y, int color) {
        graphics.text(font, text, x, this.bottomY != -1 ? (this.bottomY - 12) + 2 : y, color);
    }

    @Override // net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor
    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }
}
