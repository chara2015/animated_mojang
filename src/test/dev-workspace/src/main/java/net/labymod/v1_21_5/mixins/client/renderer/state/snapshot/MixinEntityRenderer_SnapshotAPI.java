package net.labymod.v1_21_5.mixins.client.renderer.state.snapshot;

import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.client.render.state.EntityRenderStateCreationEvent;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.render.state.entity.EntitySnapshotExtension;
import net.labymod.core.laby3d.renderer.snapshot.GameSnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/state/snapshot/MixinEntityRenderer_SnapshotAPI.class */
@Mixin({gxu.class})
public abstract class MixinEntityRenderer_SnapshotAPI {
    @Shadow
    public abstract int a(bxe bxeVar, float f);

    @Inject(method = {"createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;"}, at = {@At("RETURN")})
    private void labyMod$createSnapshot(bxe entity, float partialTicks, CallbackInfoReturnable<hec> cir, @Local hec renderState) {
        if (renderState instanceof GameSnapshot) {
            GameSnapshot gameSnapshot = (GameSnapshot) renderState;
            Extras.Builder builder = Extras.builder();
            Laby.fireEvent(new EntityRenderStateCreationEvent((Entity) CastUtil.cast(entity), partialTicks, builder));
            gameSnapshot.setExtras(builder.build());
        }
        if (renderState instanceof EntitySnapshotExtension) {
            EntitySnapshotExtension extension = (EntitySnapshotExtension) renderState;
            extension.labyMod$setLightCoords(a(entity, partialTicks));
        }
    }
}
