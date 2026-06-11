package net.labymod.v1_21_11.mixins.compatibility.optifine;

import net.labymod.api.client.entity.player.OptiFinePlayer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/compatibility/optifine/MixinOptiFineAbstractClientPlayer.class */
@DynamicMixin("optifine")
@Mixin({AbstractClientPlayer.class})
public abstract class MixinOptiFineAbstractClientPlayer implements OptiFinePlayer {

    @Shadow
    @Dynamic
    private Identifier locationOfCape;

    @Shadow
    public abstract void setReloadCapeTimeMs(long j);

    @Nullable
    public ResourceLocation getOptiFineCapeLocation() {
        ResourceLocation resourceLocation = this.locationOfCape;
        if (resourceLocation == null) {
            return null;
        }
        return resourceLocation;
    }

    public void bridge$optifine$setReloadCapeTime(long millis) {
        setReloadCapeTimeMs(millis);
    }
}
