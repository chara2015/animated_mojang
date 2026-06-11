package net.labymod.v1_21_10.mixins.client;

import net.labymod.api.client.world.WorldRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/MixinLevelRenderer.class */
@Mixin({hfq.class})
public class MixinLevelRenderer implements WorldRenderer {

    @Shadow
    @Final
    private ibp Q;

    @Override // net.labymod.api.client.world.WorldRenderer
    public int getRenderedEntities() {
        return this.Q.b.size();
    }
}
