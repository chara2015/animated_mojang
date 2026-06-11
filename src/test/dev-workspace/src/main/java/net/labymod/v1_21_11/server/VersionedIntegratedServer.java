package net.labymod.v1_21_11.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollIoHandler;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.nio.file.Path;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.models.Implements;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.server.LocalWorld;
import net.labymod.core.client.world.storage.accessor.LevelStorageAccessor;
import net.labymod.core.client.worldsharing.model.GameDifficulty;
import net.labymod.core.client.worldsharing.model.PlayerConsumer;
import net.labymod.core.server.AbstractIntegratedServer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        iqa server;
        return gfj.V().Y() && (server = gfj.V().aa()) != null && server.q();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorAa;
        GameMode gameMode;
        if (!gfj.V().Y() || (minecraftServerAccessorAa = gfj.V().aa()) == null) {
            return null;
        }
        dwl gameType = minecraftServerAccessorAa.bf();
        if (gameType == null) {
            gameType = dwl.a;
        }
        boolean allowCommands = minecraftServerAccessorAa.aj().v();
        int port = minecraftServerAccessorAa.V();
        LevelStorageAccessor storageSource = minecraftServerAccessorAa.getStorageSource();
        String strD = minecraftServerAccessorAa.bb().d();
        String levelId = storageSource.getLevelId();
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$GameType[gameType.ordinal()]) {
            case 1:
                gameMode = GameMode.CREATIVE;
                break;
            case 2:
                gameMode = GameMode.SURVIVAL;
                break;
            case 3:
                gameMode = GameMode.ADVENTURE;
                break;
            case 4:
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        return new LocalWorld(strD, levelId, gameMode, allowCommands, port, (Path) minecraftServerAccessorAa.F().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[dwl.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dwl.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dwl.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dwl.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dwl.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public Class<? extends SocketChannel> getChannelClass(EventLoopGroup group) {
        if (group instanceof NioEventLoopGroup) {
            return NioSocketChannel.class;
        }
        if (group instanceof EpollEventLoopGroup) {
            return EpollSocketChannel.class;
        }
        if (group instanceof KQueueEventLoopGroup) {
            return KQueueSocketChannel.class;
        }
        if (group instanceof MultiThreadIoEventLoopGroup) {
            MultiThreadIoEventLoopGroup mio = (MultiThreadIoEventLoopGroup) group;
            if (mio.isIoType(NioIoHandler.class)) {
                return NioSocketChannel.class;
            }
            if (mio.isIoType(EpollIoHandler.class)) {
                return EpollSocketChannel.class;
            }
            if (mio.isIoType(KQueueIoHandler.class)) {
                return KQueueSocketChannel.class;
            }
        }
        throw new IllegalArgumentException("Unknown EventLoopGroup: " + String.valueOf(group.getClass()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return bfx.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        iqa server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(dwl.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        iqa server = getServer();
        if (server == null) {
            return "";
        }
        return server.bb().d();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        iqa server = getServer();
        if (server == null) {
            return false;
        }
        return server.q();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        iqa server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(dwl.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        iqa server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.w().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        iqa server = getServer();
        if (server == null) {
            return;
        }
        server.a(ccz.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        iqa server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.bb().p().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        iqa server = getServer();
        if (server == null) {
            return false;
        }
        return server.aj().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        iqa server = getServer();
        if (server == null) {
            return;
        }
        bbz playerList = server.aj();
        playerList.a(enabled);
        for (axg pl : playerList.t()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        axg profile;
        yh yhVarB;
        iqa server = getServer();
        if (server == null || (profile = server.aj().a(name)) == null) {
            return;
        }
        ayi ayiVar = profile.g;
        if (reason instanceof yh) {
            yh component = (yh) reason;
            yhVarB = component;
        } else {
            yhVarB = yh.b(reason.toString());
        }
        ayiVar.a(yhVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        iqa server = getServer();
        if (server == null) {
            return 0;
        }
        return server.R();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        iqa server = getServer();
        if (server == null) {
            return 0;
        }
        return iqa.m;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        iqa server = getServer();
        if (server != null) {
            iqa.m = slots;
            server.at();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        iqa server = getServer();
        if (server == null) {
            return;
        }
        server.ak().b();
        server.r = -1;
        iqa.m = 8;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        axg player;
        iqa server = getServer();
        if (server == null || (player = server.aj().a(username)) == null) {
            return;
        }
        player.a(dwl.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        axg player;
        iqa server = getServer();
        if (server != null && (player = server.aj().a(username)) != null) {
            if (op) {
                server.aj().d(player.gJ());
            } else {
                server.aj().e(player.gJ());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        iqa server = getServer();
        if (server == null) {
            return null;
        }
        return server.aj().i().b();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        iqa server = getServer();
        if (server == null) {
            return null;
        }
        bbx profile = (bbx) server.ar().f().a(name).orElseGet(() -> {
            return bbx.a(name);
        });
        if (add) {
            if (!server.aj().i().a(new bck(profile))) {
                return null;
            }
        } else if (!server.aj().i().b(new bck(profile))) {
            return null;
        }
        return profile.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        iqa server = getServer();
        if (server == null) {
            return;
        }
        bbz playerList = server.aj();
        for (axg player : playerList.t()) {
            consumer.accept(player.gI().name(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.h.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getHost() {
        iqa server = getServer();
        if (server == null || server.W() == null) {
            return null;
        }
        return server.W().name();
    }

    private iqa getServer() {
        return gfj.V().aa();
    }
}
