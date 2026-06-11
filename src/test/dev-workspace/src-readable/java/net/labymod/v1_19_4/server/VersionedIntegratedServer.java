package net.labymod.v1_19_4.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        fww server;
        return emh.N().Q() && (server = emh.N().S()) != null && server.p();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorS;
        GameMode gameMode;
        if (!emh.N().Q() || (minecraftServerAccessorS = emh.N().S()) == null) {
            return null;
        }
        cmf gameType = minecraftServerAccessorS.aZ();
        if (gameType == null) {
            gameType = cmf.a;
        }
        boolean allowCheats = minecraftServerAccessorS.ac().v();
        int port = minecraftServerAccessorS.M();
        LevelStorageAccessor storageSource = minecraftServerAccessorS.getStorageSource();
        String strG = minecraftServerAccessorS.aW().g();
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
        return new LocalWorld(strG, levelId, gameMode, allowCheats, port, (Path) minecraftServerAccessorS.y().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_19_4.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[cmf.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cmf.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cmf.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cmf.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cmf.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return apb.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        fww server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(cmf.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        fww server = getServer();
        if (server == null) {
            return "";
        }
        return server.aW().g();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        fww server = getServer();
        if (server == null) {
            return false;
        }
        return server.p();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        fww server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(cmf.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        fww server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.h_().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        fww server = getServer();
        if (server == null) {
            return;
        }
        server.a(bdv.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        fww server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.aW().s().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        fww server = getServer();
        if (server == null) {
            return false;
        }
        return server.ac().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        fww server = getServer();
        if (server == null) {
            return;
        }
        alu playerList = server.ac();
        playerList.b(enabled);
        for (aiq pl : playerList.t()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        aiq profile;
        tj tjVarB;
        fww server = getServer();
        if (server == null || (profile = server.ac().a(name)) == null) {
            return;
        }
        aji ajiVar = profile.b;
        if (reason instanceof tj) {
            tj component = (tj) reason;
            tjVarB = component;
        } else {
            tjVarB = tj.b(reason.toString());
        }
        ajiVar.b(tjVarB);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        fww server = getServer();
        if (server == null) {
            return 0;
        }
        return server.H();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        fww server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ac().g;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        fww server = getServer();
        if (server != null) {
            server.ac().g = slots;
            server.ar();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        fww server = getServer();
        if (server == null) {
            return;
        }
        server.ad().b();
        server.r = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        aiq player;
        fww server = getServer();
        if (server == null || (player = server.ac().a(username)) == null) {
            return;
        }
        player.a(cmf.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        aiq player;
        fww server = getServer();
        if (server != null && (player = server.ac().a(username)) != null) {
            if (op) {
                server.ac().a(player.fI());
            } else {
                server.ac().b(player.fI());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        fww server = getServer();
        if (server == null) {
            return null;
        }
        return server.ac().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        fww server = getServer();
        if (server == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.ap().a(name).orElseGet(() -> {
            return new GameProfile(hx.a(name), name);
        });
        if (add) {
            server.ac().i().a(new amd(profile));
        } else {
            server.ac().i().b(new amd(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        fww server = getServer();
        if (server == null) {
            return;
        }
        alu playerList = server.ac();
        for (aiq player : playerList.t()) {
            consumer.accept(player.fI().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.d.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        fww server = getServer();
        if (server == null || server.N() == null) {
            return null;
        }
        return server.N().getName();
    }

    private fww getServer() {
        return emh.N().S();
    }
}
