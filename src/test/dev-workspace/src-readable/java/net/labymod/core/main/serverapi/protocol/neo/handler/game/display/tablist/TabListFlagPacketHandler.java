package net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.serverfeature.DefaultServerFeature;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.core.main.user.serverfeature.UserServerFeature;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.display.TabListFlag;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListFlagPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/display/tablist/TabListFlagPacketHandler.class */
public class TabListFlagPacketHandler implements PacketHandler<TabListFlagPacket> {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    public void handle(@NotNull UUID uuid, @NotNull TabListFlagPacket packet) {
        DefaultServerFeature serverFeature = this.serverFeatureService.get();
        for (TabListFlag flag : packet.getFlags()) {
            UUID target = flag.getUniqueId();
            UserServerFeature user = serverFeature.getOrCreateUserFeature(target);
            CountryCode code = CountryCode.fromCode(flag.getCountryCode().name());
            user.setCountryCode(code);
        }
    }
}
