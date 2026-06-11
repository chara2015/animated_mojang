package net.labymod.v1_12_2.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_12_2.client.gfx.pipeline.renderer.OptiFineFontAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/compatibility/optifine/MixinOptiFineFontRenderer.class */
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({bip.class})
public class MixinOptiFineFontRenderer implements OptiFineFontAccessor {

    @Shadow
    private float[] charWidthFloat;

    @Shadow
    public float offsetBold;

    @Override // net.labymod.v1_12_2.client.gfx.pipeline.renderer.OptiFineFontAccessor
    public float[] optiFine$getCharWidths() {
        return this.charWidthFloat;
    }

    @Override // net.labymod.v1_12_2.client.gfx.pipeline.renderer.OptiFineFontAccessor
    public float optiFine$getBoldOffset() {
        return this.offsetBold;
    }
}
