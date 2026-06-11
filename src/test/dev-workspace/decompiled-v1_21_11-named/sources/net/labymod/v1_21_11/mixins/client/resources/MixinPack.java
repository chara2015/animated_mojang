package net.labymod.v1_21_11.mixins.client.resources;

import net.labymod.v1_21_11.client.resources.pack.WrappedPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/resources/MixinPack.class */
@Mixin({Pack.class})
public class MixinPack {
    @Inject(method = {"open"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$wrapPackResources(CallbackInfoReturnable<PackResources> cir) {
        cir.setReturnValue(new WrappedPackResources((PackResources) cir.getReturnValue()));
    }
}
