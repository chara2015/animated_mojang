package net.labymod.v1_19_4.client.multiplayer.server;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.models.Implements;
import net.labymod.api.user.serverfeature.ServerFeatureService;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.HeadRotationOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/multiplayer/server/VersionedClientNetworkPacketListener.class */
@Singleton
@Implements(ClientNetworkPacketListener.class)
public class VersionedClientNetworkPacketListener extends ClientNetworkPacketListener {
    @Inject
    public VersionedClientNetworkPacketListener(ServerController serverController, OldAnimationRegistry oldAnimationRegistry, ServerFeatureService serverFeatureService) {
        super(serverController, oldAnimationRegistry, serverFeatureService);
    }

    @Override // net.labymod.core.client.multiplayer.ClientNetworkPacketListener
    public <T> T getCurrentServer() {
        return (T) emh.N().P();
    }

    @Override // net.labymod.core.client.multiplayer.ClientNetworkPacketListener
    public void onEntityRotate(Entity entity, float yaw, int lerpSteps) {
        if (!(entity instanceof bfh)) {
            return;
        }
        bfh mcEntity = (bfh) entity;
        HeadRotationOldAnimation animation = (HeadRotationOldAnimation) this.oldAnimationRegistry.get(HeadRotationOldAnimation.NAME);
        if (animation == null) {
            return;
        }
        if (animation.isEnabled()) {
            mcEntity.a(yaw, animation.getOldHeadRotationPitchStep());
        } else {
            mcEntity.a(yaw, lerpSteps);
        }
    }
}
