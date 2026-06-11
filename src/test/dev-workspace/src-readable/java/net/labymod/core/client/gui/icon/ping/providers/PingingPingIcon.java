package net.labymod.core.client.gui.icon.ping.providers;

import net.labymod.api.Namespaces;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.icon.ping.PingIcon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/icon/ping/providers/PingingPingIcon.class */
public class PingingPingIcon extends PingIcon {
    private static final ResourceLocation PINGING_1_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/pinging_1");
    private static final ResourceLocation PINGING_2_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/pinging_2");
    private static final ResourceLocation PINGING_3_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/pinging_3");
    private static final ResourceLocation PINGING_4_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/pinging_4");
    private static final ResourceLocation PINGING_5_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "server_list/pinging_5");

    @Override // net.labymod.core.client.gui.icon.ping.PingIcon
    public Icon get(int index) {
        return getIcon(getPiningSprite(index));
    }

    private ResourceLocation getPiningSprite(int index) {
        switch (index) {
            case 2:
                return PINGING_2_SPRITE;
            case 3:
                return PINGING_3_SPRITE;
            case 4:
                return PINGING_4_SPRITE;
            case 5:
                return PINGING_5_SPRITE;
            default:
                return PINGING_1_SPRITE;
        }
    }
}
