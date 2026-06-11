package net.labymod.v1_16_5.server;

import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.models.Implements;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.server.LocalWorld;
import net.labymod.core.client.worldsharing.model.GameDifficulty;
import net.labymod.core.client.worldsharing.model.PlayerConsumer;
import net.labymod.core.server.AbstractIntegratedServer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        eng server;
        return djz.C().F() && (server = djz.C().H()) != null && server.n();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        eng server;
        GameMode gameMode;
        if (!djz.C().F() || (server = djz.C().H()) == null) {
            return null;
        }
        bru gameType = server.s();
        if (gameType == null) {
            gameType = bru.b;
        }
        boolean allowCheats = server.ae().u();
        int port = server.M();
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$GameType[gameType.ordinal()]) {
            case 1:
            case 2:
                gameMode = GameMode.SURVIVAL;
                break;
            case 3:
                gameMode = GameMode.CREATIVE;
                break;
            case 4:
                gameMode = GameMode.ADVENTURE;
                break;
            case 5:
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        GameMode gameMode2 = gameMode;
        File screenshotFile = server.A();
        return new LocalWorld(server.aX().g(), screenshotFile.getParentFile().getName(), gameMode2, allowCheats, port, screenshotFile.toPath());
    }

    /* JADX INFO: renamed from: net.labymod.v1_16_5.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[bru.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bru.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bru.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bru.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bru.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[bru.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        return aff.a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        eng server = getServer();
        if (server == null) {
            return false;
        }
        return server.a(bru.a(gameMode.getId()), allowCheats, port);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        eng server = getServer();
        if (server == null) {
            return "";
        }
        return server.aX().g();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        eng server = getServer();
        if (server == null) {
            return false;
        }
        return server.n();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        eng server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(bru.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        eng server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.s().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        eng server = getServer();
        if (server == null) {
            return;
        }
        server.a(aor.a(difficulty.getId()), true);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        eng server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.aX().s().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        eng server = getServer();
        if (server == null) {
            return false;
        }
        return server.ae().u();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        eng server = getServer();
        if (server == null) {
            return;
        }
        acu playerList = server.ae();
        playerList.b(enabled);
        for (aah pl : playerList.s()) {
            playerList.d(pl);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        aah profile;
        nr nrVarA;
        eng server = getServer();
        if (server == null || (profile = server.ae().a(name)) == null) {
            return;
        }
        aay aayVar = profile.b;
        if (reason instanceof nr) {
            nr component = (nr) reason;
            nrVarA = component;
        } else {
            nrVarA = nr.a(reason.toString());
        }
        aayVar.b(nrVarA);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        eng server = getServer();
        if (server == null) {
            return 0;
        }
        return server.I();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        eng server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ae().f;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        eng server = getServer();
        if (server != null) {
            server.ae().f = slots;
            server.at();
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        eng server = getServer();
        if (server == null) {
            return;
        }
        server.af().b();
        server.l = -1;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        aah player;
        eng server = getServer();
        if (server == null || (player = server.ae().a(username)) == null) {
            return;
        }
        player.a(bru.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        aah player;
        eng server = getServer();
        if (server != null && (player = server.ae().a(username)) != null) {
            if (op) {
                server.ae().a(player.eA());
            } else {
                server.ae().b(player.eA());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        eng server = getServer();
        if (server == null) {
            return null;
        }
        return server.ae().i().a();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        eng server = getServer();
        if (server == null) {
            return null;
        }
        GameProfile profile = server.ar().a(name);
        if (profile == null) {
            profile = new GameProfile(bfw.c(name), name);
        }
        if (add) {
            server.ae().i().a(new adc(profile));
        } else {
            server.ae().i().b(new adc(profile));
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        eng server = getServer();
        if (server == null) {
            return;
        }
        acu playerList = server.ae();
        for (aah player : playerList.s()) {
            consumer.accept(player.eA().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.d.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        eng server = getServer();
        if (server == null) {
            return null;
        }
        return server.N();
    }

    private eng getServer() {
        return djz.C().H();
    }
}
