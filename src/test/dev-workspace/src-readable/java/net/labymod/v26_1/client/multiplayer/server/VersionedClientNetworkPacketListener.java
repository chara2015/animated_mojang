package net.labymod.v26_1.client.multiplayer.server;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.models.Implements;
import net.labymod.api.user.serverfeature.ServerFeatureService;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.HeadRotationOldAnimation;
import net.minecraft.client.Minecraft;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/multiplayer/server/VersionedClientNetworkPacketListener.class */
@Singleton
@Implements(ClientNetworkPacketListener.class)
public class VersionedClientNetworkPacketListener extends ClientNetworkPacketListener {
    @Inject
    public VersionedClientNetworkPacketListener(ServerController serverController, OldAnimationRegistry oldAnimationRegistry, ServerFeatureService serverFeatureService) {
        super(serverController, oldAnimationRegistry, serverFeatureService);
    }

    @Override // net.labymod.core.client.multiplayer.ClientNetworkPacketListener
    public <T> T getCurrentServer() {
        return (T) Minecraft.getInstance().getCurrentServer();
    }

    @Override // net.labymod.core.client.multiplayer.ClientNetworkPacketListener
    public void onEntityRotate(Entity entity, float yaw, int lerpSteps) {
        if (!(entity instanceof net.minecraft.world.entity.Entity)) {
            return;
        }
        net.minecraft.world.entity.Entity mcEntity = (net.minecraft.world.entity.Entity) entity;
        HeadRotationOldAnimation animation = (HeadRotationOldAnimation) this.oldAnimationRegistry.get(HeadRotationOldAnimation.NAME);
        if (animation == null) {
            return;
        }
        if (animation.isEnabled()) {
            mcEntity.lerpHeadTo(yaw, animation.getOldHeadRotationPitchStep());
        } else {
            mcEntity.lerpHeadTo(yaw, lerpSteps);
        }
    }
}
