package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiButtonLanguage.class */
@Mixin({bji.class})
public class MixinGuiButtonLanguage extends bja {
    public MixinGuiButtonLanguage(int id, int x, int y, String text) {
        super(id, x, y, text);
    }

    public void a(bib minecraft, int mouseX, int mouseY, float partialTicks) {
        super.a(minecraft, mouseX, mouseY, partialTicks);
    }
}
