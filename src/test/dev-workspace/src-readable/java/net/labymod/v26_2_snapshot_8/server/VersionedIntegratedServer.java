package net.labymod.v26_2_snapshot_8.server;

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
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        net.minecraft.client.server.IntegratedServer server;
        return Minecraft.getInstance().isLocalServer() && (server = Minecraft.getInstance().getSingleplayerServer()) != null && server.isPublished();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor singleplayerServer;
        GameMode gameMode;
        if (!Minecraft.getInstance().isLocalServer() || (singleplayerServer = Minecraft.getInstance().getSingleplayerServer()) == null) {
            return null;
        }
        GameType gameType = singleplayerServer.getForcedGameType();
        if (gameType == null) {
            gameType = GameType.SURVIVAL;
        }
        boolean allowCommands = singleplayerServer.getPlayerList().isAllowCommandsForAllPlayers();
        int port = singleplayerServer.getPort();
        LevelStorageAccessor storageSource = singleplayerServer.getStorageSource();
        String levelName = singleplayerServer.getWorldData().getLevelName();
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
        return new LocalWorld(levelName, levelId, gameMode, allowCommands, port, (Path) singleplayerServer.getWorldScreenshotFile().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[GameType.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.CREATIVE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.SURVIVAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.ADVENTURE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.SPECTATOR.ordinal()] = 4;
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
        return HttpUtil.getAvailablePort();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return false;
        }
        return server.publishServer(MinecraftServer.MultiplayerScope.LAN, GameType.byId(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return "";
        }
        return server.getWorldData().getLevelName();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return false;
        }
        return server.isPublished();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        net.minecraft.client.server.IntegratedServer server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.setDefaultGameType(GameType.byId(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.getDefaultGameType().getId());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return;
        }
        server.setDifficulty(Difficulty.byId(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.getWorldData().getDifficulty().getId());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return false;
        }
        return server.getPlayerList().isAllowCommandsForAllPlayers();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return;
        }
        PlayerList playerList = server.getPlayerList();
        playerList.setAllowCommandsForAllPlayers(enabled);
        for (ServerPlayer pl : playerList.getPlayers()) {
            playerList.sendPlayerPermissionLevel(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        ServerPlayer profile;
        Component componentLiteral;
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null || (profile = server.getPlayerList().getPlayerByName(name)) == null) {
            return;
        }
        ServerGamePacketListenerImpl serverGamePacketListenerImpl = profile.connection;
        if (reason instanceof Component) {
            Component component = (Component) reason;
            componentLiteral = component;
        } else {
            componentLiteral = Component.literal(reason.toString());
        }
        serverGamePacketListenerImpl.disconnect(componentLiteral);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return 0;
        }
        return server.getPlayerCount();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return 0;
        }
        return net.minecraft.client.server.IntegratedServer.MAX_PLAYERS;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server != null) {
            net.minecraft.client.server.IntegratedServer.MAX_PLAYERS = slots;
            server.invalidateStatus();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return;
        }
        server.getConnection().stop();
        server.publishedPort = -1;
        net.minecraft.client.server.IntegratedServer.MAX_PLAYERS = 8;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        ServerPlayer player;
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null || (player = server.getPlayerList().getPlayerByName(username)) == null) {
            return;
        }
        player.setGameMode(GameType.byId(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        ServerPlayer player;
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server != null && (player = server.getPlayerList().getPlayerByName(username)) != null) {
            if (op) {
                server.getPlayerList().op(player.nameAndId());
            } else {
                server.getPlayerList().deop(player.nameAndId());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return null;
        }
        return server.getPlayerList().getWhiteList().getUserList();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return null;
        }
        NameAndId profile = (NameAndId) server.services().nameToIdCache().get(name).orElseGet(() -> {
            return NameAndId.createOffline(name);
        });
        if (add) {
            if (!server.getPlayerList().getWhiteList().add(new UserWhiteListEntry(profile))) {
                return null;
            }
        } else if (!server.getPlayerList().getWhiteList().remove(new UserWhiteListEntry(profile))) {
            return null;
        }
        return profile.id();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null) {
            return;
        }
        PlayerList playerList = server.getPlayerList();
        for (ServerPlayer player : playerList.getPlayers()) {
            consumer.accept(player.getGameProfile().name(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.gameMode.getGameModeForPlayer().getId()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getHost() {
        net.minecraft.client.server.IntegratedServer server = getServer();
        if (server == null || server.getSingleplayerProfile() == null) {
            return null;
        }
        return server.getSingleplayerProfile().name();
    }

    private net.minecraft.client.server.IntegratedServer getServer() {
        return Minecraft.getInstance().getSingleplayerServer();
    }
}
