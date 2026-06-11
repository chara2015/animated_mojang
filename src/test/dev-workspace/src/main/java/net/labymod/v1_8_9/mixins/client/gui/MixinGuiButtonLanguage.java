package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiButtonLanguage.class */
@Mixin({avz.class})
public class MixinGuiButtonLanguage extends avs {
    public MixinGuiButtonLanguage(int id, int x, int y, String text) {
        super(id, x, y, text);
    }

    public void a(ave minecraft, int mouseX, int mouseY) {
        super.a(minecraft, mouseX, mouseY);
    }
}
