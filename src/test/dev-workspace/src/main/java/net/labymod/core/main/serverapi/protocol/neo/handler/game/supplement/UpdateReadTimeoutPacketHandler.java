package net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.sentry.Sentry;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.multiplayer.NettyConnection;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.UpdateReadTimeoutPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/supplement/UpdateReadTimeoutPacketHandler.class */
public class UpdateReadTimeoutPacketHandler implements PacketHandler<UpdateReadTimeoutPacket> {
    private static final String TIMEOUT_HANDLER_NAME = "timeout";
    private final Logging logger = DefaultLoggingFactory.createLogger((Class<?>) UpdateReadTimeoutPacketHandler.class);

    public void handle(@NotNull UUID uuid, @NotNull UpdateReadTimeoutPacket packet) {
        ClientPacketListener listener = Laby.labyAPI().minecraft().getClientPacketListener();
        if (listener == null) {
            return;
        }
        try {
            NettyConnection nettyConnection = (NettyConnection) listener.getNettyConnection();
            ChannelPipeline pipeline = nettyConnection.getChannel().pipeline();
            if (pipeline.get(TIMEOUT_HANDLER_NAME) != null) {
                nettyConnection.getChannel().eventLoop().execute(() -> {
                    pipeline.replace(TIMEOUT_HANDLER_NAME, TIMEOUT_HANDLER_NAME, new ReadTimeoutHandler(packet.getSeconds()));
                });
            }
        } catch (Throwable e) {
            this.logger.error("Could not update read timeout", e);
            Sentry.captureException(e);
        }
    }
}
