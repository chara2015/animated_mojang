package net.labymod.v1_21_10.mixins.client.renderer.state.snapshot;

import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.core.laby3d.renderer.snapshot.GameSnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/state/snapshot/MixinRenderState_SnapshotAPI.class */
@Mixin({hjw.class, ibn.class, ibo.class, huk.class, hys.class, b.class, ibp.class, a.class, ibq.class, hhj.class, ibu.class, ibv.class, ibw.class})
public class MixinRenderState_SnapshotAPI implements GameSnapshot {

    @Unique
    private Extras labyMod$extras = Extras.empty();

    @Override // net.labymod.api.laby3d.renderer.snapshot.LabySnapshot
    public Extras extras() {
        return this.labyMod$extras;
    }

    @Override // net.labymod.core.laby3d.renderer.snapshot.GameSnapshot
    public void setExtras(Extras extras) {
        this.labyMod$extras = extras;
    }
}
