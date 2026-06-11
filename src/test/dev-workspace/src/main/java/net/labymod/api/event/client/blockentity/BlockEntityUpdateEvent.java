package net.labymod.api.event.client.blockentity;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/blockentity/BlockEntityUpdateEvent.class */
public class BlockEntityUpdateEvent implements Event {
    private final BlockEntity blockEntity;

    public BlockEntityUpdateEvent(@NotNull BlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @NotNull
    public BlockEntity blockEntity() {
        return this.blockEntity;
    }
}
