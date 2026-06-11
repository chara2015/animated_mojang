package net.labymod.core.platform.launcher.communication.packets.core;

import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.serverapi.api.payload.io.PayloadWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/packets/core/LauncherIdentificationPacket.class */
public class LauncherIdentificationPacket implements LauncherPacket {
    private final String identifier;

    public LauncherIdentificationPacket(String identifier) {
        this.identifier = identifier;
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void write(PayloadWriter writer) {
        writer.writeString(this.identifier);
    }
}
