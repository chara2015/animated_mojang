package net.labymod.v1_21_10.mixins.server.packs.resources;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_10.client.resources.pack.PackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/server/packs/resources/MixinResource.class */
@Mixin({ban.class})
public class MixinResource {
    @WrapOperation(method = {"source"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;")})
    private azb labyMod$unwrapPackResources(ban instance, Operation<azb> original) {
        return PackUtil.unwrap((azb) original.call(new Object[]{instance}));
    }
}
