package net.labymod.v26_2_snapshot_8.mixins.server.packs.resources;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v26_2_snapshot_8.client.resources.pack.PackUtil;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/server/packs/resources/MixinResource.class */
@Mixin({Resource.class})
public class MixinResource {
    @WrapOperation(method = {"source"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;")})
    private PackResources labyMod$unwrapPackResources(Resource instance, Operation<PackResources> original) {
        return PackUtil.unwrap((PackResources) original.call(new Object[]{instance}));
    }
}
