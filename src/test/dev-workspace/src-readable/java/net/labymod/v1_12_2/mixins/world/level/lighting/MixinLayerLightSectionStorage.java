package net.labymod.v1_12_2.mixins.world.level.lighting;

import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.LightUpdateEvent;
import net.labymod.api.util.math.vector.IntVector3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/world/level/lighting/MixinLayerLightSectionStorage.class */
@Mixin({axw.class})
public class MixinLayerLightSectionStorage {
    @Inject(method = {"setLightFor"}, at = {@At("TAIL")})
    private void labyMod$fireLightUpdateEvent(ana layer, et blockPos, int lightLevel, CallbackInfo ci) {
        Laby.fireEvent(new LightUpdateEvent(Laby.references().lightingLayerMapper().fromMinecraft(layer), new IntVector3(blockPos.p(), blockPos.q(), blockPos.r())));
    }
}
