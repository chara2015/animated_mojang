package net.labymod.core.labyconnect.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.client.world.EntityDestructEvent;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.event.labymod.LabyModRefreshEvent;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.packets.PacketUserTracker;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/LabyConnectUserTracker.class */
public class LabyConnectUserTracker {
    private static final long LIST_UPDATE_INTERVAL = 10000;
    private static final long ENTITY_UPDATE_INTERVAL = 3000;
    private final DefaultLabyConnect labyConnect;
    private final Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> listBuffer = new HashMap();
    private final Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> entityBuffer = new HashMap();
    private long nextTimeDrainList = -1;
    private long nextTimeDrainEntities = -1;
    private boolean empty = true;

    public LabyConnectUserTracker(DefaultLabyConnect labyConnect) {
        this.labyConnect = labyConnect;
        for (PacketUserTracker.EnumTrackingAction action : PacketUserTracker.EnumTrackingAction.values()) {
            this.listBuffer.put(action, new ArrayList());
            this.entityBuffer.put(action, new ArrayList());
        }
    }

    @Subscribe
    public void onWorldLoad(WorldLoadEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        clearBuffers();
    }

    @Subscribe
    public void onNetworkDisconnect(NetworkDisconnectEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        clearBuffers();
    }

    @Subscribe
    public void onLabyConnectStateUpdate(LabyConnectStateUpdateEvent event) {
        if (event.state() != LabyConnectState.PLAY) {
            return;
        }
        clearBuffers();
        trackAllOnlinePlayers();
    }

