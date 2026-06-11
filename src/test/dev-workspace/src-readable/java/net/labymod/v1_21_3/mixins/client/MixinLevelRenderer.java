package net.labymod.v1_21_3.mixins.client;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/MixinLevelRenderer.class */
@Mixin({glh.class})
public class MixinLevelRenderer implements WorldRenderer {

    @Shadow
    private int L;

    @Inject(method = {"renderEntities"}, at = {@At("HEAD")})
    private void labyMod$beforeEntityRenderPass(fgs $$0, a $$1, flp $$2, flw $$3, List<bvk> $$4, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }

    @Inject(method = {"renderEntities"}, at = {@At("TAIL")})
    private void labyMod$afterEntityRenderPass(fgs $$0, a $$1, flp $$2, flw $$3, List<bvk> $$4, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }

    @Override // net.labymod.api.client.world.WorldRenderer
    public int getRenderedEntities() {
        return this.L;
    }
}
