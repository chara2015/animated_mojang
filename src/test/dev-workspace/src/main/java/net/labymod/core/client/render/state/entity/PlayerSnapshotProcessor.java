package net.labymod.core.client.render.state.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.EntitySnapshotProcessor;
import net.labymod.api.client.render.state.entity.EntitySnapshotRegistry;
import net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/PlayerSnapshotProcessor.class */
@AutoService(EntitySnapshotProcessor.class)
public class PlayerSnapshotProcessor extends EntitySnapshotProcessor<Player> {
    public PlayerSnapshotProcessor(EntitySnapshotRegistry registry) {
        super(registry);
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshotProcessor
    public boolean supports(Entity entity) {
        return entity instanceof Player;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshotProcessor
    public void process(Player player, float partialTicks, ExtrasWriter entityWriter) {
        EntitySnapshotRegistry registry = registry();
        registry.captureSnapshot(entityWriter, EntityExtraKeys.GAME_USER, player.gameUser());
        registry.captureSnapshot(entityWriter, EntityExtraKeys.CUSTOM_AVATAR_DATA, player);
    }
}
