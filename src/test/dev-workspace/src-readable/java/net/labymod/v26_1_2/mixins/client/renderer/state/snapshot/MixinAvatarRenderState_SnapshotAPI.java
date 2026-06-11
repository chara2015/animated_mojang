package net.labymod.v26_1_2.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.AvatarSnapshot;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/state/snapshot/MixinAvatarRenderState_SnapshotAPI.class */
@Mixin({AvatarRenderState.class})
public abstract class MixinAvatarRenderState_SnapshotAPI extends MixinLivingRenderState_SnapshotAPI implements AvatarSnapshot {
}
