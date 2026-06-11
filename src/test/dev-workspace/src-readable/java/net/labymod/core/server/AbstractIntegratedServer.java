package net.labymod.core.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.Objects;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.server.IntegratedServer;
import net.labymod.core.client.worldsharing.model.GameDifficulty;
import net.labymod.core.client.worldsharing.model.GameMode;
import net.labymod.core.client.worldsharing.model.PlayerConsumer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/server/AbstractIntegratedServer.class */
public abstract class AbstractIntegratedServer implements IntegratedServer {
    public abstract int getSuitableLanPort();

    public abstract boolean publishLanWorld(int i, GameMode gameMode, boolean z);

    public abstract boolean isPublished();

    public abstract String getWorldName();

    public abstract void changeGameMode(GameMode gameMode);

    public abstract GameMode getGameMode();

    public abstract void changeDifficulty(GameDifficulty gameDifficulty);

    public abstract GameDifficulty getDifficulty();

    public abstract void setCheatsEnabled(boolean z);

    public abstract boolean cheatsEnabled();

    protected abstract void kickPlayer(String str, Object obj);

    public abstract int onlinePlayerCount();

    public abstract int getSlots();

    public abstract void setSlots(int i);

    public abstract void stopServer();

    public abstract void setPlayerGameMode(String str, GameMode gameMode);

    public abstract void setOperator(String str, boolean z);

    @Nullable
    public abstract String[] getWhitelist();

    @Nullable
    public abstract UUID setWhitelist(String str, boolean z);

    public abstract void iterateOnlinePlayers(PlayerConsumer playerConsumer);

    @Nullable
    public abstract String getHost();

    public void kickPlayer(String name, Component component) {
        kickPlayer(name, Laby.references().componentMapper().toMinecraftComponent(component));
    }

    public Class<? extends SocketChannel> getChannelClass(EventLoopGroup group) {
        Objects.requireNonNull(group);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), EpollEventLoopGroup.class, NioEventLoopGroup.class).dynamicInvoker().invoke(group, 0) /* invoke-custom */) {
            case 0:
                return EpollSocketChannel.class;
            case 1:
                return NioSocketChannel.class;
            default:
                throw new IllegalStateException("Unknown EventLoopGroup: " + String.valueOf(group));
        }
    }
}
