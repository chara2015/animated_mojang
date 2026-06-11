package net.labymod.core.event.labymod;

import net.labymod.api.event.Event;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/labymod/PacketAddonMessageEvent.class */
public class PacketAddonMessageEvent implements Event {
    private final PacketAddonMessage packet;

    public PacketAddonMessageEvent(@NotNull PacketAddonMessage packet) {
        this.packet = packet;
    }

    @NotNull
    public PacketAddonMessage packet() {
        return this.packet;
    }
}
