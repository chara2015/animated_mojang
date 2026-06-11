package net.labymod.v1_21_10.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        igy server;
        return fzz.W().Z() && (server = fzz.W().ab()) != null && server.s();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorAb;
        GameMode gameMode;
        if (!fzz.W().Z() || (minecraftServerAccessorAb = fzz.W().ab()) == null) {
            return null;
        }
        drn gameType = minecraftServerAccessorAb.bj();
        if (gameType == null) {
            gameType = drn.a;
        }
        boolean allowCommands = minecraftServerAccessorAb.am().v();
        int port = minecraftServerAccessorAb.W();
        LevelStorageAccessor storageSource = minecraftServerAccessorAb.getStorageSource();
        String strD = minecraftServerAccessorAb.bf().d();
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
        return new LocalWorld(strD, levelId, gameMode, allowCommands, port, (Path) minecraftServerAccessorAb.H().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_10.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[drn.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[drn.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[drn.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[drn.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[drn.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return bey.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        igy server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(drn.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        igy server = getServer();
        if (server == null) {
            return "";
        }
        return server.bf().d();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        igy server = getServer();
        if (server == null) {
            return false;
        }
        return server.s();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        igy server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(drn.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        igy server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.y().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        igy server = getServer();
        if (server == null) {
            return;
        }
        server.a(cbn.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        igy server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.bf().p().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        igy server = getServer();
        if (server == null) {
            return false;
        }
        return server.am().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        igy server = getServer();
        if (server == null) {
            return;
        }
        bbd playerList = server.am();
        playerList.a(enabled);
        for (awy pl : playerList.t()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        awy profile;
        xx xxVarB;
        igy server = getServer();
        if (server == null || (profile = server.am().a(name)) == null) {
            return;
        }
        axz axzVar = profile.g;
        if (reason instanceof xx) {
            xx component = (xx) reason;
            xxVarB = component;
        } else {
            xxVarB = xx.b(reason.toString());
        }
        axzVar.a(xxVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        igy server = getServer();
        if (server == null) {
            return 0;
        }
        return server.S();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        igy server = getServer();
        if (server == null) {
            return 0;
        }
        return igy.m;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        igy server = getServer();
        if (server != null) {
            igy.m = slots;
            server.ax();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        igy server = getServer();
        if (server == null) {
            return;
        }
        server.an().b();
        server.r = -1;
        igy.m = 8;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        awy player;
        igy server = getServer();
        if (server == null || (player = server.am().a(username)) == null) {
            return;
        }
        player.a(drn.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        awy player;
        igy server = getServer();
        if (server != null && (player = server.am().a(username)) != null) {
            if (op) {
                server.am().d(player.gA());
            } else {
                server.am().e(player.gA());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        igy server = getServer();
        if (server == null) {
            return null;
        }
        return server.am().i().b();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        igy server = getServer();
        if (server == null) {
            return null;
        }
        bbb profile = (bbb) server.av().f().a(name).orElseGet(() -> {
            return bbb.a(name);
        });
        if (add) {
            if (!server.am().i().a(new bbo(profile))) {
                return null;
            }
        } else if (!server.am().i().b(new bbo(profile))) {
            return null;
        }
        return profile.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        igy server = getServer();
        if (server == null) {
            return;
        }
        bbd playerList = server.am();
        for (awy player : playerList.t()) {
            consumer.accept(player.gz().name(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.h.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getHost() {
        igy server = getServer();
        if (server == null || server.X() == null) {
            return null;
        }
        return server.X().name();
    }

    private igy getServer() {
        return fzz.W().ab();
    }
}
