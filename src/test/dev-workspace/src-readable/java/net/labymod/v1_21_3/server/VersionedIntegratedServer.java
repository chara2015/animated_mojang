package net.labymod.v1_21_3.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        hfw server;
        return fmg.Q().T() && (server = fmg.Q().V()) != null && server.r();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorV;
        GameMode gameMode;
        if (!fmg.Q().T() || (minecraftServerAccessorV = fmg.Q().V()) == null) {
            return null;
        }
        dhf gameType = minecraftServerAccessorV.bd();
        if (gameType == null) {
            gameType = dhf.a;
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

    /* JADX INFO: renamed from: net.labymod.v1_21_3.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[dhf.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dhf.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dhf.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dhf.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[dhf.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return azv.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        hfw server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(dhf.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        hfw server = getServer();
        if (server == null) {
            return "";
        }
        return server.aZ().e();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        hfw server = getServer();
        if (server == null) {
            return false;
        }
        return server.r();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        hfw server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(dhf.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        hfw server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.u().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        hfw server = getServer();
        if (server == null) {
            return;
        }
        server.a(btg.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        hfw server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.aZ().q().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        hfw server = getServer();
        if (server == null) {
            return false;
        }
        return server.ag().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        hfw server = getServer();
        if (server == null) {
            return;
        }
        awi playerList = server.ag();
        playerList.b(enabled);
        for (asi pl : playerList.t()) {
            playerList.e(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        asi profile;
        xv xvVarB;
        hfw server = getServer();
        if (server == null || (profile = server.ag().a(name)) == null) {
            return;
        }
        atk atkVar = profile.f;
        if (reason instanceof xv) {
            xv component = (xv) reason;
            xvVarB = component;
        } else {
            xvVarB = xv.b(reason.toString());
        }
        atkVar.a(xvVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        hfw server = getServer();
        if (server == null) {
            return 0;
        }
        return server.N();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        hfw server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ag().g;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        hfw server = getServer();
        if (server != null) {
            server.ag().g = slots;
            server.av();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        hfw server = getServer();
        if (server == null) {
            return;
        }
        server.ah().b();
        server.p = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        asi player;
        hfw server = getServer();
        if (server == null || (player = server.ag().a(username)) == null) {
            return;
        }
        player.a(dhf.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        asi player;
        hfw server = getServer();
        if (server != null && (player = server.ag().a(username)) != null) {
            if (op) {
                server.ag().a(player.gh());
            } else {
                server.ag().b(player.gh());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        hfw server = getServer();
        if (server == null) {
            return null;
        }
        return server.ag().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        hfw server = getServer();
        if (server == null || server.at() == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.at().a(name).orElseGet(() -> {
            return kk.b(name);
        });
        if (add) {
            server.ag().i().a(new awr(profile));
        } else {
            server.ag().i().b(new awr(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        hfw server = getServer();
        if (server == null) {
            return;
        }
        awi playerList = server.ag();
        for (asi player : playerList.t()) {
            consumer.accept(player.gh().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.h.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        hfw server = getServer();
        if (server == null || server.T() == null) {
            return null;
        }
        return server.T().getName();
    }

    private hfw getServer() {
        return fmg.Q().V();
    }
}
