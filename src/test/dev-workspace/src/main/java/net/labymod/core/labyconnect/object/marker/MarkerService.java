package net.labymod.core.labyconnect.object.marker;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.client.world.object.WorldObjectRegistry;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.event.labymod.PacketAddonDevelopmentEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;
import net.labymod.serverapi.core.packet.serverbound.game.feature.marker.ClientAddMarkerPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/marker/MarkerService.class */
@Singleton
@Referenceable
public class MarkerService {
    private static final MarkerPacket.MarkerSendType DEFAULT_SEND_TYPE = MarkerPacket.MarkerSendType.LABY_CONNECT;
    private static final float STEPS_PER_BLOCK = 2.0f;
    private static final float MAX_PLACE_DISTANCE = 100.0f;
    private static final int OUTSIDE_HEIGHT_BLOCKS = 20;
    private static final float ENTITY_RANGE = 2.0f;
    private static final int PROTOCOL_VERSION = 2;
    private static final String LABYCONNECT_KEY = "labymod:marker";
    private static final float MAX_RECEIVE_DISTANCE = 64.0f;
    private final WorldObjectRegistry worldObjectRegistry;
    private MarkerPacket.MarkerSendType markerSendType = DEFAULT_SEND_TYPE;

    public MarkerService(WorldObjectRegistry worldObjectRegistry) {
        this.worldObjectRegistry = worldObjectRegistry;
    }

    @Subscribe
    public void handleMarkerKey(KeyEvent event) {
        Key key;
        if (event.state() != KeyEvent.State.PRESS || !isEnabled() || (key = Laby.labyAPI().config().hotkeys().markerKey().get()) == Key.NONE || key != event.key() || Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened() || !Laby.labyAPI().minecraft().isIngame()) {
            return;
        }
        placeMarker();
    }

    @Subscribe
    public void handleLabyConnectMarker(PacketAddonDevelopmentEvent event) {
        double x;
        double y;
        double z;
        Entity entityOrElse;
        MarkerObject marker;
        PacketAddonDevelopment packet = event.packet();
        ClientPlayer self = Laby.labyAPI().minecraft().getClientPlayer();
        ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        if (!packet.getKey().equals(LABYCONNECT_KEY) || self == null || world == null || !isEnabled()) {
            return;
        }
        ByteBuf buffer = Unpooled.wrappedBuffer(packet.getData());
        int version = buffer.readInt();
        if (version < 0 || version > 2) {
            buffer.release();
            return;
        }
        if (version == 0) {
            x = buffer.readInt() + 0.5f;
            y = buffer.readInt() + 1.0f;
            z = buffer.readInt() + 0.5f;
        } else if (version == 1) {
            x = buffer.readFloat();
            y = buffer.readFloat();
            z = buffer.readFloat();
        } else {
            x = buffer.readDouble();
            y = buffer.readDouble();
            z = buffer.readDouble();
        }
        boolean large = buffer.readBoolean();
        if (buffer.readBoolean()) {
            entityOrElse = world.getEntity(new UUID(buffer.readLong(), buffer.readLong())).orElse(null);
        } else {
            entityOrElse = null;
        }
        Entity target = entityOrElse;
        buffer.release();
        Player sender = world.getPlayer(packet.getSender()).orElse(null);
        if (sender == null || sender.getDistanceSquared(self) > MathHelper.square(MAX_RECEIVE_DISTANCE)) {
            return;
        }
        if (target != null) {
            marker = MarkerObject.forEntity(sender.getUniqueId(), target, large);
        } else {
            marker = MarkerObject.fixed(sender.getUniqueId(), new DoubleVector3(x, y, z), large);
        }
        displayMarker(marker);
    }

    @Subscribe
    public void resetServerData(ServerDisconnectEvent event) {
        setSendType(null);
    }

    @Subscribe
    public void resetServerData(NetworkPayloadEvent event) {
        if (event.identifier().getNamespace().equals(Namespaces.MINECRAFT) && event.identifier().getPath().equals("brand")) {
            setSendType(null);
        }
    }

    public boolean isEnabled() {
        return Laby.labyAPI().config().multiplayer().marker().enabled().get().booleanValue();
    }

    @NotNull
    public MarkerPacket.MarkerSendType sendType() {
        return this.markerSendType;
    }

    public void setSendType(@Nullable MarkerPacket.MarkerSendType markerSendType) {
        if (markerSendType == null) {
            markerSendType = DEFAULT_SEND_TYPE;
        }
        this.markerSendType = markerSendType;
    }

