package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.util.color.format.ColorFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiAchievements.class */
@Mixin({aye.class})
public class MixinGuiAchievements extends axu {
    @Inject(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/achievement/GuiAchievements;drawDefaultBackground()V", shift = At.Shift.AFTER)})
    private void labyMod$drawScreen(int lvt_1_1_, int lvt_2_1_, float lvt_3_1_, CallbackInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get().booleanValue()) {
            bfl.f();
            bfl.n();
            bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
            avp.a(0, 0, this.l, this.m, ColorFormat.ARGB32.pack(0.0f, 0.0f, 0.0f, 0.105f));
        }
    }
}
