package net.minecraft.client.renderer.entity.state;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.saveddata.maps.MapId;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/ItemFrameRenderState.class */
public class ItemFrameRenderState extends EntityRenderState {
    public int rotation;
    public boolean isGlowFrame;
    public MapId mapId;
    public Direction direction = Direction.NORTH;
    public final ItemStackRenderState item = new ItemStackRenderState();
    public final MapRenderState mapRenderState = new MapRenderState();
}
