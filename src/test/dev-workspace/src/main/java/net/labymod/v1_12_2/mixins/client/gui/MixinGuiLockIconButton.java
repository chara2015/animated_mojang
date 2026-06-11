package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiLockIconButton.class */
@Mixin({bjl.class})
public class MixinGuiLockIconButton extends bja {
    public MixinGuiLockIconButton(int p_i195_1_, int p_i195_2_, int p_i195_3_, String p_i195_4_) {
        super(p_i195_1_, p_i195_2_, p_i195_3_, p_i195_4_);
    }

    public MixinGuiLockIconButton(int p_i196_1_, int p_i196_2_, int p_i196_3_, int p_i196_4_, int p_i196_5_, String p_i196_6_) {
        super(p_i196_1_, p_i196_2_, p_i196_3_, p_i196_4_, p_i196_5_, p_i196_6_);
    }

    public void a(bib minecraft, int mouseX, int mouseY, float partialTicks) {
        super.a(minecraft, mouseX, mouseY, partialTicks);
    }
}
