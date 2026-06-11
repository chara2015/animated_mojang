package net.labymod.core.main.listener;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.state.entity.EntitySnapshotRegistry;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.client.render.state.EntityRenderStateCreationEvent;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/GameRenderStateListener.class */
public class GameRenderStateListener {
    private final GameTransformations gameTransformations = Laby.references().gameTransformations();
    private final EntitySnapshotRegistry snapshotRegistry;

    public GameRenderStateListener(@NotNull EntitySnapshotRegistry snapshotRegistry) {
        this.snapshotRegistry = snapshotRegistry;
    }

    @Subscribe
    public void onServiceLoad(@NotNull ServiceLoadEvent event) {
        this.snapshotRegistry.populateRegistry(event.classLoader());
    }

    @Subscribe
    public void onEntityRenderStateCreation(@NotNull EntityRenderStateCreationEvent event) {
        Entity entity = event.entity();
        float partialTicks = event.partialTicks();
        ExtrasWriter writer = event.extrasWriter();
        this.snapshotRegistry.process(entity, partialTicks, writer);
    }

    @Subscribe(126)
    public void onCameraSetup(@NotNull CameraSetupEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.gameTransformations.viewMatrix().set(event.stack().getProvider().getPose());
    }
}
