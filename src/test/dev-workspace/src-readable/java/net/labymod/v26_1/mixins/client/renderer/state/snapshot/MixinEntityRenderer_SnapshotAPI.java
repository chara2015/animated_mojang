package net.labymod.v26_1.mixins.client.renderer.state.snapshot;

import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.state.EntityRenderStateCreationEvent;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.util.CastUtil;
import net.labymod.core.laby3d.renderer.snapshot.GameSnapshot;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/renderer/state/snapshot/MixinEntityRenderer_SnapshotAPI.class */
@Mixin({EntityRenderer.class})
public class MixinEntityRenderer_SnapshotAPI {
    @Inject(method = {"createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;"}, at = {@At("RETURN")})
    private void labyMod$createSnapshot(Entity entity, float partialTicks, CallbackInfoReturnable<EntityRenderState> cir, @Local EntityRenderState renderState) {
        if (!(renderState instanceof GameSnapshot)) {
            return;
        }
        GameSnapshot gameSnapshot = (GameSnapshot) renderState;
        Extras.Builder builder = Extras.builder();
        Laby.fireEvent(new EntityRenderStateCreationEvent((net.labymod.api.client.entity.Entity) CastUtil.cast(entity), partialTicks, builder));
        gameSnapshot.setExtras(builder.build());
    }
}
