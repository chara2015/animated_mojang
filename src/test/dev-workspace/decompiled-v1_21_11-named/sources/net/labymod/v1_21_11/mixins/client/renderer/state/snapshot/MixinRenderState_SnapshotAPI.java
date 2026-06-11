package net.labymod.v1_21_11.mixins.client.renderer.state.snapshot;

import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.core.laby3d.renderer.snapshot.GameSnapshot;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.client.renderer.state.WeatherRenderState;
import net.minecraft.client.renderer.state.WorldBorderRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/state/snapshot/MixinRenderState_SnapshotAPI.class */
@Mixin({BlockEntityRenderState.class, BlockOutlineRenderState.class, CameraRenderState.class, EntityRenderState.class, ItemStackRenderState.class, ItemStackRenderState.LayerRenderState.class, LevelRenderState.class, MapRenderState.MapDecorationRenderState.class, MapRenderState.class, MovingBlockRenderState.class, SkyRenderState.class, WeatherRenderState.class, WorldBorderRenderState.class})
public class MixinRenderState_SnapshotAPI implements GameSnapshot {

    @Unique
    private Extras labyMod$extras = Extras.empty();

    public Extras extras() {
        return this.labyMod$extras;
    }

    public void setExtras(Extras extras) {
        this.labyMod$extras = extras;
    }
}
