package net.labymod.v1_20_4.mixins.server.packs.resources;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_20_4.client.resources.pack.PackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/server/packs/resources/MixinResource.class */
@Mixin({aqg.class})
public class MixinResource {
    @WrapOperation(method = {"source"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;")})
    private aow labyMod$unwrapPackResources(aqg instance, Operation<aow> original) {
        return PackUtil.unwrap((aow) original.call(new Object[]{instance}));
    }
}
