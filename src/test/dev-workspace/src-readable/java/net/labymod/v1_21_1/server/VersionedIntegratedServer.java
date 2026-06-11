package net.labymod.v1_21_1.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        guo server;
        return fgo.Q().T() && (server = fgo.Q().V()) != null && server.r();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorV;
        GameMode gameMode;
        if (!fgo.Q().T() || (minecraftServerAccessorV = fgo.Q().V()) == null) {
            return null;
        }
        dct gameType = minecraftServerAccessorV.bf();
        if (gameType == null) {
            gameType = dct.a;
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
        return new LocalWorld(strE, levelId, gameMode, allowCommands, port, (Path) minecraftServerAccessorV.C().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_1.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[dct.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dct.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dct.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dct.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dct.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return ayf.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        guo server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(dct.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        guo server = getServer();
        if (server == null) {
            return "";
        }
        return server.bb().e();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        guo server = getServer();
        if (server == null) {
            return false;
        }
        return server.r();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        guo server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(dct.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        guo server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.u_().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        guo server = getServer();
        if (server == null) {
            return;
        }
        server.a(bqo.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        guo server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.bb().q().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        guo server = getServer();
        if (server == null) {
            return false;
        }
        return server.ah().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        guo server = getServer();
        if (server == null) {
            return;
        }
        aur playerList = server.ah();
        playerList.b(enabled);
        for (aqv pl : playerList.t()) {
            playerList.e(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        aqv profile;
        wz wzVarB;
        guo server = getServer();
        if (server == null || (profile = server.ah().a(name)) == null) {
            return;
        }
        aru aruVar = profile.c;
        if (reason instanceof wz) {
            wz component = (wz) reason;
            wzVarB = component;
        } else {
            wzVarB = wz.b(reason.toString());
        }
        aruVar.a(wzVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        guo server = getServer();
        if (server == null) {
            return 0;
        }
        return server.M();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        guo server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ah().g;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        guo server = getServer();
        if (server != null) {
            server.ah().g = slots;
            server.aw();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        guo server = getServer();
        if (server == null) {
            return;
        }
        server.ai().b();
        server.o = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        aqv player;
        guo server = getServer();
        if (server == null || (player = server.ah().a(username)) == null) {
            return;
        }
        player.a(dct.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        aqv player;
        guo server = getServer();
        if (server != null && (player = server.ah().a(username)) != null) {
            if (op) {
                server.ah().a(player.fX());
            } else {
                server.ah().b(player.fX());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        guo server = getServer();
        if (server == null) {
            return null;
        }
        return server.ah().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        guo server = getServer();
        if (server == null || server.au() == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.au().a(name).orElseGet(() -> {
            return kg.b(name);
        });
        if (add) {
            server.ah().i().a(new avb(profile));
        } else {
            server.ah().i().b(new avb(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        guo server = getServer();
        if (server == null) {
            return;
        }
        aur playerList = server.ah();
        for (aqv player : playerList.t()) {
            consumer.accept(player.fX().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.e.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        guo server = getServer();
        if (server == null || server.S() == null) {
            return null;
        }
        return server.S().getName();
    }

    private guo getServer() {
        return fgo.Q().V();
    }
}
