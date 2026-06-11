package net.labymod.v1_8_9.mixins.client.player;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/player/MixinPlayerInfo_SkinAvailable.class */
@Mixin(targets = {"net.minecraft.client.network.NetworkPlayerInfo$1"})
public abstract class MixinPlayerInfo_SkinAvailable {

    @Shadow
    @Final
    bdc a;

    @Inject(method = {"skinAvailable"}, at = {@At("RETURN")})
    public void skinAvailable(MinecraftProfileTexture.Type type, jy resourceLocation, MinecraftProfileTexture minecraftProfileTexture, CallbackInfo ci) {
        CompletableResourceLocation completable = this.a.labymod4$getPendingCompletables().get(type);
        if (completable != null) {
            completable.executeCompletableListeners((ResourceLocation) resourceLocation);
        }
    }
}
