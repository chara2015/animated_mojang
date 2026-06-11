package net.labymod.core.client.gui.icon.ping;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.icon.ping.PingIconRegistry;
import net.labymod.api.client.gui.icon.ping.PingType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.icon.ping.providers.GenericPingIcon;
import net.labymod.core.client.gui.icon.ping.providers.IncompatiblePingIcon;
import net.labymod.core.client.gui.icon.ping.providers.PingingPingIcon;
import net.labymod.core.client.gui.icon.ping.providers.ServerListPingIcon;
import net.labymod.core.util.ArrayIndex;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/icon/ping/DefaultPingIconRegistry.class */
@Singleton
@Implements(PingIconRegistry.class)
public class DefaultPingIconRegistry implements PingIconRegistry {
    private final ArrayIndex<PingIcon> pingIcons = new ArrayIndex<>(PingType.VALUES.length, x$0 -> {
        return new PingIcon[x$0];
    });

    @Inject
    public DefaultPingIconRegistry() {
        registerPingIcon(PingType.PLAYER_PING, new GenericPingIcon());
        registerPingIcon(PingType.SERVER_PING, new ServerListPingIcon());
        registerPingIcon(PingType.LOADING_PING, new PingingPingIcon());
        registerPingIcon(PingType.ERROR, new IncompatiblePingIcon());
    }

    private void registerPingIcon(PingType type, PingIcon icon) {
        this.pingIcons.set(type.ordinal(), icon);
    }

    @Override // net.labymod.api.client.gui.icon.ping.PingIconRegistry
    public PingIconRegistry render(ScreenContext context, PingType type, int value, float x, float y) {
        Icon icon = icon(type, value);
        context.canvas().submitIcon(icon, x, y, 10.0f, 8.0f);
        return this;
    }

    @Override // net.labymod.api.client.gui.icon.ping.PingIconRegistry
    public Icon icon(PingType type, int value) {
        return this.pingIcons.get(type.ordinal()).get(value);
    }
}
