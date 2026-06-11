package net.labymod.v1_19_4.mixins.client.screen.components;

import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/screen/components/MixinCommandSuggestions.class */
@Mixin({eof.class})
public class MixinCommandSuggestions implements CommandSuggestionsAccessor {
    private static final int BOTTOM_OFFSET = 12;
    private int bottomY = -1;

    @ModifyVariable(method = {"showSuggestions"}, at = @At("LOAD"), ordinal = 2)
    private int labyMod$modifyListPosition(int y) {
        return this.bottomY != -1 ? this.bottomY : y;
    }

    @Redirect(method = {"renderUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiComponent;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V"))
    private void labyMod$modifyBackgroundPosition(ehe stack, int left, int top, int right, int bottom, int color) {
        enr.a(stack, left, this.bottomY != -1 ? this.bottomY - 12 : top, right, this.bottomY != -1 ? (this.bottomY - 12) + (bottom - top) : bottom, color);
    }

    @Redirect(method = {"renderUsage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/util/FormattedCharSequence;FFI)I"))
    private int labyMod$modifyTextPosition(enp font, ehe stack, aov text, float x, float y, int color) {
        return font.a(stack, text, x, this.bottomY != -1 ? (this.bottomY - 12) + 2 : y, color);
    }

    @Override // net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor
    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }
}
