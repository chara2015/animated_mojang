package net.minecraft.network;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.PacketUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/PacketListener.class */
public interface PacketListener {
    PacketFlow flow();

    ConnectionProtocol protocol();

    void onDisconnect(DisconnectionDetails disconnectionDetails);

    boolean isAcceptingMessages();

    default void onPacketError(Packet $$0, Exception $$1) throws ReportedException {
        throw PacketUtils.makeReportedException($$1, $$0, this);
    }

    default DisconnectionDetails createDisconnectionInfo(Component $$0, Throwable $$1) {
        return new DisconnectionDetails($$0);
    }

    default boolean shouldHandleMessage(Packet<?> $$0) {
        return isAcceptingMessages();
    }

    default void fillCrashReport(CrashReport $$0) {
        CrashReportCategory $$1 = $$0.addCategory("Connection");
        $$1.setDetail("Protocol", () -> {
            return protocol().id();
        });
        $$1.setDetail("Flow", () -> {
            return flow().toString();
        });
        fillListenerSpecificCrashDetails($$0, $$1);
    }

    default void fillListenerSpecificCrashDetails(CrashReport $$0, CrashReportCategory $$1) {
    }
}
