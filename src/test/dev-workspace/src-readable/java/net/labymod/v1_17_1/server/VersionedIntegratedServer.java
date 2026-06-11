package net.labymod.v1_17_1.server;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        faq server;
        return dvp.C().F() && (server = dvp.C().H()) != null && server.o();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        MinecraftServerAccessor minecraftServerAccessorH;
        GameMode gameMode;
        if (!dvp.C().F() || (minecraftServerAccessorH = dvp.C().H()) == null) {
            return null;
        }
        bwn gameType = minecraftServerAccessorH.aY();
        if (gameType == null) {
            gameType = bwn.a;
        }
        boolean allowCheats = minecraftServerAccessorH.ad().u();
        int port = minecraftServerAccessorH.M();
        LevelStorageAccessor storageSource = minecraftServerAccessorH.getStorageSource();
        String strG = minecraftServerAccessorH.aV().g();
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
        return new LocalWorld(strG, levelId, gameMode, allowCheats, port, (Path) minecraftServerAccessorH.A().orElse(null));
    }

    /* JADX INFO: renamed from: net.labymod.v1_17_1.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[bwn.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bwn.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bwn.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bwn.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bwn.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return agw.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        faq server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(bwn.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        faq server = getServer();
        if (server == null) {
            return "";
        }
        return server.aV().g();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        faq server = getServer();
        if (server == null) {
            return false;
        }
        return server.o();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        faq server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(bwn.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        faq server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.g_().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        faq server = getServer();
        if (server == null) {
            return;
        }
        server.a(ary.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        faq server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.aV().s().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        faq server = getServer();
        if (server == null) {
            return false;
        }
        return server.ad().u();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        faq server = getServer();
        if (server == null) {
            return;
        }
        aeh playerList = server.ad();
        playerList.b(enabled);
        for (abs pl : playerList.s()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        abs profile;
        os osVarA;
        faq server = getServer();
        if (server == null || (profile = server.ad().a(name)) == null) {
            return;
        }
        acj acjVar = profile.b;
        if (reason instanceof os) {
            os component = (os) reason;
            osVarA = component;
        } else {
            osVarA = os.a(reason.toString());
        }
        acjVar.b(osVarA);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        faq server = getServer();
        if (server == null) {
            return 0;
        }
        return server.I();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        faq server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ad().f;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        faq server = getServer();
        if (server != null) {
            server.ad().f = slots;
            server.ar();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        faq server = getServer();
        if (server == null) {
            return;
        }
        server.ae().b();
        server.t = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        abs player;
        faq server = getServer();
        if (server == null || (player = server.ad().a(username)) == null) {
            return;
        }
        player.a(bwn.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        abs player;
        faq server = getServer();
        if (server != null && (player = server.ad().a(username)) != null) {
            if (op) {
                server.ad().a(player.fj());
            } else {
                server.ad().b(player.fj());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        faq server = getServer();
        if (server == null) {
            return null;
        }
        return server.ad().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        faq server = getServer();
        if (server == null) {
            return null;
        }
        GameProfile profile = (GameProfile) server.ap().a(name).orElseGet(() -> {
            return new GameProfile(bke.c(name), name);
        });
        if (add) {
            server.ad().i().a(new aeq(profile));
        } else {
            server.ad().i().b(new aeq(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        faq server = getServer();
        if (server == null) {
            return;
        }
        aeh playerList = server.ad();
        for (abs player : playerList.s()) {
            consumer.accept(player.fj().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.d.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        faq server = getServer();
        if (server == null) {
            return null;
        }
        return server.N();
    }

    private faq getServer() {
        return dvp.C().H();
    }
}
