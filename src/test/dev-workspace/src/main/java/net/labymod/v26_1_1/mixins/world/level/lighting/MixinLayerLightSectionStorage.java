package net.labymod.v26_1_1.mixins.world.level.lighting;

import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.LightUpdateEvent;
import net.labymod.api.util.math.vector.IntVector3;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/world/level/lighting/MixinLayerLightSectionStorage.class */
@Mixin({LayerLightSectionStorage.class})
public class MixinLayerLightSectionStorage {

    @Shadow
    @Final
    private LightLayer layer;

    @Inject(method = {"setStoredLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/DataLayer;set(IIII)V", shift = At.Shift.AFTER)})
    private void labyMod$fireLightUpdateEvent(long levelPos, int lightLevel, CallbackInfo ci) {
        Laby.fireEvent(new LightUpdateEvent(Laby.references().lightingLayerMapper().fromMinecraft(this.layer), new IntVector3(BlockPos.getX(levelPos), BlockPos.getY(levelPos), BlockPos.getZ(levelPos))));
    }
}
