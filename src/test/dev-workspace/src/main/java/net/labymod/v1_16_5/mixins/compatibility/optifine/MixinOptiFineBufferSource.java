package net.labymod.v1_16_5.mixins.compatibility.optifine;

import java.util.Map;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_16_5.client.gfx.buffer.BufferSourceAccessor;
import net.labymod.v1_16_5.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/compatibility/optifine/MixinOptiFineBufferSource.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({a.class})
public abstract class MixinOptiFineBufferSource implements BufferSourceAccessor {

    @Shadow
    protected eao lastState;

    @Shadow
    @Final
    protected Map<eao, dfh> b;

    @Shadow
    public abstract void a(eao eaoVar);

    @Override // net.labymod.v1_16_5.client.gfx.buffer.BufferSourceAccessor
    public void endLastBatch() {
        if (this.lastState == null) {
            return;
        }
        eao renderType = this.lastState;
        if (!this.b.containsKey(renderType)) {
            a(renderType);
        }
        this.lastState = null;
    }
}
