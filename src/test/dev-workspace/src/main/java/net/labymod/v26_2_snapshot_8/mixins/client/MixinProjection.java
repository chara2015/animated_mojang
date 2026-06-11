package net.labymod.v26_2_snapshot_8.mixins.client;

import com.mojang.blaze3d.ProjectionType;
import net.labymod.v26_2_snapshot_8.laby3d.ProjectionAccessor;
import net.minecraft.client.renderer.Projection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/MixinProjection.class */
@Mixin({Projection.class})
public class MixinProjection implements ProjectionAccessor {

    @Shadow
    private ProjectionType projectionType;

    @Override // net.labymod.v26_2_snapshot_8.laby3d.ProjectionAccessor
    public ProjectionType labyMod$getProjectionType() {
        return this.projectionType;
    }
}
