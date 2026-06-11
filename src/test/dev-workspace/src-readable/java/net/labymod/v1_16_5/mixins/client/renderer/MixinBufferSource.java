package net.labymod.v1_16_5.mixins.client.renderer;

import java.util.Map;
import java.util.Optional;
import net.labymod.v1_16_5.client.gfx.buffer.BufferSourceAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/MixinBufferSource.class */
@Mixin({a.class})
public abstract class MixinBufferSource implements BufferSourceAccessor {

    @Shadow
    protected Optional<eao> c;

    @Shadow
    @Final
    protected Map<eao, dfh> b;

    @Shadow
    public abstract void a(eao eaoVar);

    @Override // net.labymod.v1_16_5.client.gfx.buffer.BufferSourceAccessor
    public void endLastBatch() {
        if (!this.c.isPresent()) {
            return;
        }
        eao renderType = this.c.get();
        if (!this.b.containsKey(renderType)) {
            a(renderType);
        }
        this.c = Optional.empty();
    }
}
