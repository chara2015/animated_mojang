package net.minecraft.world.level.gameevent;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gameevent/GameEventListener.class */
public interface GameEventListener {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gameevent/GameEventListener$DeliveryMode.class */
    public enum DeliveryMode {
        UNSPECIFIED,
        BY_DISTANCE
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gameevent/GameEventListener$Provider.class */
    public interface Provider<T extends GameEventListener> {
        T getListener();
    }

    PositionSource getListenerSource();

    int getListenerRadius();

    boolean handleGameEvent(ServerLevel serverLevel, Holder<GameEvent> holder, GameEvent.Context context, Vec3 vec3);

    default DeliveryMode getDeliveryMode() {
        return DeliveryMode.UNSPECIFIED;
    }
}
