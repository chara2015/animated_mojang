package net.labymod.v26_2_snapshot_8.mixins.client.renderer.feature;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/feature/MixinFeatureRenderDispatcher.class */
@Mixin({FeatureRenderDispatcher.class})
public class MixinFeatureRenderDispatcher {
    @Inject(method = {"renderAllFeatures"}, at = {@At("HEAD")})
    private void labyMod$beginEntityPass(CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }

    @Inject(method = {"renderAllFeatures"}, at = {@At("TAIL")})
    private void labyMod$endEntityPass(CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }
}
