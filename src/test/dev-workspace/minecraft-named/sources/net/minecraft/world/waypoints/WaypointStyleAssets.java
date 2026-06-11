package net.minecraft.world.waypoints;

import net.minecraft.core.Registry;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/WaypointStyleAssets.class */
public interface WaypointStyleAssets {
    public static final ResourceKey<? extends Registry<WaypointStyleAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("waypoint_style_asset"));
    public static final ResourceKey<WaypointStyleAsset> DEFAULT = createId(GameTestEnvironments.DEFAULT);
    public static final ResourceKey<WaypointStyleAsset> BOWTIE = createId("bowtie");

    static ResourceKey<WaypointStyleAsset> createId(String $$0) {
        return ResourceKey.create(ROOT_ID, Identifier.withDefaultNamespace($$0));
    }
}
