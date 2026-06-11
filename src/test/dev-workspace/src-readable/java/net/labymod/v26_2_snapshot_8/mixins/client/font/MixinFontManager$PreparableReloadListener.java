package net.labymod.v26_2_snapshot_8.mixins.client.font;

import net.labymod.api.Laby;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/font/MixinFontManager$PreparableReloadListener.class */
@Mixin({FontManager.class})
public class MixinFontManager$PreparableReloadListener {
    @Inject(method = {"apply"}, at = {@At("TAIL")})
    private void labyMod$onFontApply(FontManager.Preparation $$0, ProfilerFiller $$1, CallbackInfo ci) {
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
    }
}
