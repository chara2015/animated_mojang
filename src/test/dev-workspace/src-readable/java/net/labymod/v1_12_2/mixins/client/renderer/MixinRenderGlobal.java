package net.labymod.v1_12_2.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/MixinRenderGlobal.class */
@Mixin({buy.class})
public class MixinRenderGlobal implements WorldRenderer {

    @Shadow
    private int S;

    @Override // net.labymod.api.client.world.WorldRenderer
    public int getRenderedEntities() {
        return this.S;
    }

    @Inject(method = {"renderEntities"}, at = {@At("HEAD")})
    private void labyMod$fireEntityRenderPassEventBefore(vg p_renderEntities_1_, bxy p_renderEntities_2_, float p_renderEntities_3_, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }

    @Inject(method = {"renderEntities"}, at = {@At("TAIL")})
    private void labyMod$fireEntityRenderPassEventAfter(vg p_renderEntities_1_, bxy p_renderEntities_2_, float p_renderEntities_3_, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }
}
