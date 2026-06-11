package net.labymod.v1_20_1.mixins.server.packs.resources;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_20_1.client.resources.pack.PackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/server/packs/resources/MixinResource.class */
@Mixin({akv.class})
public class MixinResource {
    @WrapOperation(method = {"source"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;")})
    private ajl labyMod$unwrapPackResources(akv instance, Operation<ajl> original) {
        return PackUtil.unwrap((ajl) original.call(new Object[]{instance}));
    }
}
