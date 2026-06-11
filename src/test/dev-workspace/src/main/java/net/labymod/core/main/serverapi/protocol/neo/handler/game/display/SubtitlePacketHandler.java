package net.labymod.core.main.serverapi.protocol.neo.handler.game.display;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.core.main.user.serverfeature.DefaultServerFeature;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.core.main.user.serverfeature.UserServerFeature;
import net.labymod.core.main.user.serverfeature.subtitle.SubtitleComponent;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.display.Subtitle;
import net.labymod.serverapi.core.packet.clientbound.game.display.SubtitlePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/display/SubtitlePacketHandler.class */
public class SubtitlePacketHandler implements PacketHandler<SubtitlePacket> {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    public void handle(@NotNull UUID uuid, @NotNull SubtitlePacket packet) {
        DefaultServerFeature serverFeature = this.serverFeatureService.get();
        for (Subtitle subtitle : packet.getSubtitles()) {
            UserServerFeature feature = serverFeature.getOrCreateUserFeature(subtitle.getUniqueId());
            if (subtitle.getText() == null) {
                feature.setSubtitle(null);
            } else {
                feature.setSubtitle(new SubtitleComponent(subtitle));
            }
        }
    }
}
