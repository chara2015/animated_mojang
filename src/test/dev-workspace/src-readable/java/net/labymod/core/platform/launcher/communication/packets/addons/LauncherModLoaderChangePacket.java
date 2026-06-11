package net.labymod.core.platform.launcher.communication.packets.addons;

import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.serverapi.api.payload.io.PayloadWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/packets/addons/LauncherModLoaderChangePacket.class */
public class LauncherModLoaderChangePacket implements LauncherPacket {
    private final String modLoader;

    public LauncherModLoaderChangePacket(String modLoader) {
        this.modLoader = modLoader;
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void write(PayloadWriter writer) {
        writer.writeBoolean(this.modLoader != null);
        if (this.modLoader != null) {
            writer.writeString(this.modLoader);
        }
    }
}
