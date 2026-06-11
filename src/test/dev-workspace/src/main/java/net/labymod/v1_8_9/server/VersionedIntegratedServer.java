package net.labymod.v1_8_9.server;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.models.Implements;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.server.LocalWorld;
import net.labymod.core.client.worldsharing.model.GameDifficulty;
import net.labymod.core.client.worldsharing.model.PlayerConsumer;
import net.labymod.core.client.worldsharing.model.PortStorage;
import net.labymod.core.server.AbstractIntegratedServer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/server/VersionedIntegratedServer.class */
@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer {
    @Inject
    public VersionedIntegratedServer() {
    }

    @Override // net.labymod.api.server.IntegratedServer
    public boolean isLanWorldOpened() {
        return false;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.server.IntegratedServer
    @Nullable
    public LocalWorld getLocalWorld() throws MatchException {
        bpo server;
        GameMode gameMode;
        if (!ave.A().E() || (server = ave.A().G()) == null || !server.v()) {
            return null;
        }
        a gameType = server.m();
        if (gameType == null) {
            gameType = a.a;
        }
        boolean allowCheats = server.ap().isCommandsAllowedForAll();
        int port = server.aq().findAnyPort();
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$WorldSettings$GameType[gameType.ordinal()]) {
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
        return new LocalWorld(server.V(), server.U(), gameMode2, allowCheats, port, null);
    }

    /* JADX INFO: renamed from: net.labymod.v1_8_9.server.VersionedIntegratedServer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/server/VersionedIntegratedServer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$WorldSettings$GameType = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$WorldSettings$GameType[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$WorldSettings$GameType[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$WorldSettings$GameType[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$WorldSettings$GameType[a.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$WorldSettings$GameType[a.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSuitableLanPort() {
        try {
            return nj.a();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean publishLanWorld(int port, net.labymod.core.client.worldsharing.model.GameMode gameMode, boolean allowCheats) {
        PortStorage server = getServer();
        if (server == null) {
            return false;
        }
        server.labymod$setPort(port);
        return !server.a(a.a(gameMode.getId()), allowCheats).equals("-1");
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String getWorldName() {
        bpo server = getServer();
        if (server == null) {
            return "";
        }
        return server.V();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean isPublished() {
        bpo server = getServer();
        if (server == null) {
            return false;
        }
        return server.b();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeGameMode(net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        bpo server;
        if (!isPublished() || (server = getServer()) == null) {
            return;
        }
        server.a(a.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public net.labymod.core.client.worldsharing.model.GameMode getGameMode() {
        bpo server = getServer();
        if (server == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(server.m().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void changeDifficulty(GameDifficulty difficulty) {
        bpo server = getServer();
        if (server == null) {
            return;
        }
        server.a(oj.a(difficulty.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public GameDifficulty getDifficulty() {
        bpo server = getServer();
        if (server == null) {
            return null;
        }
        return GameDifficulty.fromId(server.n().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public boolean cheatsEnabled() {
        bpo server = getServer();
        if (server == null) {
            return false;
        }
        return server.ap().t;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setCheatsEnabled(boolean enabled) {
        bpo server = getServer();
        if (server == null) {
            return;
        }
        server.ap().t = enabled;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void kickPlayer(String name, Object reason) {
        lf profile;
        String string;
        bpo server = getServer();
        if (server == null || (profile = server.ap().a(name)) == null) {
            return;
        }
        lm lmVar = profile.a;
        if (reason instanceof fb) {
            fb component = (fb) reason;
            string = component.i();
        } else {
            string = reason.toString();
        }
        lmVar.c(string);
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int onlinePlayerCount() {
        bpo server = getServer();
        if (server == null) {
            return 0;
        }
        return server.I();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public int getSlots() {
        bpo server = getServer();
        if (server == null) {
            return 0;
        }
        return server.ap().e;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setSlots(int slots) {
        bpo server = getServer();
        if (server == null) {
            return;
        }
        server.ap().e = slots;
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void stopServer() {
        bpo server = getServer();
        if (server == null) {
            return;
        }
        server.aq().b();
        server.o = false;
    }

    public net.labymod.core.client.worldsharing.model.GameMode getPlayerGameMode(String username) {
        lf player;
        bpo server = getServer();
        if (server == null || (player = server.ap().a(username)) == null) {
            return null;
        }
        return net.labymod.core.client.worldsharing.model.GameMode.fromId(player.c.b().a());
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setPlayerGameMode(String username, net.labymod.core.client.worldsharing.model.GameMode gameMode) {
        lf player;
        bpo server = getServer();
        if (server == null || (player = server.ap().a(username)) == null) {
            return;
        }
        player.a(a.a(gameMode.getId()));
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void setOperator(String username, boolean op) {
        lf player;
        bpo server = getServer();
        if (server != null && (player = server.ap().a(username)) != null) {
            if (op) {
                server.ap().a(player.cd());
            } else {
                server.ap().b(player.cd());
            }
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public String[] getWhitelist() {
        bpo server = getServer();
        if (server == null) {
            return null;
        }
        return server.ap().l();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public UUID setWhitelist(String name, boolean add) {
        bpo server = getServer();
        if (server == null) {
            return null;
        }
        GameProfile profile = server.aF().a(name);
        if (profile == null) {
            UUID lvt_2_1_ = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
            profile = new GameProfile(lvt_2_1_, name);
        }
        if (add) {
            server.ap().k().a(new mf(profile));
        } else {
            server.ap().k().c(profile);
        }
        return profile.getId();
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    public void iterateOnlinePlayers(PlayerConsumer consumer) {
        bpo server = getServer();
        if (server == null) {
            return;
        }
        List<lf> playerList = server.ap().v();
        for (lf player : playerList) {
            consumer.accept(player.cd().getName(), net.labymod.core.client.worldsharing.model.GameMode.fromId(player.c.b().a()), false);
        }
    }

    @Override // net.labymod.core.server.AbstractIntegratedServer
    @Nullable
    public String getHost() {
        bpo server = getServer();
        if (server == null) {
            return null;
        }
        return server.S();
    }

    private bpo getServer() {
        return ave.A().G();
    }
}
