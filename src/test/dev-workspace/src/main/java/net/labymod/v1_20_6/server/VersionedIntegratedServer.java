package net.labymod.v1_20_6.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        gtg server;
        return ffh.Q().T() && (server = ffh.Q().V()) != null && server.r();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorV;
        GameMode gameMode;
        if (!ffh.Q().T() || (minecraftServerAccessorV = ffh.Q().V()) == null) {
            return null;
        }
        dbx gameType = minecraftServerAccessorV.bf();
        if (gameType == null) {
            gameType = dbx.a;
        }
        boolean allowCommands = minecraftServerAccessorV.ah().v();
        int port = minecraftServerAccessorV.R();
        LevelStorageAccessor storageSource = minecraftServerAccessorV.getStorageSource();
        String strE = minecraftServerAccessorV.bb().e();
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
        return new LocalWorld(strE, levelId, gameMode, allowCommands, port, (Path) minecraftServerAccessorV.B().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_6.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[dbx.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dbx.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dbx.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dbx.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dbx.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return ayq.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        gtg server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(dbx.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        gtg server = getServer();
        if (server == null) {
            return "";
        }
        return server.bb().e();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        gtg server = getServer();
        if (server == null) {
            return false;
        }
        return server.r();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        gtg server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(dbx.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        gtg server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.u_().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        gtg server = getServer();
        if (server == null) {
            return;
        }
        server.a(bqt.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        gtg server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.bb().q().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        gtg server = getServer();
        if (server == null) {
            return false;
        }
        return server.ah().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        gtg server = getServer();
        if (server == null) {
            return;
        }
        avd playerList = server.ah();
        playerList.b(enabled);
        for (arg pl : playerList.t()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        arg profile;
        xp xpVarB;
        gtg server = getServer();
        if (server == null || (profile = server.ah().a(name)) == null) {
            return;
        }
        asf asfVar = profile.c;
        if (reason instanceof xp) {
            xp component = (xp) reason;
            xpVarB = component;
        } else {
            xpVarB = xp.b(reason.toString());
        }
        asfVar.b(xpVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        gtg server = getServer();
        if (server == null) {
            return 0;
        }
        return server.M();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        gtg server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ah().g;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        gtg server = getServer();
        if (server != null) {
            server.ah().g = slots;
            server.aw();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        gtg server = getServer();
        if (server == null) {
            return;
        }
        server.ai().b();
        server.o = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        arg player;
        gtg server = getServer();
        if (server == null || (player = server.ah().a(username)) == null) {
            return;
        }
        player.a(dbx.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        arg player;
        gtg server = getServer();
        if (server != null && (player = server.ah().a(username)) != null) {
            if (op) {
                server.ah().a(player.gb());
            } else {
                server.ah().b(player.gb());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        gtg server = getServer();
        if (server == null) {
            return null;
        }
        return server.ah().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        gtg server = getServer();
        if (server == null || server.au() == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.au().a(name).orElseGet(() -> {
            return kc.b(name);
        });
        if (add) {
            server.ah().i().a(new avm(profile));
        } else {
            server.ah().i().b(new avm(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        gtg server = getServer();
        if (server == null) {
            return;
        }
        avd playerList = server.ah();
        for (arg player : playerList.t()) {
            consumer.accept(player.gb().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.e.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        gtg server = getServer();
        if (server == null || server.S() == null) {
            return null;
        }
        return server.S().getName();
    }

    private gtg getServer() {
        return ffh.Q().V();
    }
}
