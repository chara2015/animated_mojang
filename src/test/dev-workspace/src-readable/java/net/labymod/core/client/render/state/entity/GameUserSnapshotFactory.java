package net.labymod.core.client.render.state.entity;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshotFactory;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.serverfeature.ServerFeature;
import net.labymod.api.user.serverfeature.ServerFeatureService;
import net.labymod.core.main.user.serverfeature.DefaultServerFeature;
import net.labymod.core.main.user.serverfeature.UserServerFeature;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/GameUserSnapshotFactory.class */
@AutoService(LabySnapshotFactory.class)
public final class GameUserSnapshotFactory extends LabySnapshotFactory<GameUser, GameUserSnapshot> {
    private final LabyAPI labyAPI;
    private final ServerFeatureService serverFeatureService;

    public GameUserSnapshotFactory(LabyAPI labyAPI) {
        super(EntityExtraKeys.GAME_USER);
        this.labyAPI = labyAPI;
        this.serverFeatureService = Laby.references().serverFeatureService();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.laby3d.renderer.snapshot.LabySnapshotFactory
    public void writeExtras(GameUser user, ExtrasWriter writer) {
        super.writeExtras(user, writer);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.laby3d.renderer.snapshot.LabySnapshotFactory
    public GameUserSnapshot create(GameUser user, Extras extras) {
        ServerFeature serverFeature = this.serverFeatureService.serverFeature();
        UserServerFeature userServerFeature = ((DefaultServerFeature) serverFeature).getUserFeature(user.getUniqueId());
        return new DefaultGameUserSnapshot(user, userServerFeature, extras, this.labyAPI.config());
    }
}
