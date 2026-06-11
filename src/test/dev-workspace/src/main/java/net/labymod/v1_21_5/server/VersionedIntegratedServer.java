package net.labymod.v1_21_5.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        hpb server;
        return fqq.Q().T() && (server = fqq.Q().V()) != null && server.r();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorV;
        GameMode gameMode;
        if (!fqq.Q().T() || (minecraftServerAccessorV = fqq.Q().V()) == null) {
            return null;
        }
        dkg gameType = minecraftServerAccessorV.bd();
        if (gameType == null) {
            gameType = dkg.a;
        }
        boolean allowCommands = minecraftServerAccessorV.ag().v();
        int port = minecraftServerAccessorV.S();
        LevelStorageAccessor storageSource = minecraftServerAccessorV.getStorageSource();
        String strE = minecraftServerAccessorV.aZ().e();
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
        return new LocalWorld(strE, levelId, gameMode, allowCommands, port, (Path) minecraftServerAccessorV.C().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_5.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[dkg.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dkg.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dkg.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dkg.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dkg.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return azq.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        hpb server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(dkg.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        hpb server = getServer();
        if (server == null) {
            return "";
        }
        return server.aZ().e();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        hpb server = getServer();
        if (server == null) {
            return false;
        }
        return server.r();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        hpb server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(dkg.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        hpb server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.u().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        hpb server = getServer();
        if (server == null) {
            return;
        }
        server.a(buz.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        hpb server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.aZ().q().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        hpb server = getServer();
        if (server == null) {
            return false;
        }
        return server.ag().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        hpb server = getServer();
        if (server == null) {
            return;
        }
        awb playerList = server.ag();
        playerList.b(enabled);
        for (asc pl : playerList.t()) {
            playerList.e(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        asc profile;
        xg xgVarB;
        hpb server = getServer();
        if (server == null || (profile = server.ag().a(name)) == null) {
            return;
        }
        ate ateVar = profile.f;
        if (reason instanceof xg) {
            xg component = (xg) reason;
            xgVarB = component;
        } else {
            xgVarB = xg.b(reason.toString());
        }
        ateVar.a(xgVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        hpb server = getServer();
        if (server == null) {
            return 0;
        }
        return server.N();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        hpb server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ag().g;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        hpb server = getServer();
        if (server != null) {
            server.ag().g = slots;
            server.av();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        hpb server = getServer();
        if (server == null) {
            return;
        }
        server.ah().b();
        server.p = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        asc player;
        hpb server = getServer();
        if (server == null || (player = server.ag().a(username)) == null) {
            return;
        }
        player.a(dkg.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        asc player;
        hpb server = getServer();
        if (server != null && (player = server.ag().a(username)) != null) {
            if (op) {
                server.ag().a(player.gi());
            } else {
                server.ag().b(player.gi());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        hpb server = getServer();
        if (server == null) {
            return null;
        }
        return server.ag().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        hpb server = getServer();
        if (server == null || server.at() == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.at().a(name).orElseGet(() -> {
            return ka.b(name);
        });
        if (add) {
            server.ag().i().a(new awk(profile));
        } else {
            server.ag().i().b(new awk(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        hpb server = getServer();
        if (server == null) {
            return;
        }
        awb playerList = server.ag();
        for (asc player : playerList.t()) {
            consumer.accept(player.gi().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.h.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        hpb server = getServer();
        if (server == null || server.T() == null) {
            return null;
        }
        return server.T().getName();
    }

    private hpb getServer() {
        return fqq.Q().V();
    }
}
