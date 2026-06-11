package net.labymod.v1_19_4.mixins.client.gui.screens;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.api.Laby;
import net.labymod.core.localization.InternationalizationReloader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/screens/MixinLanguageSelectScreen.class */
@Mixin({esn.class})
public class MixinLanguageSelectScreen {

    @Shadow
    @Final
    fui p;

    @WrapWithCondition(method = {"lambda$init$0"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;")})
    private boolean labyMod$fasterLanguageReload(emh instance) {
        boolean fastLanguageReload = Laby.labyAPI().config().other().fastLanguageReload().get().booleanValue();
        if (fastLanguageReload) {
            this.p.a(instance.Y());
            InternationalizationReloader.reload();
        }
        return !fastLanguageReload;
    }
}
