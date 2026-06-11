package net.labymod.v1_18_2.mixins.client.font;

import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/font/MixinFontManager$PreparableReloadListener.class */
@Mixin(targets = {"net.minecraft.client.gui.font.FontManager$1"})
public class MixinFontManager$PreparableReloadListener {
    @Inject(method = {"apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V"}, at = {@At("TAIL")})
    private void labyMod$onFontApply(Map<yt, List<drr>> param0, aga param1, asy param2, CallbackInfo ci) {
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
    }
}
