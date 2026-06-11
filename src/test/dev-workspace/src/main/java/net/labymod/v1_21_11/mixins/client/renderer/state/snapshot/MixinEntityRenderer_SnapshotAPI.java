package net.labymod.v1_21_11.mixins.client.renderer.state.snapshot;

import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.client.render.state.EntityRenderStateCreationEvent;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.util.CastUtil;
import net.labymod.core.laby3d.renderer.snapshot.GameSnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/state/snapshot/MixinEntityRenderer_SnapshotAPI.class */
@Mixin({hwp.class})
public class MixinEntityRenderer_SnapshotAPI {
    @Inject(method = {"createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;"}, at = {@At("RETURN")})
    private void labyMod$createSnapshot(cgk entity, float partialTicks, CallbackInfoReturnable<idf> cir, @Local idf renderState) {
        if (!(renderState instanceof GameSnapshot)) {
            return;
        }
        GameSnapshot gameSnapshot = (GameSnapshot) renderState;
        Extras.Builder builder = Extras.builder();
        Laby.fireEvent(new EntityRenderStateCreationEvent((Entity) CastUtil.cast(entity), partialTicks, builder));
        gameSnapshot.setExtras(builder.build());
    }
}
