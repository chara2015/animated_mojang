package net.minecraft.server.notifications;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.IpBanListEntry;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.world.level.gamerules.GameRule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/notifications/NotificationManager.class */
public class NotificationManager implements NotificationService {
    private final List<NotificationService> notificationServices = Lists.newArrayList();

    public void registerService(NotificationService $$0) {
        this.notificationServices.add($$0);
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerJoined(ServerPlayer $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerJoined($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerLeft(ServerPlayer $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerLeft($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void serverStarted() {
        this.notificationServices.forEach((v0) -> {
            v0.serverStarted();
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void serverShuttingDown() {
        this.notificationServices.forEach((v0) -> {
            v0.serverShuttingDown();
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void serverSaveStarted() {
        this.notificationServices.forEach((v0) -> {
            v0.serverSaveStarted();
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void serverSaveCompleted() {
        this.notificationServices.forEach((v0) -> {
            v0.serverSaveCompleted();
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void serverActivityOccured() {
        this.notificationServices.forEach((v0) -> {
            v0.serverActivityOccured();
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerOped(ServerOpListEntry $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerOped($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerDeoped(ServerOpListEntry $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerDeoped($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerAddedToAllowlist(NameAndId $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerAddedToAllowlist($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerRemovedFromAllowlist(NameAndId $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerRemovedFromAllowlist($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void ipBanned(IpBanListEntry $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.ipBanned($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void ipUnbanned(String $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.ipUnbanned($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerBanned(UserBanListEntry $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerBanned($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void playerUnbanned(NameAndId $$0) {
        this.notificationServices.forEach($$1 -> {
            $$1.playerUnbanned($$0);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public <T> void onGameRuleChanged(GameRule<T> $$0, T $$1) {
        this.notificationServices.forEach($$2 -> {
            $$2.onGameRuleChanged($$0, $$1);
        });
    }

    @Override // net.minecraft.server.notifications.NotificationService
    public void statusHeartbeat() {
        this.notificationServices.forEach((v0) -> {
            v0.statusHeartbeat();
        });
    }
}
