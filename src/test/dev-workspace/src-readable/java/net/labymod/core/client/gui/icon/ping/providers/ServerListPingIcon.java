package net.labymod.core.client.gui.icon.ping.providers;

import net.labymod.api.Namespaces;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.icon.ping.PingIcon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/icon/ping/providers/ServerListPingIcon.class */
public class ServerListPingIcon extends PingIcon {
    private static final ResourceLocation UNREACHABLE_PING = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/unreachable");
    private static final ResourceLocation PING_1 = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/ping_1");
    private static final ResourceLocation PING_2 = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/ping_2");
    private static final ResourceLocation PING_3 = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/ping_3");
    private static final ResourceLocation PING_4 = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/ping_4");
    private static final ResourceLocation PING_5 = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/ping_5");

    @Override // net.labymod.core.client.gui.icon.ping.PingIcon
    public Icon get(int milliseconds) {
        return getIcon(getSprite(milliseconds));
    }

    private ResourceLocation getSprite(int milliseconds) {
        if (milliseconds < 0) {
            return UNREACHABLE_PING;
        }
        if (milliseconds < 150) {
            return PING_5;
        }
        if (milliseconds < 300) {
            return PING_4;
        }
        if (milliseconds < 600) {
            return PING_3;
        }
        if (milliseconds < 1000) {
            return PING_2;
        }
        return PING_1;
    }
}
