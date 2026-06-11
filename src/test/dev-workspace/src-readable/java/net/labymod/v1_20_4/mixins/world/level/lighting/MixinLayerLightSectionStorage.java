package net.labymod.v1_20_4.mixins.world.level.lighting;

import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.LightUpdateEvent;
import net.labymod.api.util.math.vector.IntVector3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/world/level/lighting/MixinLayerLightSectionStorage.class */
@Mixin({eef.class})
public class MixinLayerLightSectionStorage {

    @Shadow
    @Final
    private cty i;

    @Inject(method = {"setStoredLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/DataLayer;set(IIII)V", shift = At.Shift.AFTER)})
    private void labyMod$fireLightUpdateEvent(long levelPos, int lightLevel, CallbackInfo ci) {
        Laby.fireEvent(new LightUpdateEvent(Laby.references().lightingLayerMapper().fromMinecraft(this.i), new IntVector3(hx.a(levelPos), hx.b(levelPos), hx.c(levelPos))));
    }
}
