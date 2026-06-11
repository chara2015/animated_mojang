package net.minecraft.world.waypoints;

import net.minecraft.world.waypoints.Waypoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/WaypointManager.class */
public interface WaypointManager<T extends Waypoint> {
    void trackWaypoint(T t);

    void updateWaypoint(T t);

    void untrackWaypoint(T t);
}
