package net.labymod.v1_12_2.mixins.client.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.api.Laby;
import net.labymod.core.localization.InternationalizationReloader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiLanguage.class */
@Mixin(targets = {"net.minecraft.client.gui.GuiLanguage$List"})
public class MixinGuiLanguage {
    @WrapWithCondition(method = {"elementClicked"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;refreshResources()V")})
    private boolean labyMod$fasterLanguageReload(bib minecraft) {
        boolean fastLanguageReload = Laby.labyAPI().config().other().fastLanguageReload().get().booleanValue();
        if (fastLanguageReload) {
            minecraft.Q().a(minecraft.O());
            InternationalizationReloader.reload();
        }
        return !fastLanguageReload;
    }
}
