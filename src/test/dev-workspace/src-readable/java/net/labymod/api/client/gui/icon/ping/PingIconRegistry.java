package net.labymod.api.client.gui.icon.ping;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/icon/ping/PingIconRegistry.class */
@Referenceable
public interface PingIconRegistry {
    PingIconRegistry render(ScreenContext screenContext, PingType pingType, int i, float f, float f2);

    Icon icon(PingType pingType, int i);

    default Icon icon(PingType type) {
        return icon(type, 0);
    }
}