    public void displayMarker(@NotNull MarkerObject marker) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            this.worldObjectRegistry.unregister(v -> {
                WorldObject object = (WorldObject) v.getValue();
                if (object instanceof MarkerObject) {
                    return ((MarkerObject) object).getOwner().equals(marker.getOwner());
                }
                return false;
            });
            this.worldObjectRegistry.register(marker);
            if (!marker.getOwner().equals(Laby.labyAPI().getUniqueId())) {
                Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_MARKER_NOTIFY, 0.1f, 1.0f, marker.position());
            }
        });
    }

    public void placeMarker() {
        ThreadSafe.ensureRenderThread();
        MarkerObject marker = createMarker();
        if (marker == null) {
            return;
        }
        displayMarker(marker);
        if (this.markerSendType == MarkerPacket.MarkerSendType.SERVER) {
            DoubleVector3 position = marker.position();
            Laby.references().labyModProtocolService().sendLabyModPacket(new ClientAddMarkerPacket(0, MathHelper.floor(position.getX() - 0.5d), MathHelper.floor(position.getY() - 1.0d), MathHelper.floor(position.getZ() - 0.5d), marker.isLarge(), marker.getTargetEntity() != null ? marker.getTargetEntity().getUniqueId() : null));
        } else {
            sendLabyConnect(marker);
        }
        Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_PLACE_MARKER, 0.5f, 1.0f, marker.position());
    }

    private void sendLabyConnect(MarkerObject marker) {
        ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (world == null || session == null) {
            return;
        }
        Collection<UUID> receivers = new ArrayList<>();
        for (Player player : world.getPlayers()) {
            Friend friend = session.getFriend(player.getUniqueId());
            if (friend != null && friend.isOnline()) {
                receivers.add(friend.getUniqueId());
            }
        }
        if (receivers.isEmpty()) {
            return;
        }
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(2);
        DoubleVector3 position = marker.position();
        buffer.writeDouble(position.getX());
        buffer.writeDouble(position.getY());
        buffer.writeDouble(position.getZ());
        buffer.writeBoolean(marker.isLarge());
        buffer.writeBoolean(marker.getTargetEntity() != null);
        if (marker.getTargetEntity() != null) {
            buffer.writeLong(marker.getTargetEntity().getUniqueId().getMostSignificantBits());
            buffer.writeLong(marker.getTargetEntity().getUniqueId().getLeastSignificantBits());
        }
        session.sendAddonDevelopment(LABYCONNECT_KEY, (UUID[]) receivers.toArray(new UUID[0]), buffer.array());
        buffer.release();
    }

    private MarkerObject createMarker() {
        DoubleVector3 targetPoint;
        Minecraft minecraft = Laby.labyAPI().minecraft();
        ClientPlayer player = minecraft.getClientPlayer();
        ClientWorld world = minecraft.clientWorld();
        if (player == null || world == null || (targetPoint = getTargetPoint(world, player.perspectiveVector(0.0f), new DoubleVector3(player.eyePosition()))) == null) {
            return null;
        }
        int y = world.getTopBlockYOf(MathHelper.floor(targetPoint.getX()), MathHelper.floor(targetPoint.getY()), MathHelper.floor(targetPoint.getZ()));
        DoubleVector3 targetLocation = new DoubleVector3(targetPoint.getX(), y, targetPoint.getZ());
        boolean large = isOutside(world, targetLocation);
        Entity targetEntity = getTargetEntity(world, targetLocation);
        if (targetEntity != null) {
            return MarkerObject.forEntity(player.getUniqueId(), targetEntity, large);
        }
        targetLocation.set(MathHelper.floor(targetLocation.getX()) + 0.5f, ((int) targetLocation.getY()) + 1, MathHelper.floor(targetLocation.getZ()) + 0.5f);
        return MarkerObject.fixed(player.getUniqueId(), targetLocation, large);
    }

    private Entity getTargetEntity(ClientWorld world, DoubleVector3 location) {
        if (!Laby.references().permissionRegistry().isPermissionEnabled("entity_marker")) {
            return null;
        }
        Entity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (Entity allEntity : world.getEntities()) {
            double distance = allEntity.getDistanceSquared(location);
            if (distance < MathHelper.square(2.0f) && distance < nearestDistance && allEntity != Laby.labyAPI().minecraft().getClientPlayer()) {
                nearest = allEntity;
                nearestDistance = distance;
            }
        }
        return nearest;
    }

    private boolean isOutside(ClientWorld world, DoubleVector3 source) {
        DoubleVector3 target = source.copy();
        for (int i = 0; i < 20; i++) {
            if (world.hasSolidBlockAt(MathHelper.floor(target.getX()), MathHelper.floor(target.getY()), MathHelper.floor(target.getZ()))) {
                return false;
            }
            target.add(0.0d, 1.0d, 0.0d);
        }
        return true;
    }

    private DoubleVector3 getTargetPoint(ClientWorld world, FloatVector3 direction, DoubleVector3 source) {
        direction.multiply(0.5f);
        for (int i = 0; i < 200 * 2.0f && !world.hasSolidBlockAt(MathHelper.floor(source.getX()), MathHelper.floor(source.getY()), MathHelper.floor(source.getZ())) && (getTargetEntity(world, source) == null || i <= 5); i++) {
            source.add(direction);
            if (i + 1 == 200) {
                return null;
            }
        }
        return source;
    }
}
