package net.labymod.v1_21_10.mixins.client.gui.screens;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.api.Laby;
import net.labymod.core.localization.InternationalizationReloader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/gui/screens/MixinLanguageSelectScreen.class */
@Mixin({gqe.class})
public class MixinLanguageSelectScreen {

    @Shadow
    @Final
    idv u;

    @WrapWithCondition(method = {"onDone"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;")})
    private boolean labyMod$fasterLanguageReload(fzz instance) {
        boolean fastLanguageReload = Laby.labyAPI().config().other().fastLanguageReload().get().booleanValue();
        if (fastLanguageReload) {
            this.u.a(instance.ai());
            InternationalizationReloader.reload();
        }
        return !fastLanguageReload;
    }
}
