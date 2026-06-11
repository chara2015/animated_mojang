package net.labymod.v1_16_5.mixins.client.renderer.skipcolors;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/skipcolors/MixinModelPartSkipColors.class */
@Mixin({dwn.class})
public class MixinModelPartSkipColors {
    @ModifyVariable(method = {"compile"}, at = @At("HEAD"), argsOnly = true, index = 8)
    private float labyMod$modifyAlpha(float alpha) {
        return GlColorAlphaModifier.isModifiedAlpha() ? GlColorAlphaModifier.getAlpha() : alpha;
    }
}
