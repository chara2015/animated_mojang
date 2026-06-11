package net.labymod.v26_1.mixins.client.gui.screens;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.api.Laby;
import net.labymod.core.localization.InternationalizationReloader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.resources.language.LanguageManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/gui/screens/MixinLanguageSelectScreen.class */
@Mixin({LanguageSelectScreen.class})
public class MixinLanguageSelectScreen {

    @Shadow
    @Final
    LanguageManager languageManager;

    @WrapWithCondition(method = {"onDone"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;")})
    private boolean labyMod$fasterLanguageReload(Minecraft instance) {
        boolean fastLanguageReload = Laby.labyAPI().config().other().fastLanguageReload().get().booleanValue();
        if (fastLanguageReload) {
            this.languageManager.onResourceManagerReload(instance.getResourceManager());
            InternationalizationReloader.reload();
        }
        return !fastLanguageReload;
    }
}
