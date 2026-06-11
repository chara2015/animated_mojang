package net.labymod.v1_21_8.server;

import com.mojang.authlib.GameProfile;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        hwf server;
        return fue.R().U() && (server = fue.R().W()) != null && server.r();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorW;
        GameMode gameMode;
        if (!fue.R().U() || (minecraftServerAccessorW = fue.R().W()) == null) {
            return null;
        }
        dmr gameType = minecraftServerAccessorW.bd();
        if (gameType == null) {
            gameType = dmr.a;
        }
        boolean allowCommands = minecraftServerAccessorW.ag().v();
        int port = minecraftServerAccessorW.S();
        LevelStorageAccessor storageSource = minecraftServerAccessorW.getStorageSource();
        String strE = minecraftServerAccessorW.aZ().e();
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
        return new LocalWorld(strE, levelId, gameMode, allowCommands, port, (Path) minecraftServerAccessorW.C().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_8.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[dmr.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dmr.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dmr.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dmr.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dmr.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return bbs.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        hwf server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(dmr.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        hwf server = getServer();
        if (server == null) {
            return "";
        }
        return server.aZ().e();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        hwf server = getServer();
        if (server == null) {
            return false;
        }
        return server.r();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        hwf server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(dmr.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        hwf server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.u().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        hwf server = getServer();
        if (server == null) {
            return;
        }
        server.a(bxg.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        hwf server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.aZ().q().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        hwf server = getServer();
        if (server == null) {
            return false;
        }
        return server.ag().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        hwf server = getServer();
        if (server == null) {
            return;
        }
        ayb playerList = server.ag();
        playerList.b(enabled);
        for (auc pl : playerList.t()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        auc profile;
        xo xoVarB;
        hwf server = getServer();
        if (server == null || (profile = server.ag().a(name)) == null) {
            return;
        }
        avf avfVar = profile.g;
        if (reason instanceof xo) {
            xo component = (xo) reason;
            xoVarB = component;
        } else {
            xoVarB = xo.b(reason.toString());
        }
        avfVar.a(xoVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        hwf server = getServer();
        if (server == null) {
            return 0;
        }
        return server.N();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        hwf server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ag().g;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        hwf server = getServer();
        if (server != null) {
            server.ag().g = slots;
            server.av();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        hwf server = getServer();
        if (server == null) {
            return;
        }
        server.ah().b();
        server.p = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        auc player;
        hwf server = getServer();
        if (server == null || (player = server.ag().a(username)) == null) {
            return;
        }
        player.a(dmr.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        auc player;
        hwf server = getServer();
        if (server != null && (player = server.ag().a(username)) != null) {
            if (op) {
                server.ag().a(player.gr());
            } else {
                server.ag().b(player.gr());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        hwf server = getServer();
        if (server == null) {
            return null;
        }
        return server.ag().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        hwf server = getServer();
        if (server == null || server.at() == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.at().a(name).orElseGet(() -> {
            return kf.b(name);
        });
        if (add) {
            server.ag().i().a(new ayk(profile));
        } else {
            server.ag().i().b(new ayk(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        hwf server = getServer();
        if (server == null) {
            return;
        }
        ayb playerList = server.ag();
        for (auc player : playerList.t()) {
            consumer.accept(player.gr().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.h.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        hwf server = getServer();
        if (server == null || server.T() == null) {
            return null;
        }
        return server.T().getName();
    }

    private hwf getServer() {
        return fue.R().W();
    }
}
