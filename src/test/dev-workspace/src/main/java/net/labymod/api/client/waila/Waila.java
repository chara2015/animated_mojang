package net.labymod.api.client.waila;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.block.BlockState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/Waila.class */
public interface Waila<T> {
    T getValue();

    static Waila<Entity> entity(Entity entity) {
        return new WailaEntity(entity);
    }

    static Waila<BlockState> block(BlockState block) {
        return new WailaBlock(block);
    }
}