    @Subscribe
    public void onLabyModRefresh(LabyModRefreshEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        clearBuffers();
        trackAllOnlinePlayers();
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (!this.labyConnect.isAuthenticated()) {
            return;
        }
        if (this.nextTimeDrainList < TimeUtil.getMillis() || this.listBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).size() > 10) {
            this.nextTimeDrainList = TimeUtil.getMillis() + LIST_UPDATE_INTERVAL;
            drain(PacketUserTracker.EnumTrackingChannel.LIST, this.listBuffer);
        }
        if (this.nextTimeDrainEntities < TimeUtil.getMillis() || this.entityBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).size() > 10) {
            this.nextTimeDrainEntities = TimeUtil.getMillis() + ENTITY_UPDATE_INTERVAL;
            drain(PacketUserTracker.EnumTrackingChannel.ENTITIES, this.entityBuffer);
        }
    }

    private void drain(PacketUserTracker.EnumTrackingChannel channel, Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> map) {
        for (Map.Entry<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> entry : map.entrySet()) {
            List<PacketUserTracker.PlayerEntityMeta> buffer = entry.getValue();
            if (!buffer.isEmpty()) {
                this.labyConnect.sendPacket(new PacketUserTracker(channel, entry.getKey(), (PacketUserTracker.PlayerEntityMeta[]) buffer.toArray(new PacketUserTracker.PlayerEntityMeta[0])));
                buffer.clear();
            }
        }
    }

    @Subscribe
    public void onPlayerInfoAdd(PlayerInfoAddEvent event) {
        NetworkPlayerInfo playerInfo = event.playerInfo();
        if (!isPlayerInfoValid(playerInfo)) {
            return;
        }
        update(this.listBuffer, new PacketUserTracker.PlayerEntityMeta(playerInfo.profile()), PacketUserTracker.EnumTrackingAction.REMOVE, PacketUserTracker.EnumTrackingAction.ADD, true);
    }

    @Subscribe
    public void onPlayerInfoRemove(PlayerInfoRemoveEvent event) {
        NetworkPlayerInfo playerInfo = event.playerInfo();
        if (!isPlayerInfoValid(playerInfo)) {
            return;
        }
        UUID uniqueId = playerInfo.profile().getUniqueId();
        update(this.listBuffer, new PacketUserTracker.PlayerEntityMeta(uniqueId), PacketUserTracker.EnumTrackingAction.ADD, PacketUserTracker.EnumTrackingAction.REMOVE, false);
    }

    @Subscribe
    public void onVisiblePlayer(EntitySpawnEvent event) {
        Entity entity = event.entity();
        if (!(entity instanceof Player) || !isUniqueIdValid(entity.getUniqueId())) {
            return;
        }
        UUID uniqueId = entity.getUniqueId();
        update(this.entityBuffer, new PacketUserTracker.PlayerEntityMeta(uniqueId), PacketUserTracker.EnumTrackingAction.REMOVE, PacketUserTracker.EnumTrackingAction.ADD, true);
    }

    @Subscribe
    public void onEntityDestruct(EntityDestructEvent event) {
        Entity entity = event.entity();
        if (!(entity instanceof Player) || !isUniqueIdValid(entity.getUniqueId())) {
            return;
        }
        UUID uniqueId = entity.getUniqueId();
        update(this.entityBuffer, new PacketUserTracker.PlayerEntityMeta(uniqueId), PacketUserTracker.EnumTrackingAction.ADD, PacketUserTracker.EnumTrackingAction.REMOVE, false);
    }

    private void update(Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> buffers, PacketUserTracker.PlayerEntityMeta uniqueId, PacketUserTracker.EnumTrackingAction cleanTarget, PacketUserTracker.EnumTrackingAction actionTarget, boolean forceAdd) {
        if (!this.labyConnect.isAuthenticated()) {
            return;
        }
        List<PacketUserTracker.PlayerEntityMeta> buffer = buffers.get(cleanTarget);
        if (buffer.contains(uniqueId)) {
            buffer.remove(uniqueId);
            if (!forceAdd) {
                return;
            }
        }
        List<PacketUserTracker.PlayerEntityMeta> target = buffers.get(actionTarget);
        if (!target.contains(uniqueId)) {
            target.add(uniqueId);
        }
        this.empty = false;
    }

    private void clearBuffers() {
        for (List<PacketUserTracker.PlayerEntityMeta> buffer : this.listBuffer.values()) {
            buffer.clear();
        }
        for (List<PacketUserTracker.PlayerEntityMeta> buffer2 : this.entityBuffer.values()) {
            buffer2.clear();
        }
        if (this.labyConnect.isAuthenticated() && !this.empty) {
            this.labyConnect.sendPacket(new PacketUserTracker(PacketUserTracker.EnumTrackingChannel.LIST, PacketUserTracker.EnumTrackingAction.CLEAR));
            this.labyConnect.sendPacket(new PacketUserTracker(PacketUserTracker.EnumTrackingChannel.ENTITIES, PacketUserTracker.EnumTrackingAction.CLEAR));
        }
        this.empty = true;
    }

    private void trackAllOnlinePlayers() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        ClientPacketListener packetListener = minecraft.getClientPacketListener();
        ClientWorld world = minecraft.clientWorld();
        if (packetListener != null) {
            for (NetworkPlayerInfo playerInfo : packetListener.getNetworkPlayerInfos()) {
                if (isPlayerInfoValid(playerInfo)) {
                    this.listBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).add(new PacketUserTracker.PlayerEntityMeta(playerInfo.profile()));
                    this.empty = false;
                }
            }
        }
        if (world != null) {
            for (Entity entity : world.getPlayers()) {
                this.entityBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).add(new PacketUserTracker.PlayerEntityMeta(entity.getUniqueId()));
                this.empty = false;
            }
        }
    }

    private boolean isPlayerInfoValid(NetworkPlayerInfo playerInfo) {
        UUID uuid = playerInfo.profile().getUniqueId();
        return isUniqueIdValid(uuid);
    }

    private boolean isUniqueIdValid(UUID uniqueId) {
        return (uniqueId.getMostSignificantBits() == 0 || uniqueId.getLeastSignificantBits() == 0 || ((uniqueId.getMostSignificantBits() >> 12) & 15) != 4) ? false : true;
    }
}
