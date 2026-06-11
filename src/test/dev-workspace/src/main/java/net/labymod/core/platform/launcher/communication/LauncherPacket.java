package net.labymod.core.platform.launcher.communication;

import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/LauncherPacket.class */
public interface LauncherPacket {
    default void read(PayloadReader reader) {
        throw new UnsupportedOperationException("Write only packet!");
    }

    default void write(PayloadWriter writer) {
        throw new UnsupportedOperationException("Read only packet!");
    }

    default void handle(LauncherPacketHandler handler) {
    }
}
