package net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.playerlist.ServerBannerEvent;
import net.labymod.api.uri.URIParser;
import net.labymod.api.util.HashUtil;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListBannerPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/display/tablist/TabListBannerPacketHandler.class */
public class TabListBannerPacketHandler implements PacketHandler<TabListBannerPacket> {
    public void handle(@NotNull UUID uuid, @NotNull TabListBannerPacket packet) {
        String iconUrl = packet.getIconUrl();
        if (!URIParser.isHttpUrl(iconUrl)) {
            return;
        }
        Laby.fireEvent(new ServerBannerEvent(iconUrl, HashUtil.md5Hex(iconUrl.getBytes(StandardCharsets.UTF_8))));
    }
}
