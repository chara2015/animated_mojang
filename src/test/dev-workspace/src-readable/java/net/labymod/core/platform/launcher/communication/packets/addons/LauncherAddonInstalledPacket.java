package net.labymod.core.platform.launcher.communication.packets.addons;

import java.util.Objects;
import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.core.platform.launcher.communication.LauncherPacketHandler;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/packets/addons/LauncherAddonInstalledPacket.class */
public class LauncherAddonInstalledPacket implements LauncherPacket {
    private String namespace;
    private String fileName;

    public LauncherAddonInstalledPacket() {
    }

    public LauncherAddonInstalledPacket(@NotNull String namespace, @NotNull String fileName) {
        Objects.requireNonNull(namespace, "namespace");
        Objects.requireNonNull(fileName, "fileName");
        this.namespace = namespace;
        this.fileName = fileName;
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void read(PayloadReader reader) {
        this.namespace = reader.readString();
        this.fileName = reader.readString();
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void write(PayloadWriter writer) {
        writer.writeString(this.namespace);
        writer.writeString(this.fileName);
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void handle(LauncherPacketHandler handler) {
        handler.handleAddonInstalledPacket(this);
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getFileName() {
        return this.fileName;
    }
}
