package net.minecraft.network.protocol.common;

import net.minecraft.network.protocol.cookie.ClientCookiePacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ClientCommonPacketListener.class */
public interface ClientCommonPacketListener extends ClientCookiePacketListener {
    void handleKeepAlive(ClientboundKeepAlivePacket clientboundKeepAlivePacket);

    void handlePing(ClientboundPingPacket clientboundPingPacket);

    void handleCustomPayload(ClientboundCustomPayloadPacket clientboundCustomPayloadPacket);

    void handleDisconnect(ClientboundDisconnectPacket clientboundDisconnectPacket);

    void handleResourcePackPush(ClientboundResourcePackPushPacket clientboundResourcePackPushPacket);

    void handleResourcePackPop(ClientboundResourcePackPopPacket clientboundResourcePackPopPacket);

    void handleUpdateTags(ClientboundUpdateTagsPacket clientboundUpdateTagsPacket);

    void handleStoreCookie(ClientboundStoreCookiePacket clientboundStoreCookiePacket);

    void handleTransfer(ClientboundTransferPacket clientboundTransferPacket);

    void handleCustomReportDetails(ClientboundCustomReportDetailsPacket clientboundCustomReportDetailsPacket);

    void handleServerLinks(ClientboundServerLinksPacket clientboundServerLinksPacket);

    void handleClearDialog(ClientboundClearDialogPacket clientboundClearDialogPacket);

    void handleShowDialog(ClientboundShowDialogPacket clientboundShowDialogPacket);
}
