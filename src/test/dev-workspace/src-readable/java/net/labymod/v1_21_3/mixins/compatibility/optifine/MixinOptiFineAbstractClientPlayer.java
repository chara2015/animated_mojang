package net.labymod.v1_21_3.mixins.compatibility.optifine;

import net.labymod.api.client.entity.player.OptiFinePlayer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/compatibility/optifine/MixinOptiFineAbstractClientPlayer.class */
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({gke.class})
public abstract class MixinOptiFineAbstractClientPlayer implements OptiFinePlayer {

    @Shadow
    @Dynamic
    private alz locationOfCape;

    @Shadow
    public abstract void setReloadCapeTimeMs(long j);

    @Override // net.labymod.api.client.entity.player.OptiFinePlayer
    @Nullable
    public ResourceLocation getOptiFineCapeLocation() {
        ResourceLocation resourceLocation = this.locationOfCape;
        if (resourceLocation == null) {
            return null;
        }
        return resourceLocation;
    }

    @Override // net.labymod.api.client.entity.player.OptiFinePlayer
    public void bridge$optifine$setReloadCapeTime(long millis) {
        setReloadCapeTimeMs(millis);
    }
}
