package net.minecraft.network.protocol.game;

import com.mojang.logging.LogUtils;
import net.minecraft.ReportedException;
import net.minecraft.network.ServerboundPacketListener;
import net.minecraft.network.protocol.Packet;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/game/ServerPacketListener.class */
public interface ServerPacketListener extends ServerboundPacketListener {
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override // net.minecraft.network.PacketListener
    default void onPacketError(Packet $$0, Exception $$1) throws ReportedException {
        LOGGER.error("Failed to handle packet {}, suppressing error", $$0, $$1);
    }
}
