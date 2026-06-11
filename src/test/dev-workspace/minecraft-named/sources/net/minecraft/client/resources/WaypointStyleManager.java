package net.minecraft.client.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.waypoints.WaypointStyleAsset;
import net.minecraft.world.waypoints.WaypointStyleAssets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/WaypointStyleManager.class */
public class WaypointStyleManager extends SimpleJsonResourceReloadListener<WaypointStyle> {
    private static final FileToIdConverter ASSET_LISTER = FileToIdConverter.json("waypoint_style");
    private static final WaypointStyle MISSING = new WaypointStyle(0, 1, List.of(MissingTextureAtlasSprite.getLocation()));
    private Map<ResourceKey<WaypointStyleAsset>, WaypointStyle> waypointStyles;

    public WaypointStyleManager() {
        super(WaypointStyle.CODEC, ASSET_LISTER);
        this.waypointStyles = Map.of();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public void apply(Map<Identifier, WaypointStyle> $$0, ResourceManager $$1, ProfilerFiller $$2) {
        this.waypointStyles = (Map) $$0.entrySet().stream().collect(Collectors.toUnmodifiableMap($$02 -> {
            return ResourceKey.create(WaypointStyleAssets.ROOT_ID, (Identifier) $$02.getKey());
        }, (v0) -> {
            return v0.getValue();
        }));
    }

    public WaypointStyle get(ResourceKey<WaypointStyleAsset> $$0) {
        return this.waypointStyles.getOrDefault($$0, MISSING);
    }
}
