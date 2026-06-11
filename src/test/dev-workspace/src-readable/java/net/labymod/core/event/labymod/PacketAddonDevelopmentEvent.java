package net.labymod.core.event.labymod;

import net.labymod.api.event.Event;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/labymod/PacketAddonDevelopmentEvent.class */
public class PacketAddonDevelopmentEvent implements Event {
    private final PacketAddonDevelopment packet;

    public PacketAddonDevelopmentEvent(@NotNull PacketAddonDevelopment packet) {
        this.packet = packet;
    }

    @NotNull
    public PacketAddonDevelopment packet() {
        return this.packet;
    }
}
