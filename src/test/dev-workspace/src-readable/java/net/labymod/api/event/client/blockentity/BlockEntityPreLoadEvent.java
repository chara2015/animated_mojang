package net.labymod.api.event.client.blockentity;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.event.Event;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/blockentity/BlockEntityPreLoadEvent.class */
public class BlockEntityPreLoadEvent implements Event {
    private final BlockEntity blockEntity;
    private final NBTTagCompound tag;

    public BlockEntityPreLoadEvent(@NotNull BlockEntity blockEntity, @NotNull NBTTagCompound tag) {
        this.blockEntity = blockEntity;
        this.tag = tag;
    }

    @NotNull
    public BlockEntity blockEntity() {
        return this.blockEntity;
    }

    @NotNull
    public NBTTagCompound tag() {
        return this.tag;
    }
}
