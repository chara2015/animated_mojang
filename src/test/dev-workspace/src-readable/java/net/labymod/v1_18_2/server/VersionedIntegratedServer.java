package net.labymod.v1_18_2.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        fec server;
        return dyr.D().G() && (server = dyr.D().I()) != null && server.o();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorI;
        GameMode gameMode;
        if (!dyr.D().G() || (minecraftServerAccessorI = dyr.D().I()) == null) {
            return null;
        }
        cas gameType = minecraftServerAccessorI.aW();
        if (gameType == null) {
            gameType = cas.a;
        }
        boolean allowCheats = minecraftServerAccessorI.ac().v();
        int port = minecraftServerAccessorI.M();
        LevelStorageAccessor storageSource = minecraftServerAccessorI.getStorageSource();
        String strG = minecraftServerAccessorI.aT().g();
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
        return new LocalWorld(strG, levelId, gameMode, allowCheats, port, (Path) minecraftServerAccessorI.z().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_18_2.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[cas.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return aje.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        fec server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(cas.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        fec server = getServer();
        if (server == null) {
            return "";
        }
        return server.aT().g();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        fec server = getServer();
        if (server == null) {
            return false;
        }
        return server.o();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        fec server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(cas.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        fec server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.h_().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        fec server = getServer();
        if (server == null) {
            return;
        }
        server.a(awe.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        fec server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.aT().s().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        fec server = getServer();
        if (server == null) {
            return false;
        }
        return server.ac().v();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        fec server = getServer();
        if (server == null) {
            return;
        }
        agn playerList = server.ac();
        playerList.b(enabled);
        for (adx pl : playerList.t()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        adx profile;
        qk qkVarA;
        fec server = getServer();
        if (server == null || (profile = server.ac().a(name)) == null) {
            return;
        }
        aeo aeoVar = profile.b;
        if (reason instanceof qk) {
            qk component = (qk) reason;
            qkVarA = component;
        } else {
            qkVarA = qk.a(reason.toString());
        }
        aeoVar.b(qkVarA);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        fec server = getServer();
        if (server == null) {
            return 0;
        }
        return server.H();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        fec server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ac().f;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        fec server = getServer();
        if (server != null) {
            server.ac().f = slots;
            server.aq();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        fec server = getServer();
        if (server == null) {
            return;
        }
        server.ad().b();
        server.u = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        adx player;
        fec server = getServer();
        if (server == null || (player = server.ac().a(username)) == null) {
            return;
        }
        player.a(cas.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        adx player;
        fec server = getServer();
        if (server != null && (player = server.ac().a(username)) != null) {
            if (op) {
                server.ac().a(player.fq());
            } else {
                server.ac().b(player.fq());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        fec server = getServer();
        if (server == null) {
            return null;
        }
        return server.ac().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        fec server = getServer();
        if (server == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.ao().a(name).orElseGet(() -> {
            return new GameProfile(boj.c(name), name);
        });
        if (add) {
            server.ac().i().a(new agw(profile));
        } else {
            server.ac().i().b(new agw(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        fec server = getServer();
        if (server == null) {
            return;
        }
        agn playerList = server.ac();
        for (adx player : playerList.t()) {
            consumer.accept(player.fq().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.d.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        fec server = getServer();
        if (server == null) {
            return null;
        }
        return server.N();
    }

    private fec getServer() {
        return dyr.D().I();
    }
}
