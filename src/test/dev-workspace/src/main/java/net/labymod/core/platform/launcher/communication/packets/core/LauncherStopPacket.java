package net.labymod.core.platform.launcher.communication.packets.core;

import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.core.platform.launcher.communication.LauncherPacketHandler;
import net.labymod.serverapi.api.payload.io.PayloadReader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/packets/core/LauncherStopPacket.class */
public class LauncherStopPacket implements LauncherPacket {
    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void read(PayloadReader reader) {
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void handle(LauncherPacketHandler handler) {
        handler.handleStopPacket(this);
    }
}
