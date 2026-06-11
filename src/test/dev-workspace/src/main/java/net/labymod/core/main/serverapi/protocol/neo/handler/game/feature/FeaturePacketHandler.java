package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.serverapi.ServerFeatureUpdateEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.serverfeature.DefaultServerFeature;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.feature.Feature;
import net.labymod.serverapi.core.packet.clientbound.game.feature.UpdateFeaturePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/FeaturePacketHandler.class */
public class FeaturePacketHandler implements PacketHandler<UpdateFeaturePacket> {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) LabyMod.references().serverFeatureService();

    public void handle(@NotNull UUID uuid, @NotNull UpdateFeaturePacket updateFeaturePacket) {
        DefaultServerFeature serverFeature = this.serverFeatureService.get();
        Map<Feature, Feature.StatedFeature> updated = new HashMap<>();
        for (Feature.StatedFeature feature : updateFeaturePacket.getFeatures()) {
            serverFeature.setFeature(feature.feature(), feature);
            updated.put(feature.feature(), feature);
        }
        Laby.labyAPI().eventBus().fire(new ServerFeatureUpdateEvent(Collections.unmodifiableMap(updated)));
    }
}
